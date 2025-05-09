package com.habithive.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.habithive.app.MainActivity
import com.habithive.app.R
import com.habithive.app.utils.CHANNEL_ID
import com.habithive.app.utils.DEFAULT_REMINDER_HOUR
import com.habithive.app.utils.DEFAULT_REMINDER_MINUTE
import com.habithive.app.utils.KEY_NOTIFICATIONS_ENABLED
import com.habithive.app.utils.KEY_REMINDER_FRIDAY
import com.habithive.app.utils.KEY_REMINDER_HOUR
import com.habithive.app.utils.KEY_REMINDER_MINUTE
import com.habithive.app.utils.KEY_REMINDER_MONDAY
import com.habithive.app.utils.KEY_REMINDER_SATURDAY
import com.habithive.app.utils.KEY_REMINDER_SUNDAY
import com.habithive.app.utils.KEY_REMINDER_THURSDAY
import com.habithive.app.utils.KEY_REMINDER_TUESDAY
import com.habithive.app.utils.KEY_REMINDER_WEDNESDAY
import com.habithive.app.utils.NotificationScheduler
import com.habithive.app.utils.PREFS_NOTIFICATION_SETTINGS
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {

    private val TAG = "NotificationReceiver"

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "Received intent: ${intent.action}")

        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Schedule reminders if needed after boot
            scheduleRemindersAfterBoot(context)
        } else {
            // Show notification based on intent extras
            val title = intent.getStringExtra("title") ?: context.getString(R.string.notification_title)
            val message = intent.getStringExtra("message") ?: context.getString(R.string.notification_message)
            showNotification(context, title, message)
        }
    }

    private fun scheduleRemindersAfterBoot(context: Context) {
        Log.d(TAG, "Scheduling reminders after boot")

        // Get notification settings from preferences
        val prefs = context.getSharedPreferences(PREFS_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE)

        // Check if notifications are enabled
        val notificationsEnabled = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)

        if (notificationsEnabled) {
            // Get reminder time
            val hour = prefs.getInt(KEY_REMINDER_HOUR, DEFAULT_REMINDER_HOUR)
            val minute = prefs.getInt(KEY_REMINDER_MINUTE, DEFAULT_REMINDER_MINUTE)

            // Get days
            val days = mutableListOf<Int>()
            if (prefs.getBoolean(KEY_REMINDER_MONDAY, true)) {
                days.add(Calendar.MONDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_TUESDAY, true)) {
                days.add(Calendar.TUESDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_WEDNESDAY, true)) {
                days.add(Calendar.WEDNESDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_THURSDAY, true)) {
                days.add(Calendar.THURSDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_FRIDAY, true)) {
                days.add(Calendar.FRIDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_SATURDAY, true)) {
                days.add(Calendar.SATURDAY)
            }
            if (prefs.getBoolean(KEY_REMINDER_SUNDAY, true)) {
                days.add(Calendar.SUNDAY)
            }

            // Schedule notifications
            val scheduler = NotificationScheduler(context)
            scheduler.scheduleNotifications(hour, minute, days)

            Log.d(TAG, "Rescheduled notifications for ${days.size} days at $hour:$minute after boot")
        } else {
            Log.d(TAG, "Notifications are disabled, not scheduling after boot")
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        Log.d(TAG, "Showing notification: $title - $message")

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = context.getString(R.string.channel_description)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }
}