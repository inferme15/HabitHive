package com.habithive.app.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.habithive.app.MainActivity
import com.habithive.app.R
import com.habithive.app.databinding.ActivityLoginBinding

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
                binding.progressBar.visibility = View.GONE
                
                if (task.isSuccessful) {
                    // Login success, update UI with the signed-in user's information
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user
                    Toast.makeText(
                        baseContext, "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
