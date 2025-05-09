package com.habithive.app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.databinding.ActivityMainBinding
import com.habithive.app.notifications.NotificationReceiver
import com.habithive.app.ui.MainViewModel
import com.habithive.app.ui.exercise.ExerciseActivity
import com.habithive.app.ui.settings.NotificationSettingsActivity
import com.habithive.app.utils.KEY_NOTIFICATIONS_ENABLED
import com.habithive.app.utils.PREFS_NOTIFICATION_SETTINGS

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var viewModel: MainViewModel

    // Permission request launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, set notifications as enabled in preferences
            val prefs = getSharedPreferences(PREFS_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, true).apply()
            Log.d(TAG, "Notification permission granted")
        } else {
            // Permission denied, set notifications as disabled in preferences
            val prefs = getSharedPreferences(PREFS_NOTIFICATION_SETTINGS, Context.MODE_PRIVATE)
            prefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, false).apply()
            Log.d(TAG, "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = MainViewModel()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Setup bottom navigation
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // In your MainActivity or relevant Fragment
        val intent = Intent(this, ExerciseActivity::class.java)
        startActivity(intent)

        // Setup Action Bar with nav controller
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_habits,
                R.id.navigation_leaderboard,
                R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Check for notification permission if on Android 13+
        checkNotificationPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_notification_settings -> {
                // Navigate to notification settings
                val intent = Intent(this, NotificationSettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    /**
     * Check if notification permission is granted and request if needed
     */
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission already granted
                    Log.d(TAG, "Notification permission already granted")
                }
                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    // Explain why the app needs this permission
                    Log.d(TAG, "Should show notification permission rationale")
                    // For simplicity, just request the permission again
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                else -> {
                    // First time asking for permission
                    Log.d(TAG, "Requesting notification permission")
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            // Permission not required for Android < 13
            Log.d(TAG, "Notification permission not required for this Android version")
        }
    }

    /**
     * Register the device to receive boot completed broadcasts
     */
    private fun registerBootReceiver() {
        // Note: This is handled in the manifest with the BOOT_COMPLETED intent filter
        // See NotificationReceiver for implementation
    }
}