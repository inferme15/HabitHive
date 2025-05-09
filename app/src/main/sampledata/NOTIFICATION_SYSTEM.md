# HabitHive Notification System

## Overview

The HabitHive notification system provides a comprehensive solution for scheduling and managing reminders within the app. It allows users to:

- Enable/disable notifications
- Set specific times for reminders
- Choose which days of the week to receive reminders
- Manage notification permissions (handled automatically for Android 13+)

## Components

### Core Classes

1. **NotificationConstants.kt**
    - Contains constants for notification channel ID, preference keys, and default values
    - Used throughout the notification system for consistency

2. **NotificationScheduler.kt**
    - Handles scheduling notifications for specific days and times
    - Uses the AlarmManager to set repeating weekly notifications
    - Can cancel previously scheduled notifications

3. **NotificationReceiver.kt**
    - BroadcastReceiver that handles incoming notifications
    - Shows notifications to the user
    - Handles boot completed broadcasts to reschedule notifications after device restart

4. **FirebaseMessagingServiceImpl.kt**
    - Handles Firebase Cloud Messaging (FCM) for remote notifications
    - Displays notifications received from Firebase

5. **NotificationSettingsActivity.kt**
    - User interface for configuring notification preferences
    - Allows users to enable/disable notifications, select time and days

6. **HabitHiveApplication.kt**
    - Initializes notification settings on app startup
    - Creates the notification channel (required for Android 8.0+)

### Layouts and Resources

1. **activity_notification_settings.xml**
    - Layout for the notification settings screen
    - Contains UI components for time picker, day selection, and enable/disable toggle

2. **main_menu.xml**
    - Menu resource containing the "Notification Settings" action
    - Integrated into MainActivity's options menu

3. **ic_notification.xml**
    - Vector drawable for the notification icon

## Integration Steps

1. **AndroidManifest.xml Updates**
    - Add required permissions: `POST_NOTIFICATIONS` (Android 13+), `RECEIVE_BOOT_COMPLETED`, `VIBRATE`
    - Register the notification receiver with the `BOOT_COMPLETED` intent filter
    - Register the Firebase messaging service
    - Add the NotificationSettingsActivity to the manifest

2. **MainActivity Integration**
    - Add menu inflation for the notification settings option
    - Request notification permission for Android 13+
    - Navigate to notification settings when the menu item is selected

3. **Application Class Setup**
    - Create the notification channel in onCreate()
    - Initialize default notification settings if not already set

## Usage

### Scheduling Notifications

```kotlin
// Schedule notifications for specified days at specified time
val scheduler = NotificationScheduler(context)
val days = listOf(Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY)
scheduler.scheduleNotifications(20, 0, days) // 8:00 PM on Mon, Wed, Fri
```

### Canceling Notifications

```kotlin
// Cancel all scheduled notifications
val scheduler = NotificationScheduler(context)
scheduler.cancelAllNotifications()
```

### Showing an Immediate Notification

```kotlin
// Show a notification immediately
val intent = Intent(context, NotificationReceiver::class.java)
intent.putExtra("title", "Custom Title")
intent.putExtra("message", "Custom Message")
context.sendBroadcast(intent)
```

## User Permissions Flow

1. On app startup (for Android 13+), the app checks for notification permission
2. If not granted, a permission request is shown to the user
3. The user's choice is stored in preferences
4. The notification settings UI reflects the permission status

## Notification Settings Persistence

All notification settings are stored in SharedPreferences with the name defined in `PREFS_NOTIFICATION_SETTINGS`. The following settings are saved:

- Notifications enabled/disabled
- Reminder time (hour and minute)
- Reminder days (individual boolean for each day of the week)

## Implementation Notes

1. The notification scheduler uses a unique request code for each day of the week to allow for separate alarms
2. Default settings (8:00 PM daily) are applied on first app run
3. The notification channel must be created before any notifications can be displayed on Android 8.0+
4. The permission request is only needed for Android 13+ (Tiramisu)

## Troubleshooting

1. **Notifications not showing**
    - Check that permissions are granted
    - Verify the notification channel is created
    - Ensure notifications are enabled in device settings
    - Check that alarms are not being killed by battery optimization

2. **Notifications not rescheduled after reboot**
    - Ensure the `RECEIVE_BOOT_COMPLETED` permission is declared
    - Verify the receiver is properly registered in the manifest
    - Check that the NotificationReceiver's onReceive method handles the BOOT_COMPLETED action