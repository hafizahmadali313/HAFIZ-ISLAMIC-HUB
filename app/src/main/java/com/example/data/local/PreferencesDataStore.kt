package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.model.AppPreferences
import com.example.data.model.AsrMadhhab
import com.example.data.model.CityLocation
import com.example.data.model.DeveloperProfile
import com.example.data.model.LanguageDisplayMode
import com.example.data.model.LastReadPosition
import com.example.data.model.NotificationSettings
import com.example.data.model.PrayerCalcMethod
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "hafiz_hub_prefs")

class PreferencesDataStore(private val context: Context) {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val LANGUAGE_MODE = stringPreferencesKey("language_mode")
        val ARABIC_FONT_SIZE = floatPreferencesKey("arabic_font_size")
        val URDU_FONT_SIZE = floatPreferencesKey("urdu_font_size")

        val LAST_SURAH_NUM = intPreferencesKey("last_surah_num")
        val LAST_SURAH_NAME = stringPreferencesKey("last_surah_name")
        val LAST_AYAH_NUM = intPreferencesKey("last_ayah_num")

        val CITY_NAME = stringPreferencesKey("city_name")
        val CITY_COUNTRY = stringPreferencesKey("city_country")
        val CITY_LAT = doublePreferencesKey("city_lat")
        val CITY_LNG = doublePreferencesKey("city_lng")
        val CITY_TZ = stringPreferencesKey("city_tz")
        val CALC_METHOD = stringPreferencesKey("calc_method")
        val ASR_MADHHAB = stringPreferencesKey("asr_madhhab")

        val NOTIF_MASTER = booleanPreferencesKey("notif_master")
        val NOTIF_FAJR = booleanPreferencesKey("notif_fajr")
        val NOTIF_DHUHR = booleanPreferencesKey("notif_dhuhr")
        val NOTIF_ASR = booleanPreferencesKey("notif_asr")
        val NOTIF_MAGHRIB = booleanPreferencesKey("notif_maghrib")
        val NOTIF_ISHA = booleanPreferencesKey("notif_isha")
        val NOTIF_DAILY_REMINDER = booleanPreferencesKey("notif_daily_reminder")
        val NOTIF_OFFSET_MIN = intPreferencesKey("notif_offset_min")
        val NOTIF_VIBRATE_ONLY = booleanPreferencesKey("notif_vibrate_only")

        val DEV_NAME = stringPreferencesKey("dev_name")
        val DEV_TITLE = stringPreferencesKey("dev_title")
        val DEV_BIO = stringPreferencesKey("dev_bio")
        val DEV_EMAIL = stringPreferencesKey("dev_email")
        val DEV_PHOTO_PATH = stringPreferencesKey("dev_photo_path")

        val TASBEEH_COUNT = intPreferencesKey("tasbeeh_count")
        val TASBEEH_TARGET = intPreferencesKey("tasbeeh_target")
        val TASBEEH_DHIKR_ID = stringPreferencesKey("tasbeeh_dhikr_id")
        val TASBEEH_DAILY = intPreferencesKey("tasbeeh_daily")
        val TASBEEH_TOTAL = longPreferencesKey("tasbeeh_total")
        val TASBEEH_LAST_DATE = stringPreferencesKey("tasbeeh_last_date")
        val TASBEEH_HAPTICS = booleanPreferencesKey("tasbeeh_haptics")

