package com.habithive.app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.MainActivity
import com.habithive.app.databinding.ActivityLoginBinding
import com.habithive.app.ui.profile.EditProfileActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Setup login button
        binding.buttonLogin.setOnClickListener {
            loginUser()
        }

        // Setup register button
        binding.buttonRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        // Validate input
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email is required"
            binding.editTextEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is required"
            binding.editTextPassword.requestFocus()
            return
        }

        // Show progress bar
        binding.progressBar.visibility = View.VISIBLE

        // Authenticate with Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login success, check if profile is complete
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        checkUserProfileCompletion(userId)
                    } else {
                        // Shouldn't happen, but just in case
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            baseContext, "Authentication error: Unable to get user ID",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // If sign in fails, display a message to the user
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        baseContext, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkUserProfileCompletion(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                binding.progressBar.visibility = View.GONE

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
                        Log.d("LoginActivity", "Profile is complete, navigating to main screen")
                        navigateToHome()
                    } else {
                        // Profile is incomplete, go to Profile
                        Log.d("LoginActivity", "Profile is incomplete, navigating to profile setup")
                        navigateToProfile()
                    }
                } else {
                    // User document doesn't exist, go to Profile
                    Log.d("LoginActivity", "User document doesn't exist, navigating to profile setup")
                    navigateToProfile()
                }
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                // Error occurred, go to Home anyway and let the user fix profile later
                Log.e("LoginActivity", "Error checking profile: ${e.message}", e)
                navigateToHome()
            }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        intent.putExtra("FIRST_TIME_SETUP", false) // Not first time, but needs completion
        startActivity(intent)
        finish()
    }
}
