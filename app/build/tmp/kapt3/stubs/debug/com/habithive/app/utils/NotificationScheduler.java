package com.habithive.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.habithive.app.notifications.NotificationReceiver;
import java.util.Calendar;

/**
 * Class responsible for scheduling and canceling notifications
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ(\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000bH\u0002J$\u0010\u000f\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/habithive/app/utils/NotificationScheduler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "cancelAllNotifications", "", "scheduleNotificationForDay", "hour", "", "minute", "dayOfWeek", "requestCode", "scheduleNotifications", "days", "", "app_debug"})
public final class NotificationScheduler {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String TAG = "NotificationScheduler";
    
    public NotificationScheduler(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Schedule notifications for specific days of the week at a specific time
     *
     * @param hour Hour of the day (24-hour format)
     * @param minute Minute of the hour
     * @param days List of Calendar constants representing days of the week
     */
    public final void scheduleNotifications(int hour, int minute, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> days) {
    }
    
    /**
     * Schedule a notification for a specific day of the week
     *
     * @param hour Hour of the day (24-hour format)
     * @param minute Minute of the hour
     * @param dayOfWeek Calendar constant representing the day of the week
     * @param requestCode Unique identifier for the PendingIntent
     */
    private final void scheduleNotificationForDay(int hour, int minute, int dayOfWeek, int requestCode) {
    }
    
    /**
     * Cancel all scheduled notifications
     */
    public final void cancelAllNotifications() {
    }
}