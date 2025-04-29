package com.habithive.app.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.databinding.ActivityEditProfileBinding
import com.habithive.app.model.User

class EditProfileActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Setup back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Profile"
        
        // Setup gender spinner
        setupGenderSpinner()
        
        // Load current user data
        loadUserData()
        
        // Setup save button
        binding.buttonSave.setOnClickListener {
            saveUserData()
        }
    }
    
    private fun setupGenderSpinner() {
        val genders = arrayOf("Male", "Female", "Other", "Prefer not to say")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter
    }
    
    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        
        binding.progressBar.visibility = View.VISIBLE
        
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                binding.progressBar.visibility = View.GONE
                
                if (document != null && document.exists()) {
                    val user = document.toObject(User::class.java)
                    user?.let {
                        binding.editTextName.setText(it.name)
                        
                        // Set gender spinner
                        when (it.gender) {
                            "Male" -> binding.spinnerGender.setSelection(0)
                            "Female" -> binding.spinnerGender.setSelection(1)
                            "Other" -> binding.spinnerGender.setSelection(2)
                            "Prefer not to say" -> binding.spinnerGender.setSelection(3)
                        }
                        
                        binding.editTextHealth.setText(it.health)
                        
                        if (it.height > 0) {
                            binding.editTextHeight.setText(it.height.toString())
                        }
                        
                        if (it.weight > 0) {
                            binding.editTextWeight.setText(it.weight.toString())
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error loading user data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun saveUserData() {
        val userId = auth.currentUser?.uid ?: return
        val email = auth.currentUser?.email ?: return
        
        val name = binding.editTextName.text.toString().trim()
        val gender = binding.spinnerGender.selectedItem.toString()
        val health = binding.editTextHealth.text.toString().trim()
        val height = binding.editTextHeight.text.toString().toDoubleOrNull() ?: 0.0
        val weight = binding.editTextWeight.text.toString().toDoubleOrNull() ?: 0.0
        
        // Validate input
        if (name.isEmpty()) {
            binding.editTextName.error = "Name is required"
            binding.editTextName.requestFocus()
            return
        }
        
        // Show progress
        binding.progressBar.visibility = View.VISIBLE
        
        // Create user object
        val user = User(
            id = userId,
            name = name,
            email = email,
            gender = gender,
            health = health,
            height = height,
            weight = weight
        )
        
        // Save to Firestore
        firestore.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
