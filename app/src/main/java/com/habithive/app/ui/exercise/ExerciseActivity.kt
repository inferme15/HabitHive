package com.habithive.app.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.habithive.app.R
import com.habithive.app.databinding.ActivityExerciseBinding
import com.habithive.app.data.model.Exercise
import com.habithive.app.ui.auth.LoginActivity
import java.util.Date

/**
 * Activity for logging exercise
 * Tracks exercise type, duration, and notes
 * Calculates calories burned and points earned
 */
class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var viewModel: ExerciseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        // Setup action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Setup exercise types spinner
        setupSpinner()

        // Setup listeners
        setupListeners()

        // Observe ViewModel
        observeViewModel()

        // Setup View History button
        binding.buttonViewHistory.setOnClickListener {
            val intent = Intent(this, ExerciseHistoryActivity::class.java)
            startActivity(intent)
        }

        // Add burn rate info button click
        binding.cardCalculation.setOnClickListener {
            val intent = Intent(this, BurnRateInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSpinner() {
        val exerciseTypes = resources.getStringArray(R.array.exercise_types)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            exerciseTypes
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerExerciseType.adapter = adapter

        // Update burn rate when exercise type changes, but DON'T calculate calories automatically
        binding.spinnerExerciseType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val exerciseType = exerciseTypes[position]
                val burnRate = viewModel.getBurnRateForExerciseType(exerciseType)
                binding.textBurnRateValue.text = getString(R.string.burn_rate_value, burnRate)

                // Removed automatic calculation here
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun setupListeners() {
        // Calculate button click - This is now the ONLY place where calculation happens
        binding.buttonCalculate.setOnClickListener {
            calculateCaloriesAndPoints()
        }

        // Save button click
        binding.buttonSave.setOnClickListener {
            if (binding.textCaloriesValue.text.toString() == "0") {
                Toast.makeText(this, "Please calculate calories first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            saveExercise()
        }

        // Removed duration focus change listener to prevent automatic calculation
    }

    private fun calculateCaloriesAndPoints() {
        val durationText = binding.editTextDuration.text.toString()
        if (durationText.isEmpty()) {
            Toast.makeText(this, "Please enter duration", Toast.LENGTH_SHORT).show()
            return
        }

        val duration = durationText.toIntOrNull() ?: 0
        if (duration <= 0) {
            Toast.makeText(this, "Duration must be greater than 0", Toast.LENGTH_SHORT).show()
            return
        }

        val exerciseType = binding.spinnerExerciseType.selectedItem.toString()

        // Calculate calories and points
        val calories = viewModel.calculateCaloriesBurned(exerciseType, duration)
        val points = viewModel.calculatePointsEarned(calories)

        // Update UI
        binding.textCaloriesValue.text = calories.toString()
        binding.textPointsValue.text = points.toString()
    }

    private fun saveExercise() {
        // Check if user is authenticated (will delegate to ViewModel)
        if (!viewModel.isUserAuthenticated()) {
            Toast.makeText(this, "You must be logged in to save exercises", Toast.LENGTH_SHORT).show()
            // Navigate to login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return
        }

        val durationText = binding.editTextDuration.text.toString()
        if (durationText.isEmpty()) {
            Toast.makeText(this, "Please enter duration", Toast.LENGTH_SHORT).show()
            return
        }

        val duration = durationText.toIntOrNull() ?: 0
        if (duration <= 0) {
            Toast.makeText(this, "Duration must be greater than 0", Toast.LENGTH_SHORT).show()
            return
        }

        val exerciseType = binding.spinnerExerciseType.selectedItem.toString()
        val notes = binding.editTextNotes.text.toString()

        // Get the calculated calories and points from the UI
        val calories = binding.textCaloriesValue.text.toString().toIntOrNull() ?: 0
        val points = binding.textPointsValue.text.toString().toIntOrNull() ?: 0

        // Ensure calories have been calculated
        if (calories <= 0) {
            Toast.makeText(this, "Please calculate calories first", Toast.LENGTH_SHORT).show()
            return
        }

        // Create exercise object
        val exercise = Exercise(
            type = exerciseType,
            durationMinutes = duration,
            caloriesBurned = calories,
            pointsEarned = points,
            notes = notes,
            date = Date()
        )

        // Show loading indicator
        binding.progressBar.visibility = View.VISIBLE

        // Save exercise
        viewModel.saveExercise(exercise)
    }

    private fun observeViewModel() {
        // Observe save result
        viewModel.saveResult.observe(this) { success ->
            binding.progressBar.visibility = View.GONE

            if (success) {
                Toast.makeText(
                    this,
                    "Exercise saved successfully!",
                    Toast.LENGTH_SHORT
                ).show()

                // Reset form
                binding.editTextDuration.text?.clear()
                binding.editTextNotes.text?.clear()
                binding.spinnerExerciseType.setSelection(0)
                binding.textCaloriesValue.text = "0"
                binding.textPointsValue.text = "0"
            } else {
                Toast.makeText(
                    this,
                    "Failed to save exercise. Please try again.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Observe error
        viewModel.error.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}