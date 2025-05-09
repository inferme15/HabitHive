package com.habithive.app.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.databinding.ActivityEditProfileBinding

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

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.edit_profile)

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
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,  // Make sure to create this array in strings.xml
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerGender.adapter = adapter
        }
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            binding.progressBar.visibility = View.VISIBLE

            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        binding.editTextName.setText(document.getString("name") ?: "")

                        // Set spinner selection based on gender
                        val gender = document.getString("gender") ?: ""
                        val genderArray = resources.getStringArray(R.array.gender_array)
                        val genderPosition = genderArray.indexOf(gender)
                        if (genderPosition >= 0) {
                            binding.spinnerGender.setSelection(genderPosition)
                        }

                        binding.editTextHealth.setText(document.getString("health") ?: "")

                        // Handle height which might be a number
                        val heightValue = document.get("height")
                        binding.editTextHeight.setText(heightValue?.toString() ?: "")

                        // Handle weight which might be a number
                        val weightValue = document.get("weight")
                        binding.editTextWeight.setText(weightValue?.toString() ?: "")
                    }
                    binding.progressBar.visibility = View.GONE
                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error loading profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            // Validate inputs
            val name = binding.editTextName.text.toString().trim()
            if (name.isEmpty()) {
                binding.editTextName.error = "Name is required"
                binding.editTextName.requestFocus()
                return
            }

            val gender = binding.spinnerGender.selectedItem.toString()
            val health = binding.editTextHealth.text.toString().trim()
            val heightText = binding.editTextHeight.text.toString().trim()
            val weightText = binding.editTextWeight.text.toString().trim()

            binding.progressBar.visibility = View.VISIBLE

            // Create user data map with appropriate types
            val userData = HashMap<String, Any>().apply {
                put("name", name)
                put("gender", gender)
                put("health", health)

                // Convert height to Double if possible
                try {
                    if (heightText.isNotEmpty()) {
                        put("height", heightText.toDouble())
                    } else {
                        put("height", 0.0)
                    }
                } catch (e: Exception) {
                    put("height", heightText)
                }

                // Convert weight to Double if possible
                try {
                    if (weightText.isNotEmpty()) {
                        put("weight", weightText.toDouble())
                    } else {
                        put("weight", 0.0)
                    }
                } catch (e: Exception) {
                    put("weight", weightText)
                }
            }

            // Update in Firestore
            firestore.collection("users").document(userId)
                .update(userData)
                .addOnSuccessListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close activity and go back
                }
                .addOnFailureListener { e ->
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}