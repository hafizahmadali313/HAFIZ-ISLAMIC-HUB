package com.example.domain

import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import com.example.data.model.AsrMadhhab
import com.example.data.model.CityLocation
import com.example.data.model.PrayerCalcMethod
import com.example.data.model.PrayerSchedule
import com.example.data.model.PrayerTimeEntry
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.tan

object PrayerTimeCalculator {

    private const val KAABA_LAT = 21.422487
    private const val KAABA_LNG = 39.826206

    private val hijriMonths = listOf(
        "Muharram",
        "Safar",
        "Rabi' al-Awwal",
        "Rabi' al-Thani",
        "Jumada al-Awwal",
        "Jumada al-Thani",
        "Rajab",
        "Sha'ban",
        "Ramadan",
        "Shawwal",
        "Dhul-Qa'dah",
        "Dhul-Hijjah"
    )

    fun getCurrentHijriDate(date: Date = Date()): String {
        return try {
            val hijriCal = IslamicCalendar(ULocale("@calendar=islamic-umalqura"))
            hijriCal.time = date
            val day = hijriCal.get(IslamicCalendar.DAY_OF_MONTH)
            val monthIndex = hijriCal.get(IslamicCalendar.MONTH).coerceIn(0, 11)
            val year = hijriCal.get(IslamicCalendar.YEAR)
            "$day ${hijriMonths[monthIndex]} $year AH"
        } catch (_: Exception) {
            "Hijri Calendar"
        }
    }

    fun getFormattedGregorianDate(date: Date = Date()): String {
        val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
        return sdf.format(date)
    }

    /**
     * Computes Great-Circle bearing from [latitude, longitude] to Holy Kaaba in degrees [0..360).
     */
    fun calculateQiblaBearing(latitude: Double, longitude: Double): Double {
        val phi1 = Math.toRadians(latitude)
        val phi2 = Math.toRadians(KAABA_LAT)
        val deltaLambda = Math.toRadians(KAABA_LNG - longitude)

        val y = sin(deltaLambda) * cos(phi2)
        val x = cos(phi1) * sin(phi2) - sin(phi1) * cos(phi2) * cos(deltaLambda)
        val bearingRad = atan2(y, x)
        val bearingDeg = Math.toDegrees(bearingRad)
        return (bearingDeg + 360.0) % 360.0
    }

    fun getCompassCardinalLabel(degrees: Double): String {
        val dirs = listOf("N", "NE", "E", "SE", "S", "SW", "W", "NW")
        val index = (((degrees + 22.5) % 360.0) / 45.0).toInt().coerceIn(0, 7)
        return dirs[index]
    }

    fun calculatePrayerSchedule(
        city: CityLocation,
        method: PrayerCalcMethod,
        madhhab: AsrMadhhab,
        nowMillis: Long = System.currentTimeMillis()
    ): PrayerSchedule {
        val tz = TimeZone.getTimeZone(city.timeZoneId)
        val cal = Calendar.getInstance(tz).apply { timeInMillis = nowMillis }
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val tzOffsetHours = tz.getOffset(nowMillis) / 3600000.0
        val jd = julianDate(year, month, day) - city.longitude / (15.0 * 24.0)

        val sun = sunPosition(jd)
        val dhuhrHours = fixHour(12.0 + tzOffsetHours - city.longitude / 15.0 - sun.equationOfTime)

        val sunriseAngle = 0.833
        val sunsetAngle = 0.833

        val sunriseHours = dhuhrHours - hourAngleForSunDepression(city.latitude, sun.declination, sunriseAngle)
        val sunsetHours = dhuhrHours + hourAngleForSunDepression(city.latitude, sun.declination, sunsetAngle)

        val fajrHours = dhuhrHours - hourAngleForSunDepression(city.latitude, sun.declination, method.fajrAngle)
        val asrHours = dhuhrHours + asrHourAngle(city.latitude, sun.declination, madhhab.shadowFactor)
        val maghribHours = sunsetHours + (2.0 / 60.0) // standard 2-minute precaution

        val ishaHours = if (method.ishaIntervalMinutes != null) {
            maghribHours + (method.ishaIntervalMinutes / 60.0)
        } else {
            dhuhrHours + hourAngleForSunDepression(city.latitude, sun.declination, method.ishaAngle)
        }

        val entries = listOf(
            toPrayerEntry("Fajr", "فجر", fajrHours),
            toPrayerEntry("Sunrise", "طلوع آفتاب", sunriseHours, isSunrise = true),
            toPrayerEntry("Dhuhr", "ظہر", dhuhrHours),
            toPrayerEntry("Asr", "عصر", asrHours),
            toPrayerEntry("Maghrib", "مغرب", maghribHours),
            toPrayerEntry("Isha", "عشاء", ishaHours)
        )

        val currentHour = cal.get(Calendar.HOUR_OF_DAY)
        val currentMin = cal.get(Calendar.MINUTE)
        val currentSec = cal.get(Calendar.SECOND)
        val currentTotalSec = currentHour * 3600L + currentMin * 60L + currentSec

        val fardPrayers = entries.filter { !it.isSunrise }
        var nextPrayer = fardPrayers.firstOrNull {
            (it.hour24 * 3600L + it.minute * 60L) > currentTotalSec
        }
        val remainingMillis: Long
        if (nextPrayer != null) {
            val targetSec = nextPrayer.hour24 * 3600L + nextPrayer.minute * 60L
            remainingMillis = (targetSec - currentTotalSec) * 1000L
        } else {
            nextPrayer = fardPrayers.first()
            val targetSec = nextPrayer.hour24 * 3600L + nextPrayer.minute * 60L + 24L * 3600L
            remainingMillis = (targetSec - currentTotalSec) * 1000L
        }

        return PrayerSchedule(
            cityName = city.name,
            country = city.country,
            latitude = city.latitude,
            longitude = city.longitude,
            gregorianDate = getFormattedGregorianDate(Date(nowMillis)),
            hijriDate = getCurrentHijriDate(Date(nowMillis)),
            prayers = entries,
            nextPrayerName = nextPrayer.name,
            nextPrayerTime = nextPrayer.formattedTime,
            nextPrayerMillisRemaining = remainingMillis.coerceAtLeast(0L)
        )
    }

