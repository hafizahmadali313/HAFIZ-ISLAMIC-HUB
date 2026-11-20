package com.example

import android.app.Application
import com.example.data.local.PreferencesDataStore
import com.example.data.local.QuranDatabase
import com.example.data.remote.AlQuranApiService
import com.example.data.repository.IslamicHubRepository
import com.example.notifications.NotificationHelper

class HafizIslamicHubApp : Application() {

    val repository: IslamicHubRepository by lazy {
        val db = QuranDatabase.getInstance(this)
        val prefs = PreferencesDataStore(this)
        val api = AlQuranApiService.create()
        IslamicHubRepository(
            context = this,
            quranDao = db.quranDao(),
            prefsStore = prefs,
            quranApi = api
        )
    }

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
    }
}
