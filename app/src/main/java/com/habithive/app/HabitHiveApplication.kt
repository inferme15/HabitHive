package com.habithive.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import com.google.firebase.FirebaseApp
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

class HabitHiveApplication : Application() {

    private val TAG = "HabitHiveApplication"

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create notification channel for Android O and above
        createNotificationChannel()

        // Initialize notification settings
        initializeNotificationSettings()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initializeNotificationSettings() {
        val prefs = getSharedPreferences(PREFS_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE)

        // Check if this is the first run
        if (!prefs.contains(KEY_NOTIFICATIONS_ENABLED)) {
            Log.d(TAG, "First run, setting up default notification settings")

            // Set up default values
            prefs.edit().apply {
                // Default notification settings
                putBoolean(KEY_NOTIFICATIONS_ENABLED, true)
                putInt(KEY_REMINDER_HOUR, DEFAULT_REMINDER_HOUR)
                putInt(KEY_REMINDER_MINUTE, DEFAULT_REMINDER_MINUTE)
                putBoolean(KEY_REMINDER_MONDAY, true)
                putBoolean(KEY_REMINDER_TUESDAY, true)
                putBoolean(KEY_REMINDER_WEDNESDAY, true)
                putBoolean(KEY_REMINDER_THURSDAY, true)
                putBoolean(KEY_REMINDER_FRIDAY, true)
                putBoolean(KEY_REMINDER_SATURDAY, true)
                putBoolean(KEY_REMINDER_SUNDAY, true)

                apply()
            }

            // Schedule notifications based on defaults
            scheduleDefaultNotifications()
        }
    }

    private fun scheduleDefaultNotifications() {
        val prefs = getSharedPreferences(PREFS_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE)

        // Check if notifications are enabled
        val notificationsEnabled = prefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)

        if (notificationsEnabled) {
            // Get default reminder time
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
            val scheduler = NotificationScheduler(this)
            scheduler.scheduleNotifications(hour, minute, days)

            Log.d(TAG, "Scheduled default notifications for ${days.size} days at $hour:$minute")
        }
    }
}