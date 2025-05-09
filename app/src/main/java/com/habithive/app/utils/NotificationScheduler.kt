package com.habithive.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.habithive.app.notifications.NotificationReceiver
import java.util.Calendar

/**
 * Class responsible for scheduling and canceling notifications
 */
class NotificationScheduler(private val context: Context) {

    private val TAG = "NotificationScheduler"

    /**
     * Schedule notifications for specific days of the week at a specific time
     *
     * @param hour Hour of the day (24-hour format)
     * @param minute Minute of the hour
     * @param days List of Calendar constants representing days of the week
     */
    fun scheduleNotifications(hour: Int, minute: Int, days: List<Int>) {
        // Cancel any existing notifications
        cancelAllNotifications()

        // Schedule new notifications for each selected day
        days.forEachIndexed { index, dayOfWeek ->
            scheduleNotificationForDay(hour, minute, dayOfWeek, index)
        }

        Log.d(TAG, "Scheduled notifications for ${days.size} days at $hour:$minute")
    }

    /**
     * Schedule a notification for a specific day of the week
     *
     * @param hour Hour of the day (24-hour format)
     * @param minute Minute of the hour
     * @param dayOfWeek Calendar constant representing the day of the week
     * @param requestCode Unique identifier for the PendingIntent
     */
    private fun scheduleNotificationForDay(hour: Int, minute: Int, dayOfWeek: Int, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Create the intent for the notification
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", context.getString(com.habithive.app.R.string.notification_title))
            putExtra("message", context.getString(com.habithive.app.R.string.notification_message))
        }

        // Create a unique pending intent for each day
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the alarm to start at the specified time on the specified day
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // Set the day of the week
            set(Calendar.DAY_OF_WEEK, dayOfWeek)

            // If the time has already passed this week, set it for next week
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        // Schedule the alarm to repeat every week
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7, // Weekly
            pendingIntent
        )

        Log.d(TAG, "Scheduled notification for day $dayOfWeek at $hour:$minute (requestCode: $requestCode)")
    }

    /**
     * Cancel all scheduled notifications
     */
    fun cancelAllNotifications() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Cancel each possible pending intent
        // Since we're using request codes 0-6 for the days of the week
        for (i in 0..6) {
            val intent = Intent(context, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                i,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Cancel the alarm
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
        }

        Log.d(TAG, "Canceled all notifications")
    }
}