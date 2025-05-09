package com.habithive.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar

/**
 * Handles scheduling and canceling notifications
 */
class NotificationScheduler(private val context: Context) {
    
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    
    /**
     * Schedule daily notifications for specified days of the week at the given time
     * @param hour Hour of the day (0-23)
     * @param minute Minute of the hour (0-59)
     * @param days List of Calendar days of week (e.g. Calendar.MONDAY)
     */
    fun scheduleNotifications(hour: Int, minute: Int, days: List<Int>) {
        // Cancel existing notifications
        cancelAllNotifications()
        
        if (days.isEmpty()) {
            Log.d(TAG, "No days selected for notifications")
            return
        }
        
        // Set up a notification for each selected day
        days.forEachIndexed { index, dayOfWeek ->
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra(EXTRA_NOTIFICATION_ID, dayOfWeek)
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                dayOfWeek,  // Request code is the day of week
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            
            // Calculate the next occurrence of the specified day and time
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                
                // If the specified time is earlier than current time, schedule for the next occurrence
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
                
                // Adjust to the next occurrence of the specified day
                while (get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            
            // Schedule the alarm using the appropriate method based on Android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }
            
            Log.d(TAG, "Scheduled notification for day $dayOfWeek at ${calendar.time}")
        }
        
        Log.d(TAG, "Scheduled notifications for ${days.size} days")
    }
    
    /**
     * Cancel all scheduled notifications
     */
    fun cancelAllNotifications() {
        // Cancel all potential day-of-week notifications
        for (dayOfWeek in 1..7) { // Calendar.SUNDAY=1 to Calendar.SATURDAY=7
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra(EXTRA_NOTIFICATION_ID, dayOfWeek)
            
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                dayOfWeek,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )
            
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent)
                pendingIntent.cancel()
                Log.d(TAG, "Canceled notification for day $dayOfWeek")
            }
        }
        
        Log.d(TAG, "Canceled all notifications")
    }
    
    companion object {
        private const val TAG = "NotificationScheduler"
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }
}