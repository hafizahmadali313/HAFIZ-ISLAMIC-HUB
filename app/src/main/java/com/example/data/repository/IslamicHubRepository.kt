package com.example.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import com.example.data.catalog.QuranCatalogData
import com.example.data.local.BookmarkEntity
import com.example.data.local.PreferencesDataStore
import com.example.data.local.QuizHistoryEntity
import com.example.data.local.QuranDao
import com.example.data.local.SurahAyahEntity
import com.example.data.model.AppPreferences
import com.example.data.model.AsrMadhhab
import com.example.data.model.AyahItem
import com.example.data.model.CityLocation
import com.example.data.model.DeveloperProfile
import com.example.data.model.LanguageDisplayMode
import com.example.data.model.NotificationSettings
import com.example.data.model.PrayerCalcMethod
import com.example.data.remote.AlQuranApiService
import com.example.notifications.NotificationHelper
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class IslamicHubRepository(
    private val context: Context,
    private val quranDao: QuranDao,
    private val prefsStore: PreferencesDataStore,
    private val quranApi: AlQuranApiService
) {
    val preferencesFlow: Flow<AppPreferences> = prefsStore.preferencesFlow
    val bookmarksFlow: Flow<List<BookmarkEntity>> = quranDao.observeBookmarks()
    val quizHistoryFlow: Flow<List<QuizHistoryEntity>> = quranDao.observeQuizHistory()

    fun observeSurahAyahs(surahNumber: Int): Flow<List<AyahItem>> {
        return combine(
            quranDao.observeAyahsBySurah(surahNumber),
            quranDao.observeBookmarkIds()
        ) { entities, bookmarkIds ->
            val set = bookmarkIds.toHashSet()
            entities.map { entity ->
                AyahItem(
                    surahNumber = entity.surahNumber,
                    ayahNumberInSurah = entity.ayahNumberInSurah,
                    globalAyahNumber = entity.globalAyahNumber,
                    arabicText = entity.arabicText,
                    urduTranslation = entity.urduTranslation,
                    juz = entity.juz,
                    page = entity.page,
                    isBookmarked = set.contains(entity.id)
                )
            }
        }
    }

    suspend fun ensureSurahLoaded(surahNumber: Int): Result<Unit> = withContext(Dispatchers.IO) {
        val existingCount = quranDao.countAyahsForSurah(surahNumber)
        val expectedCount = QuranCatalogData.allSurahs.find { it.number == surahNumber }?.ayahCount ?: 0
        if (existingCount > 0 && existingCount >= expectedCount) {
            return@withContext Result.success(Unit)
        }

        // First check pre-bundled verified Surahs (1 and 105..114)
        val bundled = QuranCatalogData.getBundledSurahAyahs(surahNumber)
        if (bundled.isNotEmpty()) {
            quranDao.insertAyahs(bundled)
            return@withContext Result.success(Unit)
        }

        if (!isNetworkAvailable()) {
            return@withContext Result.failure(
                IllegalStateException("Internet connection is required to download this Surah for offline reading.")
            )
        }

        try {
            val response = quranApi.getSurahWithUrduTranslation(surahNumber)
            val arabicEdition = response.data.getOrNull(0)
            val urduEdition = response.data.getOrNull(1)
            if (arabicEdition == null || urduEdition == null) {
                return@withContext Result.failure(IllegalStateException("Incomplete Surah data received."))
            }

            val entities = arabicEdition.ayahs.mapIndexed { index, arabicAyah ->
                val urduAyah = urduEdition.ayahs.getOrNull(index)
                SurahAyahEntity(
                    id = "$surahNumber:${arabicAyah.numberInSurah}",
                    surahNumber = surahNumber,
                    ayahNumberInSurah = arabicAyah.numberInSurah,
                    globalAyahNumber = arabicAyah.number,
                    arabicText = arabicAyah.text,
                    urduTranslation = urduAyah?.text ?: "",
                    juz = arabicAyah.juz,
                    page = arabicAyah.page
                )
            }
            quranDao.insertAyahs(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchCachedAyahs(query: String): List<SurahAyahEntity> = withContext(Dispatchers.IO) {
        if (query.isBlank()) emptyList() else quranDao.searchCachedAyahs(query.trim())
    }

    suspend fun toggleBookmark(ayah: AyahItem, surahName: String) {
        val id = "${ayah.surahNumber}:${ayah.ayahNumberInSurah}"
        if (ayah.isBookmarked) {
            quranDao.deleteBookmarkById(id)
        } else {
            quranDao.upsertBookmark(
                BookmarkEntity(
                    id = id,
                    surahNumber = ayah.surahNumber,
                    surahName = surahName,
                    ayahNumberInSurah = ayah.ayahNumberInSurah,
                    arabicSnippet = ayah.arabicText,
                    urduSnippet = ayah.urduTranslation
                )
            )
        }
    }

    suspend fun removeBookmark(id: String) {
        quranDao.deleteBookmarkById(id)
    }

    suspend fun saveLastRead(surahNumber: Int, surahName: String, ayahNumber: Int) {
        prefsStore.saveLastRead(surahNumber, surahName, ayahNumber)
    }

    suspend fun setThemeMode(mode: ThemeMode) = prefsStore.setThemeMode(mode)
    suspend fun setLanguageMode(mode: LanguageDisplayMode) = prefsStore.setLanguageMode(mode)
    suspend fun setFontSizes(arabicSp: Float, urduSp: Float) = prefsStore.setFontSizes(arabicSp, urduSp)

    suspend fun setCityLocation(city: CityLocation) {
        prefsStore.setCityLocation(city)
        val latest = prefsStore.preferencesFlow.first()
        NotificationHelper.scheduleAllAlerts(context, latest)
    }

    suspend fun setPrayerMethod(method: PrayerCalcMethod) {
        prefsStore.setPrayerMethod(method)
        val latest = prefsStore.preferencesFlow.first()
        NotificationHelper.scheduleAllAlerts(context, latest)
    }

    suspend fun setAsrMadhhab(madhhab: AsrMadhhab) {
        prefsStore.setAsrMadhhab(madhhab)
        val latest = prefsStore.preferencesFlow.first()
        NotificationHelper.scheduleAllAlerts(context, latest)
    }

    suspend fun updateNotifications(settings: NotificationSettings) {
        prefsStore.updateNotificationSettings(settings)
        val latest = prefsStore.preferencesFlow.first()
        NotificationHelper.scheduleAllAlerts(context, latest)
    }

    suspend fun updateDeveloperProfile(profile: DeveloperProfile) {
        prefsStore.updateDeveloperProfile(profile)
    }

    suspend fun saveDeveloperPhotoFromUri(uri: Uri): Result<String> = withContext(Dispatchers.IO) {
        try {
            val destFile = File(context.filesDir, "developer_profile_photo_${System.currentTimeMillis()}.jpg")
            // Clean up older profile photos
            context.filesDir.listFiles()?.forEach { file ->
                if (file.name.startsWith("developer_profile_photo_")) {
                    file.delete()
                }
            }
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(destFile).use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext Result.failure(IllegalStateException("Could not open selected photo"))

            prefsStore.updateDeveloperPhotoPath(destFile.absolutePath)
            Result.success(destFile.absolutePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun clearDeveloperPhoto() = withContext(Dispatchers.IO) {
        context.filesDir.listFiles()?.forEach { file ->
            if (file.name.startsWith("developer_profile_photo_")) {
                file.delete()
            }
        }
        prefsStore.updateDeveloperPhotoPath(null)
    }

    suspend fun incrementTasbeeh() = prefsStore.incrementTasbeeh()
    suspend fun resetCurrentTasbeeh() = prefsStore.resetCurrentTasbeeh()
    suspend fun setTasbeehDhikr(id: String, target: Int) = prefsStore.setTasbeehDhikr(id, target)
    suspend fun setTasbeehTarget(target: Int) = prefsStore.setTasbeehTarget(target)
    suspend fun setTasbeehHaptics(enabled: Boolean) = prefsStore.setTasbeehHaptics(enabled)

    suspend fun saveQuizResult(categoryName: String, score: Int, totalQuestions: Int) {
        quranDao.insertQuizResult(
            QuizHistoryEntity(
                categoryName = categoryName,
                score = score,
                totalQuestions = totalQuestions
            )
        )
        prefsStore.updateQuizHighScore(score)
    }

    fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
