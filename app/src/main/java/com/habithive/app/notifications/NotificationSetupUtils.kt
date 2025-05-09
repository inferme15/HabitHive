package com.habithive.app.notifications

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.habithive.app.ui.settings.NotificationSettingsActivity

/**
 * Utility functions for setting up notifications on app startup
 */
object NotificationSetupUtils {
    
    private const val TAG = "NotificationSetupUtils"
    
    /**
     * Initialize notification settings with defaults if not already set
     * @param context Application context
     */
    fun initializeNotificationSettings(context: Context) {
        val prefs = context.getSharedPreferences(
            NotificationSettingsActivity.PREFS_NAME,
            Context.MODE_PRIVATE
        )
        
        // Check if this is the first run
        if (!prefs.contains(PREF_FIRST_RUN)) {
            Log.d(TAG, "First run, setting up default notification settings")
            
            // Set up default values
            prefs.edit().apply {
                // Mark as not first run anymore
                putBoolean(PREF_FIRST_RUN, false)
                
                // Default notification settings
                putBoolean(NotificationSettingsActivity.KEY_NOTIFICATIONS_ENABLED, true)
                putInt(NotificationSettingsActivity.KEY_REMINDER_HOUR, NotificationSettingsActivity.DEFAULT_REMINDER_HOUR)
                putInt(NotificationSettingsActivity.KEY_REMINDER_MINUTE, NotificationSettingsActivity.DEFAULT_REMINDER_MINUTE)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_MONDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_TUESDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_WEDNESDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_THURSDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_FRIDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_SATURDAY, true)
                putBoolean(NotificationSettingsActivity.KEY_REMINDER_SUNDAY, true)
                
                apply()
            }
            
            // Schedule notifications based on defaults
            scheduleDefaultNotifications(context)
        }
    }
    
    /**
     * Schedule notifications based on default settings
     */
    private fun scheduleDefaultNotifications(context: Context) {
        val prefs = context.getSharedPreferences(
            NotificationSettingsActivity.PREFS_NAME,
            Context.MODE_PRIVATE
        )
        
        // Check if notifications are enabled
        val notificationsEnabled = prefs.getBoolean(
            NotificationSettingsActivity.KEY_NOTIFICATIONS_ENABLED,
            false
        )
        
        if (notificationsEnabled) {
            // Get default reminder time
            val hour = prefs.getInt(
                NotificationSettingsActivity.KEY_REMINDER_HOUR,
                NotificationSettingsActivity.DEFAULT_REMINDER_HOUR
            )
            val minute = prefs.getInt(
                NotificationSettingsActivity.KEY_REMINDER_MINUTE,
                NotificationSettingsActivity.DEFAULT_REMINDER_MINUTE
            )
            
            // Get days
            val days = mutableListOf<Int>()
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_MONDAY, true)) {
                days.add(java.util.Calendar.MONDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_TUESDAY, true)) {
                days.add(java.util.Calendar.TUESDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_WEDNESDAY, true)) {
                days.add(java.util.Calendar.WEDNESDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_THURSDAY, true)) {
                days.add(java.util.Calendar.THURSDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_FRIDAY, true)) {
                days.add(java.util.Calendar.FRIDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_SATURDAY, true)) {
                days.add(java.util.Calendar.SATURDAY)
            }
            if (prefs.getBoolean(NotificationSettingsActivity.KEY_REMINDER_SUNDAY, true)) {
                days.add(java.util.Calendar.SUNDAY)
            }
            
            // Schedule notifications
            val scheduler = NotificationScheduler(context)
            scheduler.scheduleNotifications(hour, minute, days)
            
            Log.d(TAG, "Scheduled default notifications for ${days.size} days at $hour:$minute")
        }
    }
    
    private const val PREF_FIRST_RUN = "first_run"
}