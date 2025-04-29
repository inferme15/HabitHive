package com.habithive.app.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.MainActivity
import com.habithive.app.databinding.ActivityRegisterBinding
import com.habithive.app.model.User

class RegisterActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase Auth and Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Setup register button
        binding.buttonRegister.setOnClickListener {
            registerUser()
        }
        
        // Setup login button (to go back to login)
        binding.buttonLogin.setOnClickListener {
            finish()
        }
    }
    
    private fun registerUser() {
        val name = binding.editTextName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
        
        // Validate input
        if (name.isEmpty()) {
            binding.editTextName.error = "Name is required"
            binding.editTextName.requestFocus()
            return
        }
        
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
        
        if (password.length < 6) {
            binding.editTextPassword.error = "Password must be at least 6 characters"
            binding.editTextPassword.requestFocus()
            return
        }
        
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Passwords do not match"
            binding.editTextConfirmPassword.requestFocus()
            return
        }
        
        // Show progress bar
        binding.progressBar.visibility = View.VISIBLE
        
        // Create user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registration success, now create user document in Firestore
                    val userId = auth.currentUser?.uid
                    
                    if (userId != null) {
                        val user = User(
                            id = userId,
                            name = name,
                            email = email,
                            gender = "",
                            health = "",
                            height = 0.0,
                            weight = 0.0
                        )
                        
                        // Save user to Firestore
                        firestore.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                // Initialize achievements document
                                val achievements = hashMapOf(
                                    "totalPoints" to 0,
                                    "caloriesBurned" to 0,
                                    "dailyScore" to 0,
                                    "weeklyScore" to 0
                                )
                                
                                firestore.collection("achievements")
                                    .document(userId)
                                    .set(achievements)
                                    .addOnSuccessListener {
                                        binding.progressBar.visibility = View.GONE
                                        
                                        // Registration complete, go to MainActivity
                                        startActivity(Intent(this, MainActivity::class.java))
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(
                                            baseContext, "Error initializing user data: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                            .addOnFailureListener { e ->
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(
                                    baseContext, "Error creating user profile: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // If registration fails, display a message to the user
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        baseContext, "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
