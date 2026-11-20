package com.example.data.model

import com.example.ui.theme.ThemeMode

enum class LanguageDisplayMode(val label: String) {
    BOTH("Arabic + Urdu & English"),
    URDU_PRIMARY("Arabic + Urdu"),
    ENGLISH_PRIMARY("Arabic + English")
}

enum class PrayerCalcMethod(val displayName: String, val fajrAngle: Double, val ishaAngle: Double, val ishaIntervalMinutes: Int? = null) {
    KARACHI("University of Islamic Sciences, Karachi", 18.0, 18.0),
    MWL("Muslim World League", 18.0, 17.0),
    ISNA("ISNA (North America)", 15.0, 15.0),
    UMM_AL_QURA("Umm al-Qura University, Makkah", 18.5, 0.0, 90),
    EGYPT("Egyptian General Authority", 19.5, 17.5)
}

enum class AsrMadhhab(val displayName: String, val shadowFactor: Int) {
    HANAFI("Hanafi (Standard Pakistan/South Asia)", 2),
    SHAFI("Shafi / Maliki / Hanbali", 1)
}

data class SurahInfo(
    val number: Int,
    val arabicName: String,
    val englishName: String,
    val englishMeaning: String,
    val urduName: String,
    val revelationType: String,
    val ayahCount: Int,
    val startingJuz: Int
)

data class JuzInfo(
    val juzNumber: Int,
    val arabicName: String,
    val englishName: String,
    val startSurah: Int,
    val startAyah: Int
)

data class AyahItem(
    val surahNumber: Int,
    val ayahNumberInSurah: Int,
    val globalAyahNumber: Int,
    val arabicText: String,
    val urduTranslation: String,
    val juz: Int,
    val page: Int,
    val isBookmarked: Boolean = false
)

data class DailyVerse(
    val surahName: String,
    val reference: String,
    val arabic: String,
    val urdu: String,
    val english: String
)

data class DailyReminder(
    val title: String,
    val message: String,
    val source: String
)

enum class DuaCategory(val title: String) {
    DAILY("Daily Duas"),
    MORNING_EVENING("Morning & Evening Azkar"),
    SLEEPING("Sleeping Duas"),
    EATING("Eating Duas"),
    TRAVELLING("Travelling Duas"),
    MASNOON("Masnoon Duas"),
    QURANIC("Important Quranic Duas")
}

data class DuaItem(
    val id: String,
    val category: DuaCategory,
    val title: String,
    val arabic: String,
    val urdu: String,
    val english: String,
    val reference: String
)

data class AllahName(
    val number: Int,
    val arabic: String,
    val transliteration: String,
    val urduMeaning: String,
    val englishMeaning: String,
    val reflection: String
)

data class CityLocation(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val timeZoneId: String
)

data class PrayerTimeEntry(
    val name: String,
    val arabicName: String,
    val formattedTime: String,
    val hour24: Int,
    val minute: Int,
    val isSunrise: Boolean = false
)

data class PrayerSchedule(
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val gregorianDate: String,
    val hijriDate: String,
    val prayers: List<PrayerTimeEntry>,
    val nextPrayerName: String,
    val nextPrayerTime: String,
    val nextPrayerMillisRemaining: Long
)

enum class QuizCategory(val title: String) {
    ALL("All Categories"),
    QURAN("Quran Pak"),
    PROPHETS("Prophets (AS)"),
    HISTORY("Islamic History"),
    SEERAH("Seerah of Prophet ﷺ"),
    TEACHINGS("Islamic Teachings")
}

data class QuizQuestion(
    val id: Int,
    val category: QuizCategory,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val reference: String
)

data class TasbeehDhikr(
    val id: String,
    val title: String,
    val arabic: String,
    val urduMeaning: String,
    val englishMeaning: String,
    val defaultTarget: Int
)

data class LastReadPosition(
    val surahNumber: Int = 1,
    val surahName: String = "Al-Fatihah",
    val ayahNumber: Int = 1
)

data class NotificationSettings(
    val masterEnabled: Boolean = true,
    val fajrEnabled: Boolean = true,
    val dhuhrEnabled: Boolean = true,
    val asrEnabled: Boolean = true,
    val maghribEnabled: Boolean = true,
    val ishaEnabled: Boolean = true,
    val dailyReminderEnabled: Boolean = true,
    val reminderMinutesBefore: Int = 0,
    val vibrateOnly: Boolean = false
)

data class DeveloperProfile(
    val name: String = "HAFIZ AHMAD ALI",
    val title: String = "App Builder & Islamic Software Architect",
    val bio: String = "Dedicated to crafting serene, authentic, and ad-free Islamic digital tools for daily Quran recitation, accurate Salah timings, and spiritual reflection.",
    val email: String = "shazamali4444@gmail.com",
    val photoPath: String? = null
)

data class AppPreferences(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageMode: LanguageDisplayMode = LanguageDisplayMode.BOTH,
    val arabicFontSizeSp: Float = 26f,
    val urduFontSizeSp: Float = 17f,
    val lastRead: LastReadPosition = LastReadPosition(),
    val selectedCity: CityLocation = CityLocation("Lahore", "Pakistan", 31.5204, 74.3587, "Asia/Karachi"),
    val calcMethod: PrayerCalcMethod = PrayerCalcMethod.KARACHI,
    val asrMadhhab: AsrMadhhab = AsrMadhhab.HANAFI,
    val notifications: NotificationSettings = NotificationSettings(),
    val developerProfile: DeveloperProfile = DeveloperProfile(),
    val tasbeehCount: Int = 0,
    val tasbeehTarget: Int = 33,
    val tasbeehDhikrId: String = "subhanallah",
    val tasbeehDailyCount: Int = 0,
    val tasbeehTotalCount: Long = 0L,
    val tasbeehLastDate: String = "",
    val tasbeehHaptics: Boolean = true,
    val quizHighScore: Int = 0
)
