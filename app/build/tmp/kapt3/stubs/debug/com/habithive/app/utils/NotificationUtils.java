package com.habithive.app.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t\u00a8\u0006\u000b"}, d2 = {"Lcom/habithive/app/utils/NotificationUtils;", "", "()V", "cancelDailyReminder", "", "context", "Landroid/content/Context;", "scheduleDailyReminder", "hourOfDay", "", "minute", "app_debug"})
public final class NotificationUtils {
    @org.jetbrains.annotations.NotNull
    public static final com.habithive.app.utils.NotificationUtils INSTANCE = null;
    
    private NotificationUtils() {
        super();
    }
    
    /**
     * Schedules a daily reminder notification
     */
    public final void scheduleDailyReminder(@org.jetbrains.annotations.NotNull
    android.content.Context context, int hourOfDay, int minute) {
    }
    
    /**
     * Cancels the daily reminder
     */
    public final void cancelDailyReminder(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
    }
}