package com.habithive.app.ui.settings;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.habithive.app.R;
import com.habithive.app.databinding.ActivityNotificationSettingsBinding;
import com.habithive.app.notifications.NotificationScheduler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Activity for managing notification settings
 * Allows users to enable/disable notifications and set reminder times
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0012H\u0002J\u0012\u0010\u0017\u001a\u00020\u00122\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\b\u0010\u001c\u001a\u00020\u0012H\u0002J\b\u0010\u001d\u001a\u00020\u0012H\u0002J\b\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u0012H\u0002J\u0010\u0010 \u001a\u00020\u00122\u0006\u0010!\u001a\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0010\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\f0\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/habithive/app/ui/settings/NotificationSettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/habithive/app/databinding/ActivityNotificationSettingsBinding;", "calendar", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "notificationScheduler", "Lcom/habithive/app/notifications/NotificationScheduler;", "requestPermissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "sharedPreferences", "Landroid/content/SharedPreferences;", "timeFormat", "Ljava/text/SimpleDateFormat;", "checkNotificationPermission", "", "enableNotifications", "enabled", "", "loadSettings", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "saveSettings", "setupListeners", "showPermissionRationaleDialog", "showTimePickerDialog", "updateNotificationSchedule", "updateSettingsVisibility", "visible", "Companion", "app_debug"})
public final class NotificationSettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.habithive.app.databinding.ActivityNotificationSettingsBinding binding;
    private android.content.SharedPreferences sharedPreferences;
    private com.habithive.app.notifications.NotificationScheduler notificationScheduler;
    private final java.util.Calendar calendar = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat timeFormat = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestPermissionLauncher = null;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String PREFS_NAME = "notification_preferences";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_HOUR = "reminder_hour";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_MINUTE = "reminder_minute";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_MONDAY = "reminder_monday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_TUESDAY = "reminder_tuesday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_WEDNESDAY = "reminder_wednesday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_THURSDAY = "reminder_thursday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_FRIDAY = "reminder_friday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_SATURDAY = "reminder_saturday";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String KEY_REMINDER_SUNDAY = "reminder_sunday";
    public static final int DEFAULT_REMINDER_HOUR = 20;
    public static final int DEFAULT_REMINDER_MINUTE = 0;
    @org.jetbrains.annotations.NotNull
    public static final com.habithive.app.ui.settings.NotificationSettingsActivity.Companion Companion = null;
    
    public NotificationSettingsActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void loadSettings() {
    }
    
    private final void setupListeners() {
    }
    
    private final void checkNotificationPermission() {
    }
    
    private final void showPermissionRationaleDialog() {
    }
    
    private final void enableNotifications(boolean enabled) {
    }
    
    private final void updateSettingsVisibility(boolean visible) {
    }
    
    private final void showTimePickerDialog() {
    }
    
    private final void saveSettings() {
    }
    
    private final void updateNotificationSchedule() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/habithive/app/ui/settings/NotificationSettingsActivity$Companion;", "", "()V", "DEFAULT_REMINDER_HOUR", "", "DEFAULT_REMINDER_MINUTE", "KEY_NOTIFICATIONS_ENABLED", "", "KEY_REMINDER_FRIDAY", "KEY_REMINDER_HOUR", "KEY_REMINDER_MINUTE", "KEY_REMINDER_MONDAY", "KEY_REMINDER_SATURDAY", "KEY_REMINDER_SUNDAY", "KEY_REMINDER_THURSDAY", "KEY_REMINDER_TUESDAY", "KEY_REMINDER_WEDNESDAY", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}