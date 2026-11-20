package com.example.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.HafizIslamicHubApp
import com.example.data.catalog.QuranCatalogData
import com.example.data.model.AppPreferences
import com.example.data.model.DailyReminder
import com.example.data.model.DailyVerse
import com.example.data.model.DeveloperProfile
import com.example.data.model.LanguageDisplayMode
import com.example.data.model.NotificationSettings
import com.example.data.model.PrayerSchedule
import com.example.data.repository.IslamicHubRepository
import com.example.domain.PrayerTimeCalculator
import com.example.notifications.NotificationHelper
import com.example.ui.theme.ThemeMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class MainViewModel(
    application: Application,
    private val repository: IslamicHubRepository
) : AndroidViewModel(application) {

    val preferences: StateFlow<AppPreferences> = repository.preferencesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AppPreferences())

    private val tickerFlow = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1000L)
        }
    }

    val prayerSchedule: StateFlow<PrayerSchedule> = combine(preferences, tickerFlow) { prefs, now ->
        PrayerTimeCalculator.calculatePrayerSchedule(
            city = prefs.selectedCity,
            method = prefs.calcMethod,
            madhhab = prefs.asrMadhhab,
            nowMillis = now
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PrayerTimeCalculator.calculatePrayerSchedule(
            city = AppPreferences().selectedCity,
            method = AppPreferences().calcMethod,
            madhhab = AppPreferences().asrMadhhab
        )
    )

    private val dayOfYearIndex: Int
        get() = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)

    val dailyVerse: DailyVerse
        get() = QuranCatalogData.dailyVerses[dayOfYearIndex % QuranCatalogData.dailyVerses.size]

    val dailyReminder: DailyReminder
        get() = QuranCatalogData.dailyReminders[dayOfYearIndex % QuranCatalogData.dailyReminders.size]

    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    fun setThemeMode(mode: ThemeMode) {
        viewModelScope.launch { repository.setThemeMode(mode) }
    }

    fun setLanguageMode(mode: LanguageDisplayMode) {
        viewModelScope.launch { repository.setLanguageMode(mode) }
    }

    fun setFontSizes(arabicSp: Float, urduSp: Float) {
        viewModelScope.launch { repository.setFontSizes(arabicSp, urduSp) }
    }

    fun updateNotifications(settings: NotificationSettings) {
        viewModelScope.launch {
            repository.updateNotifications(settings)
        }
    }

    fun sendTestPrayerNotification() {
        val current = preferences.value
        val schedule = prayerSchedule.value
        NotificationHelper.showNotification(
            context = getApplication(),
            id = 999,
            channelId = NotificationHelper.CHANNEL_AZAN,
            title = "HAFIZ ISLAMIC HUB • Prayer Alert Active",
            body = "Next Salah: ${schedule.nextPrayerName} at ${schedule.nextPrayerTime} (${schedule.cityName}).",
            vibrateOnly = current.notifications.vibrateOnly
        )
        _statusMessage.value = "Test prayer notification sent"
    }

    fun updateDeveloperProfile(profile: DeveloperProfile) {
        viewModelScope.launch {
            repository.updateDeveloperProfile(profile)
            _statusMessage.value = "Developer profile updated"
        }
    }

    fun updateDeveloperPhoto(uri: Uri) {
        viewModelScope.launch {
            val result = repository.saveDeveloperPhotoFromUri(uri)
            _statusMessage.value = if (result.isSuccess) {
                "Profile photo updated successfully"
            } else {
                "Could not load selected image"
            }
        }
    }

    fun clearDeveloperPhoto() {
        viewModelScope.launch {
            repository.clearDeveloperPhoto()
            _statusMessage.value = "Profile photo reset to default"
        }
    }

    fun incrementTasbeeh() {
        viewModelScope.launch { repository.incrementTasbeeh() }
    }

    fun resetTasbeeh() {
        viewModelScope.launch { repository.resetCurrentTasbeeh() }
    }

    fun selectTasbeehDhikr(id: String, target: Int) {
        viewModelScope.launch { repository.setTasbeehDhikr(id, target) }
    }

    fun setTasbeehTarget(target: Int) {
        viewModelScope.launch { repository.setTasbeehTarget(target) }
    }

    fun setTasbeehHaptics(enabled: Boolean) {
        viewModelScope.launch { repository.setTasbeehHaptics(enabled) }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HafizIslamicHubApp)
                MainViewModel(app, app.repository)
            }
        }
    }
}
