package com.example.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.data.local.PreferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PrayerAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra(NotificationHelper.EXTRA_NOTIF_ID, 100)
        val channel = intent.getStringExtra(NotificationHelper.EXTRA_CHANNEL) ?: NotificationHelper.CHANNEL_AZAN
        val title = intent.getStringExtra(NotificationHelper.EXTRA_TITLE) ?: "Prayer Reminder"
        val body = intent.getStringExtra(NotificationHelper.EXTRA_BODY) ?: "It is time for Salah."

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prefs = PreferencesDataStore(context.applicationContext).preferencesFlow.first()
                NotificationHelper.showNotification(
                    context = context.applicationContext,
                    id = id,
                    channelId = channel,
                    title = title,
                    body = body,
                    vibrateOnly = prefs.notifications.vibrateOnly
                )
                // Schedule next day's alerts
                NotificationHelper.scheduleAllAlerts(context.applicationContext, prefs)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
