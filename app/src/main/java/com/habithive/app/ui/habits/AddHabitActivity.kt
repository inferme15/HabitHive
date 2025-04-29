package com.habithive.app.ui.habits

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.databinding.ActivityAddHabitBinding
import com.habithive.app.model.Habit
import java.util.UUID

class AddHabitActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddHabitBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityAddHabitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Setup back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Habit"
        
        // Setup spinners
        setupSpinners()
        
        // Setup save button
        binding.buttonSave.setOnClickListener {
            saveHabit()
        }
    }
    
    private fun setupSpinners() {
        // Setup type spinner
        val habitTypes = arrayOf("Running", "Cycling", "Weightlifting", "Custom")
        val typeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitTypes)
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = typeAdapter
        
        // Setup frequency spinner
        val habitFrequencies = arrayOf("Daily", "Weekly", "Custom")
        val frequencyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitFrequencies)
        frequencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFrequency.adapter = frequencyAdapter
    }
    
    private fun saveHabit() {
        val userId = auth.currentUser?.uid ?: return
        
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val type = binding.spinnerType.selectedItem.toString()
        val frequency = binding.spinnerFrequency.selectedItem.toString()
        val points = binding.editTextPoints.text.toString().toIntOrNull() ?: 0
        val calories = binding.editTextCalories.text.toString().toIntOrNull() ?: 0
        
        // Validate input
        if (title.isEmpty()) {
            binding.editTextTitle.error = "Title is required"
            binding.editTextTitle.requestFocus()
            return
        }
        
        // Show progress
        binding.progressBar.visibility = View.VISIBLE
        
        // Create habit object
        val habit = Habit(
            id = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            description = description,
            type = type,
            frequency = frequency,
            points = points,
            caloriesBurned = calories
        )
        
        // Save to Firestore
        firestore.collection("habits")
            .document(habit.id)
            .set(habit)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Habit added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error adding habit: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
