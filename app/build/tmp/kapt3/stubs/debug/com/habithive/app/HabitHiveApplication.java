package com.habithive.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.habithive.app.utils.NotificationScheduler;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/habithive/app/HabitHiveApplication;", "Landroid/app/Application;", "()V", "TAG", "", "createNotificationChannel", "", "initializeNotificationSettings", "onCreate", "scheduleDefaultNotifications", "app_debug"})
public final class HabitHiveApplication extends android.app.Application {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String TAG = "HabitHiveApplication";
    
    public HabitHiveApplication() {
        super();
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final void initializeNotificationSettings() {
    }
    
    private final void scheduleDefaultNotifications() {
    }
}