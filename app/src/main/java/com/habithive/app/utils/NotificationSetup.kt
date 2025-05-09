package com.habithive.app.utils

import android.content.Context

/**
 * Setup daily notifications for habit reminders
 */
fun setupNotifications(context: Context) {
    // Setup a reminder at 8:00 PM
    NotificationUtils.scheduleDailyReminder(context, 20, 0)
}