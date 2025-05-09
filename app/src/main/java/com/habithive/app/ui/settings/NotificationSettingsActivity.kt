package com.habithive.app.ui.settings

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.habithive.app.R
import com.habithive.app.databinding.ActivityNotificationSettingsBinding
import com.habithive.app.notifications.NotificationScheduler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Activity for managing notification settings
 * Allows users to enable/disable notifications and set reminder times
 */
class NotificationSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notificationScheduler: NotificationScheduler

    private val calendar = Calendar.getInstance()
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    // Permission request launcher for notification permissions
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            enableNotifications(true)
            updateNotificationSchedule()
        } else {
            binding.switchEnableNotifications.isChecked = false
            Toast.makeText(
                this,
                getString(R.string.notification_permission_denied),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityNotificationSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Initialize notification scheduler
        notificationScheduler = NotificationScheduler(this)

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.notification_settings)

        // Load current settings
        loadSettings()

        // Setup UI listeners
        setupListeners()
    }

    private fun loadSettings() {
        // Load notification enabled state
        val notificationsEnabled = sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, false)
        binding.switchEnableNotifications.isChecked = notificationsEnabled

        // Update UI state based on notification toggle
        updateSettingsVisibility(notificationsEnabled)

        // Load daily reminder time
        val reminderHour = sharedPreferences.getInt(KEY_REMINDER_HOUR, DEFAULT_REMINDER_HOUR)
        val reminderMinute = sharedPreferences.getInt(KEY_REMINDER_MINUTE, DEFAULT_REMINDER_MINUTE)

        // Set calendar time for display
        calendar.set(Calendar.HOUR_OF_DAY, reminderHour)
        calendar.set(Calendar.MINUTE, reminderMinute)

        // Update time display
        binding.textReminderTime.text = timeFormat.format(calendar.time)

        // Load reminder days
        binding.checkboxMonday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_MONDAY, true)
        binding.checkboxTuesday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_TUESDAY, true)
        binding.checkboxWednesday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_WEDNESDAY, true)
        binding.checkboxThursday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_THURSDAY, true)
        binding.checkboxFriday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_FRIDAY, true)
        binding.checkboxSaturday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_SATURDAY, true)
        binding.checkboxSunday.isChecked = sharedPreferences.getBoolean(KEY_REMINDER_SUNDAY, true)
    }

    private fun setupListeners() {
        // Notification toggle
        binding.switchEnableNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Check for notification permission
                checkNotificationPermission()
            } else {
                // Disable notifications
                enableNotifications(false)
                updateSettingsVisibility(false)
            }
        }

        // Time picker
        binding.layoutReminderTime.setOnClickListener {
            showTimePickerDialog()
        }

        // Day checkboxes
        val dayCheckboxListener = { _: Boolean ->
            saveSettings()
            updateNotificationSchedule()
        }

        binding.checkboxMonday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxTuesday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxWednesday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxThursday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxFriday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxSaturday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
        binding.checkboxSunday.setOnCheckedChangeListener { _, isChecked -> dayCheckboxListener(isChecked) }
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted
                    enableNotifications(true)
                    updateSettingsVisibility(true)
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Show permission rationale
                    binding.switchEnableNotifications.isChecked = false
                    showPermissionRationaleDialog()
                }
                else -> {
                    // Request permission
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // For Android < 13, permission is granted at install time
            enableNotifications(true)
            updateSettingsVisibility(true)
        }
    }

    private fun showPermissionRationaleDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle(R.string.notification_permission_title)
            .setMessage(R.string.notification_permission_rationale)
            .setPositiveButton(R.string.request_permission) { _, _ ->
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun enableNotifications(enabled: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply()

        if (enabled) {
            updateNotificationSchedule()
        } else {
            notificationScheduler.cancelAllNotifications()
        }
    }

    private fun updateSettingsVisibility(visible: Boolean) {
        val visibility = if (visible) android.view.View.VISIBLE else android.view.View.GONE
        binding.layoutNotificationOptions.visibility = visibility
    }

    private fun showTimePickerDialog() {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // Update calendar with selected time
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                // Update time display
                binding.textReminderTime.text = timeFormat.format(calendar.time)

                // Save settings and update schedule
                sharedPreferences.edit()
                    .putInt(KEY_REMINDER_HOUR, selectedHour)
                    .putInt(KEY_REMINDER_MINUTE, selectedMinute)
                    .apply()

                updateNotificationSchedule()
            },
            hour,
            minute,
            false
        ).show()
    }

    private fun saveSettings() {
        sharedPreferences.edit()
            .putBoolean(KEY_REMINDER_MONDAY, binding.checkboxMonday.isChecked)
            .putBoolean(KEY_REMINDER_TUESDAY, binding.checkboxTuesday.isChecked)
            .putBoolean(KEY_REMINDER_WEDNESDAY, binding.checkboxWednesday.isChecked)
            .putBoolean(KEY_REMINDER_THURSDAY, binding.checkboxThursday.isChecked)
            .putBoolean(KEY_REMINDER_FRIDAY, binding.checkboxFriday.isChecked)
            .putBoolean(KEY_REMINDER_SATURDAY, binding.checkboxSaturday.isChecked)
            .putBoolean(KEY_REMINDER_SUNDAY, binding.checkboxSunday.isChecked)
            .apply()
    }

    private fun updateNotificationSchedule() {
        if (!sharedPreferences.getBoolean(KEY_NOTIFICATIONS_ENABLED, false)) {
            return
        }

        // Cancel existing notifications
        notificationScheduler.cancelAllNotifications()

        // Get days to schedule
        val days = mutableListOf<Int>()
        if (binding.checkboxMonday.isChecked) days.add(Calendar.MONDAY)
        if (binding.checkboxTuesday.isChecked) days.add(Calendar.TUESDAY)
        if (binding.checkboxWednesday.isChecked) days.add(Calendar.WEDNESDAY)
        if (binding.checkboxThursday.isChecked) days.add(Calendar.THURSDAY)
        if (binding.checkboxFriday.isChecked) days.add(Calendar.FRIDAY)
        if (binding.checkboxSaturday.isChecked) days.add(Calendar.SATURDAY)
        if (binding.checkboxSunday.isChecked) days.add(Calendar.SUNDAY)

        // Get time
        val hour = sharedPreferences.getInt(KEY_REMINDER_HOUR, DEFAULT_REMINDER_HOUR)
        val minute = sharedPreferences.getInt(KEY_REMINDER_MINUTE, DEFAULT_REMINDER_MINUTE)

        // Schedule notifications
        notificationScheduler.scheduleNotifications(hour, minute, days)

        Toast.makeText(
            this,
            getString(R.string.notifications_updated),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        // Shared Preferences
        const val PREFS_NAME = "notification_preferences"

        // Keys
        const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        const val KEY_REMINDER_HOUR = "reminder_hour"
        const val KEY_REMINDER_MINUTE = "reminder_minute"
        const val KEY_REMINDER_MONDAY = "reminder_monday"
        const val KEY_REMINDER_TUESDAY = "reminder_tuesday"
        const val KEY_REMINDER_WEDNESDAY = "reminder_wednesday"
        const val KEY_REMINDER_THURSDAY = "reminder_thursday"
        const val KEY_REMINDER_FRIDAY = "reminder_friday"
        const val KEY_REMINDER_SATURDAY = "reminder_saturday"
        const val KEY_REMINDER_SUNDAY = "reminder_sunday"

        // Default values
        const val DEFAULT_REMINDER_HOUR = 20 // 8 PM
        const val DEFAULT_REMINDER_MINUTE = 0
    }
}