    fun formatCountdown(millis: Long): String {
        val totalSec = (millis / 1000L).coerceAtLeast(0L)
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        return String.format(Locale.US, "%02dh %02dm %02ds", h, m, s)
    }

    private data class SunCoords(val declination: Double, val equationOfTime: Double)

    private fun julianDate(year: Int, month: Int, day: Int): Double {
        var y = year
        var m = month
        if (m <= 2) {
            y -= 1
            m += 12
        }
        val a = floor(y / 100.0)
        val b = 2 - a + floor(a / 4.0)
        return floor(365.25 * (y + 4716)) + floor(30.6001 * (m + 1)) + day + b - 1524.5
    }

    private fun sunPosition(jd: Double): SunCoords {
        val d = jd - 2451545.0
        val g = fixAngle(357.529 + 0.98560028 * d)
        val q = fixAngle(280.459 + 0.98564736 * d)
        val l = fixAngle(q + 1.915 * sinDeg(g) + 0.020 * sinDeg(2 * g))

        val e = 23.439 - 0.00000036 * d
        val ra = Math.toDegrees(atan2(cosDeg(e) * sinDeg(l), cosDeg(l))) / 15.0
        val eqt = q / 15.0 - fixHour(ra)
        val decl = Math.toDegrees(asin(sinDeg(e) * sinDeg(l)))
        return SunCoords(declination = decl, equationOfTime = eqt)
    }

    private fun hourAngleForSunDepression(lat: Double, decl: Double, angle: Double): Double {
        val num = -sinDeg(angle) - sinDeg(lat) * sinDeg(decl)
        val den = cosDeg(lat) * cosDeg(decl)
        val ratio = (num / den).coerceIn(-1.0, 1.0)
        return Math.toDegrees(acos(ratio)) / 15.0
    }

    private fun asrHourAngle(lat: Double, decl: Double, factor: Int): Double {
        val angle = -Math.toDegrees(atan(1.0 / (factor + tanDeg(kotlin.math.abs(lat - decl)))))
        return hourAngleForSunDepression(lat, decl, angle)
    }

    private fun toPrayerEntry(name: String, arabicName: String, decimalHours: Double, isSunrise: Boolean = false): PrayerTimeEntry {
        val fixed = fixHour(decimalHours)
        var hour = floor(fixed).toInt()
        var minute = kotlin.math.round((fixed - hour) * 60.0).toInt()
        if (minute >= 60) {
            minute -= 60
            hour = (hour + 1) % 24
        }
        val amPm = if (hour >= 12) "PM" else "AM"
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        val formatted = String.format(Locale.US, "%d:%02d %s", displayHour, minute, amPm)
        return PrayerTimeEntry(name, arabicName, formatted, hour, minute, isSunrise)
    }

    private fun fixAngle(a: Double): Double {
        var res = a % 360.0
        if (res < 0) res += 360.0
        return res
    }

    private fun fixHour(h: Double): Double {
        var res = h % 24.0
        if (res < 0) res += 24.0
        return res
    }

    private fun sinDeg(d: Double) = sin(Math.toRadians(d))
    private fun cosDeg(d: Double) = cos(Math.toRadians(d))
    private fun tanDeg(d: Double) = tan(Math.toRadians(d))
}
