package com.example.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.MainActivity
import com.example.R
import com.example.data.model.AppPreferences
import com.example.domain.PrayerTimeCalculator
import java.util.Calendar
import java.util.TimeZone

object NotificationHelper {
    const val CHANNEL_AZAN = "hafiz_azan_alerts"
    const val CHANNEL_REMINDER = "hafiz_daily_reminders"

    const val EXTRA_TITLE = "extra_title"
    const val EXTRA_BODY = "extra_body"
    const val EXTRA_CHANNEL = "extra_channel"
    const val EXTRA_NOTIF_ID = "extra_notif_id"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val azanChannel = NotificationChannel(
                CHANNEL_AZAN,
                context.getString(R.string.channel_azan_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = context.getString(R.string.channel_azan_desc)
                enableVibration(true)
            }
            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDER,
                context.getString(R.string.channel_reminder_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = context.getString(R.string.channel_reminder_desc)
            }
            manager.createNotificationChannel(azanChannel)
            manager.createNotificationChannel(reminderChannel)
        }
    }

    fun showNotification(
        context: Context,
        id: Int,
        channelId: String,
        title: String,
        body: String,
        vibrateOnly: Boolean = false
    ) {
        createChannels(context)
        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            id,
            openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(
                if (channelId == CHANNEL_AZAN) NotificationCompat.PRIORITY_HIGH
                else NotificationCompat.PRIORITY_DEFAULT
            )
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)

        if (vibrateOnly) {
            builder.setSilent(true)
        }

        try {
            NotificationManagerCompat.from(context).notify(id, builder.build())
        } catch (_: SecurityException) {
            // POST_NOTIFICATIONS permission not granted on Android 13+
        }
    }

    fun scheduleAllAlerts(context: Context, prefs: AppPreferences) {
        createChannels(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Cancel existing scheduled prayer alarms (codes 100..106)
        for (reqCode in 100..106) {
            val intent = Intent(context, PrayerAlarmReceiver::class.java)
            val pi = PendingIntent.getBroadcast(
                context,
                reqCode,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            if (pi != null) {
                alarmManager.cancel(pi)
                pi.cancel()
            }
        }

        if (!prefs.notifications.masterEnabled) return

        val schedule = PrayerTimeCalculator.calculatePrayerSchedule(
            city = prefs.selectedCity,
            method = prefs.calcMethod,
            madhhab = prefs.asrMadhhab
        )

        val prayerEnabledMap = mapOf(
            "Fajr" to prefs.notifications.fajrEnabled,
            "Dhuhr" to prefs.notifications.dhuhrEnabled,
            "Asr" to prefs.notifications.asrEnabled,
            "Maghrib" to prefs.notifications.maghribEnabled,
            "Isha" to prefs.notifications.ishaEnabled
        )

        val offsetMin = prefs.notifications.reminderMinutesBefore
        val tz = TimeZone.getTimeZone(prefs.selectedCity.timeZoneId)
        val now = System.currentTimeMillis()

        schedule.prayers.filter { !it.isSunrise }.forEachIndexed { index, prayer ->
            if (prayerEnabledMap[prayer.name] == true) {
                val cal = Calendar.getInstance(tz).apply {
                    timeInMillis = now
                    set(Calendar.HOUR_OF_DAY, prayer.hour24)
                    set(Calendar.MINUTE, prayer.minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    add(Calendar.MINUTE, -offsetMin)
                    if (timeInMillis <= now) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
                val title = if (offsetMin > 0) {
                    "${prayer.name} Prayer in $offsetMin min"
                } else {
                    "Azan Time: ${prayer.name} (${prayer.formattedTime})"
                }
                val body = "Hayya 'alas-Salah • ${prefs.selectedCity.name} (${prayer.formattedTime})"
                scheduleSingleAlarm(
                    context = context,
                    alarmManager = alarmManager,
                    requestCode = 100 + index,
                    triggerAtMillis = cal.timeInMillis,
                    channelId = CHANNEL_AZAN,
                    title = title,
                    body = body
                )
            }
        }

        if (prefs.notifications.dailyReminderEnabled) {
            val dailyCal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 8)
                set(Calendar.MINUTE, 30)
                set(Calendar.SECOND, 0)
                if (timeInMillis <= now) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            scheduleSingleAlarm(
                context = context,
                alarmManager = alarmManager,
                requestCode = 106,
                triggerAtMillis = dailyCal.timeInMillis,
                channelId = CHANNEL_REMINDER,
                title = "Daily Islamic Reminder • HAFIZ ISLAMIC HUB",
                body = "Begin your day with Bismillah, recite a few verses of the Holy Quran, and remember Allah abundantly."
            )
        }
    }

    private fun scheduleSingleAlarm(
        context: Context,
        alarmManager: AlarmManager,
        requestCode: Int,
        triggerAtMillis: Long,
        channelId: String,
        title: String,
        body: String
    ) {
        val intent = Intent(context, PrayerAlarmReceiver::class.java).apply {
            putExtra(EXTRA_NOTIF_ID, requestCode)
            putExtra(EXTRA_CHANNEL, channelId)
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_BODY, body)
        }
        val pi = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pi)
    }
}
