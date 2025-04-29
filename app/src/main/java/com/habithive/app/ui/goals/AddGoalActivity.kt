package com.habithive.app.ui.goals

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.databinding.ActivityAddGoalBinding
import com.habithive.app.model.Goal
import java.util.UUID

class AddGoalActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAddGoalBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Setup back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Goal"
        
        // Setup duration spinner
        setupDurationSpinner()
        
        // Setup save button
        binding.buttonSave.setOnClickListener {
            saveGoal()
        }
    }
    
    private fun setupDurationSpinner() {
        val durations = arrayOf("Daily", "Weekly", "Monthly")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, durations)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDuration.adapter = adapter
    }
    
    private fun saveGoal() {
        val userId = auth.currentUser?.uid ?: return
        
        val title = binding.editTextTitle.text.toString().trim()
        val description = binding.editTextDescription.text.toString().trim()
        val targetPoints = binding.editTextTargetPoints.text.toString().toIntOrNull() ?: 0
        val targetCalories = binding.editTextTargetCalories.text.toString().toIntOrNull() ?: 0
        val duration = binding.spinnerDuration.selectedItem.toString()
        val shared = binding.switchShare.isChecked
        
        // Validate input
        if (title.isEmpty()) {
            binding.editTextTitle.error = "Title is required"
            binding.editTextTitle.requestFocus()
            return
        }
        
        // Show progress
        binding.progressBar.visibility = View.VISIBLE
        
        // Create goal object
        val goal = Goal(
            id = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            description = description,
            targetPoints = targetPoints,
            targetCalories = targetCalories,
            duration = duration,
            shared = shared
        )
        
        // Save to Firestore
        firestore.collection("goals")
            .document(goal.id)
            .set(goal)
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Goal added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, "Error adding goal: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
