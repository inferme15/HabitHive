package com.habithive.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.habithive.app.MainActivity
import com.habithive.app.R
import java.util.Calendar

const val CHANNEL_ID = "habithive_notifications"
const val NOTIFICATION_ID = 1001
const val REMINDER_REQUEST_CODE = 2001

// Setup notifications for the user
fun setupNotifications(context: Context) {
    // Schedule daily reminder notification
    scheduleReminderNotification(context)
}

// Schedule daily reminder notification
fun scheduleReminderNotification(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    
    // Create pending intent
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.getBroadcast(
            context,
            REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    } else {
        PendingIntent.getBroadcast(
            context,
            REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    
    // Set reminder for 8:00 AM every day
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 8)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    
    // If time has already passed today, schedule for tomorrow
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    
    // Schedule the alarm
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
}

// Show notification
fun showNotification(context: Context, title: String, message: String) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    
    val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    } else {
        PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
    
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
    
    with(NotificationManagerCompat.from(context)) {
        if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            notify(NOTIFICATION_ID, notification)
        }
    }
}

// Notification Receiver for scheduled notifications
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Show reminder notification
        showNotification(
            context,
            "HabitHive Reminder",
            "Don't forget to track your habits today!"
        )
        
        // Reschedule for the next day
        scheduleReminderNotification(context)
    }
}

// Firebase Messaging Service for push notifications
class FirebaseMessagingService : FirebaseMessagingService() {
    
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        // Check if message contains notification payload
        remoteMessage.notification?.let {
            val title = it.title ?: "HabitHive"
            val message = it.body ?: "You have a new notification"
            
            showNotification(this, title, message)
        }
    }
    
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Handle new FCM token (store on server, etc.)
    }
}
