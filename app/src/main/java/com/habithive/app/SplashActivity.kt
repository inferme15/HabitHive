package com.habithive.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.ui.auth.LoginActivity
import com.habithive.app.ui.profile.EditProfileActivity

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Delay for 2 seconds then check authentication status
        Handler(Looper.getMainLooper()).postDelayed({
            checkAuthenticationStatus()
        }, 2000)
    }

    private fun checkAuthenticationStatus() {
        // Check if user is already signed in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is signed in, check profile completion
            Log.d(TAG, "User is already logged in, checking profile completion")
            checkUserProfileCompletion(currentUser.uid)
        } else {
            // No user signed in, go to login
            Log.d(TAG, "User is not logged in, navigating to login screen")
            navigateToLogin()
        }
    }

    private fun checkUserProfileCompletion(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Check if profile is complete
                    val name = document.getString("name")
                    val gender = document.getString("gender")
                    val height = document.getDouble("height")
                    val weight = document.getDouble("weight")

                    val isProfileComplete = !name.isNullOrEmpty() &&
                            !gender.isNullOrEmpty() &&
                            height != null && height > 0.0 &&
                            weight != null && weight > 0.0

                    if (isProfileComplete) {
                        // Profile is complete, go to Home
                        Log.d(TAG, "Profile is complete, navigating to main screen")
                        navigateToHome() // ✅ FIXED: Goes to MainActivity (Home), not Exercise
                    } else {
                        // Profile is incomplete, go to Profile
                        Log.d(TAG, "Profile is incomplete, navigating to profile setup")
                        navigateToProfile()
                    }
                } else {
                    // User document doesn't exist, go to Profile
                    Log.d(TAG, "User document doesn't exist, navigating to profile setup")
                    navigateToProfile()
                }
            }
            .addOnFailureListener { e ->
                // Error occurred, default to Login
                Log.e(TAG, "Error checking profile: ${e.message}", e)
                navigateToLogin()
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java) // ✅ Ensured Home is default
        startActivity(intent)
        finish()
    }

    private fun navigateToProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        intent.putExtra("FIRST_TIME_SETUP", true)
        startActivity(intent)
        finish()
    }
}