        val QUIZ_HIGH_SCORE = intPreferencesKey("quiz_high_score")
    }

    private fun todayKey(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

    val preferencesFlow: Flow<AppPreferences> = context.dataStore.data.map { prefs ->
        val today = todayKey()
        val storedDate = prefs[Keys.TASBEEH_LAST_DATE] ?: today
        val dailyCount = if (storedDate == today) (prefs[Keys.TASBEEH_DAILY] ?: 0) else 0

        AppPreferences(
            themeMode = prefs[Keys.THEME_MODE]?.let { runCatching { ThemeMode.valueOf(it) }.getOrNull() } ?: ThemeMode.SYSTEM,
            languageMode = prefs[Keys.LANGUAGE_MODE]?.let { runCatching { LanguageDisplayMode.valueOf(it) }.getOrNull() } ?: LanguageDisplayMode.BOTH,
            arabicFontSizeSp = prefs[Keys.ARABIC_FONT_SIZE] ?: 26f,
            urduFontSizeSp = prefs[Keys.URDU_FONT_SIZE] ?: 17f,
            lastRead = LastReadPosition(
                surahNumber = prefs[Keys.LAST_SURAH_NUM] ?: 1,
                surahName = prefs[Keys.LAST_SURAH_NAME] ?: "Al-Fatihah",
                ayahNumber = prefs[Keys.LAST_AYAH_NUM] ?: 1
            ),
            selectedCity = CityLocation(
                name = prefs[Keys.CITY_NAME] ?: "Lahore",
                country = prefs[Keys.CITY_COUNTRY] ?: "Pakistan",
                latitude = prefs[Keys.CITY_LAT] ?: 31.5204,
                longitude = prefs[Keys.CITY_LNG] ?: 74.3587,
                timeZoneId = prefs[Keys.CITY_TZ] ?: "Asia/Karachi"
            ),
            calcMethod = prefs[Keys.CALC_METHOD]?.let { runCatching { PrayerCalcMethod.valueOf(it) }.getOrNull() } ?: PrayerCalcMethod.KARACHI,
            asrMadhhab = prefs[Keys.ASR_MADHHAB]?.let { runCatching { AsrMadhhab.valueOf(it) }.getOrNull() } ?: AsrMadhhab.HANAFI,
            notifications = NotificationSettings(
                masterEnabled = prefs[Keys.NOTIF_MASTER] ?: true,
                fajrEnabled = prefs[Keys.NOTIF_FAJR] ?: true,
                dhuhrEnabled = prefs[Keys.NOTIF_DHUHR] ?: true,
                asrEnabled = prefs[Keys.NOTIF_ASR] ?: true,
                maghribEnabled = prefs[Keys.NOTIF_MAGHRIB] ?: true,
                ishaEnabled = prefs[Keys.NOTIF_ISHA] ?: true,
                dailyReminderEnabled = prefs[Keys.NOTIF_DAILY_REMINDER] ?: true,
                reminderMinutesBefore = prefs[Keys.NOTIF_OFFSET_MIN] ?: 0,
                vibrateOnly = prefs[Keys.NOTIF_VIBRATE_ONLY] ?: false
            ),
            developerProfile = DeveloperProfile(
                name = prefs[Keys.DEV_NAME] ?: "HAFIZ AHMAD ALI",
                title = prefs[Keys.DEV_TITLE] ?: "App Builder & Islamic Software Architect",
                bio = prefs[Keys.DEV_BIO] ?: "Dedicated to crafting serene, authentic, and ad-free Islamic digital tools for daily Quran recitation, accurate Salah timings, and spiritual reflection.",
                email = prefs[Keys.DEV_EMAIL] ?: "shazamali4444@gmail.com",
                photoPath = prefs[Keys.DEV_PHOTO_PATH]?.takeIf { it.isNotBlank() }
            ),
            tasbeehCount = prefs[Keys.TASBEEH_COUNT] ?: 0,
            tasbeehTarget = prefs[Keys.TASBEEH_TARGET] ?: 33,
            tasbeehDhikrId = prefs[Keys.TASBEEH_DHIKR_ID] ?: "subhanallah",
            tasbeehDailyCount = dailyCount,
            tasbeehTotalCount = prefs[Keys.TASBEEH_TOTAL] ?: 0L,
            tasbeehLastDate = today,
            tasbeehHaptics = prefs[Keys.TASBEEH_HAPTICS] ?: true,
            quizHighScore = prefs[Keys.QUIZ_HIGH_SCORE] ?: 0
        )
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[Keys.THEME_MODE] = mode.name }
    }

    suspend fun setLanguageMode(mode: LanguageDisplayMode) {
        context.dataStore.edit { it[Keys.LANGUAGE_MODE] = mode.name }
    }

    suspend fun setFontSizes(arabicSp: Float, urduSp: Float) {
        context.dataStore.edit {
            it[Keys.ARABIC_FONT_SIZE] = arabicSp.coerceIn(18f, 44f)
            it[Keys.URDU_FONT_SIZE] = urduSp.coerceIn(13f, 28f)
        }
    }

    suspend fun saveLastRead(surahNumber: Int, surahName: String, ayahNumber: Int) {
        context.dataStore.edit {
            it[Keys.LAST_SURAH_NUM] = surahNumber
            it[Keys.LAST_SURAH_NAME] = surahName
            it[Keys.LAST_AYAH_NUM] = ayahNumber
        }
    }

    suspend fun setCityLocation(city: CityLocation) {
        context.dataStore.edit {
            it[Keys.CITY_NAME] = city.name
            it[Keys.CITY_COUNTRY] = city.country
            it[Keys.CITY_LAT] = city.latitude
            it[Keys.CITY_LNG] = city.longitude
            it[Keys.CITY_TZ] = city.timeZoneId
        }
    }

    suspend fun setPrayerMethod(method: PrayerCalcMethod) {
        context.dataStore.edit { it[Keys.CALC_METHOD] = method.name }
    }

    suspend fun setAsrMadhhab(madhhab: AsrMadhhab) {
        context.dataStore.edit { it[Keys.ASR_MADHHAB] = madhhab.name }
    }

    suspend fun updateNotificationSettings(settings: NotificationSettings) {
        context.dataStore.edit {
            it[Keys.NOTIF_MASTER] = settings.masterEnabled
            it[Keys.NOTIF_FAJR] = settings.fajrEnabled
            it[Keys.NOTIF_DHUHR] = settings.dhuhrEnabled
            it[Keys.NOTIF_ASR] = settings.asrEnabled
            it[Keys.NOTIF_MAGHRIB] = settings.maghribEnabled
            it[Keys.NOTIF_ISHA] = settings.ishaEnabled
            it[Keys.NOTIF_DAILY_REMINDER] = settings.dailyReminderEnabled
            it[Keys.NOTIF_OFFSET_MIN] = settings.reminderMinutesBefore
            it[Keys.NOTIF_VIBRATE_ONLY] = settings.vibrateOnly
        }
    }

    suspend fun updateDeveloperProfile(profile: DeveloperProfile) {
        context.dataStore.edit {
            it[Keys.DEV_NAME] = profile.name.ifBlank { "HAFIZ AHMAD ALI" }
            it[Keys.DEV_TITLE] = profile.title
            it[Keys.DEV_BIO] = profile.bio
            it[Keys.DEV_EMAIL] = profile.email
            it[Keys.DEV_PHOTO_PATH] = profile.photoPath ?: ""
        }
    }

    suspend fun updateDeveloperPhotoPath(path: String?) {
        context.dataStore.edit {
            it[Keys.DEV_PHOTO_PATH] = path ?: ""
        }
    }

    suspend fun incrementTasbeeh() {
        val today = todayKey()
        context.dataStore.edit { prefs ->
            val current = prefs[Keys.TASBEEH_COUNT] ?: 0
            val target = prefs[Keys.TASBEEH_TARGET] ?: 33
            val storedDate = prefs[Keys.TASBEEH_LAST_DATE] ?: today
            val daily = if (storedDate == today) (prefs[Keys.TASBEEH_DAILY] ?: 0) else 0
            val total = prefs[Keys.TASBEEH_TOTAL] ?: 0L

            val nextCount = if (target > 0 && current >= target) 1 else current + 1
            prefs[Keys.TASBEEH_COUNT] = nextCount
            prefs[Keys.TASBEEH_DAILY] = daily + 1
            prefs[Keys.TASBEEH_TOTAL] = total + 1L
            prefs[Keys.TASBEEH_LAST_DATE] = today
        }
    }

    suspend fun resetCurrentTasbeeh() {
        context.dataStore.edit { it[Keys.TASBEEH_COUNT] = 0 }
    }

    suspend fun setTasbeehDhikr(dhikrId: String, target: Int) {
        context.dataStore.edit {
            it[Keys.TASBEEH_DHIKR_ID] = dhikrId
            it[Keys.TASBEEH_TARGET] = target
            it[Keys.TASBEEH_COUNT] = 0
        }
    }

    suspend fun setTasbeehTarget(target: Int) {
        context.dataStore.edit { it[Keys.TASBEEH_TARGET] = target }
    }

    suspend fun setTasbeehHaptics(enabled: Boolean) {
        context.dataStore.edit { it[Keys.TASBEEH_HAPTICS] = enabled }
    }

    suspend fun updateQuizHighScore(score: Int) {
        context.dataStore.edit { prefs ->
            val existing = prefs[Keys.QUIZ_HIGH_SCORE] ?: 0
            if (score > existing) {
                prefs[Keys.QUIZ_HIGH_SCORE] = score
            }
        }
    }
}
