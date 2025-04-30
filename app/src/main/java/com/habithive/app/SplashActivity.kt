package com.habithive.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.habithive.app.ui.auth.LoginActivity

class SplashActivity : AppCompatActivity() {
    
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
            // User is signed in, go to main activity
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // No user signed in, go to login
            startActivity(Intent(this, LoginActivity::class.java))
        }
        
        // Close splash activity
        finish()
    }
}
