package com.habithive.app.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import java.util.Calendar;

/**
 * Handles scheduling and canceling notifications
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ$\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000eR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/habithive/app/notifications/NotificationScheduler;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "alarmManager", "Landroid/app/AlarmManager;", "cancelAllNotifications", "", "scheduleNotifications", "hour", "", "minute", "days", "", "Companion", "app_debug"})
public final class NotificationScheduler {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final android.app.AlarmManager alarmManager = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TAG = "NotificationScheduler";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXTRA_NOTIFICATION_ID = "notification_id";
    @org.jetbrains.annotations.NotNull
    public static final com.habithive.app.notifications.NotificationScheduler.Companion Companion = null;
    
    public NotificationScheduler(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    /**
     * Schedule daily notifications for specified days of the week at the given time
     * @param hour Hour of the day (0-23)
     * @param minute Minute of the hour (0-59)
     * @param days List of Calendar days of week (e.g. Calendar.MONDAY)
     */
    public final void scheduleNotifications(int hour, int minute, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> days) {
    }
    
    /**
     * Cancel all scheduled notifications
     */
    public final void cancelAllNotifications() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2 = {"Lcom/habithive/app/notifications/NotificationScheduler$Companion;", "", "()V", "EXTRA_NOTIFICATION_ID", "", "TAG", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}