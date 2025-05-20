package com.habithive.app.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.data.model.Exercise
import com.habithive.app.databinding.ActivityExerciseBinding
import com.habithive.app.ui.auth.LoginActivity
import java.util.*

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    private lateinit var viewModel: ExerciseViewModel
    private var isSaving = false // ✅ Prevent double-save

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupSpinner()
        setupListeners()
        observeViewModel()

        binding.buttonViewHistory.setOnClickListener {
            startActivity(Intent(this, ExerciseHistoryActivity::class.java))
        }

        binding.buttonBurnRateInfo.setOnClickListener {
            startActivity(Intent(this, BurnRateInfoActivity::class.java))
        }
    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.exercise_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerExerciseType.adapter = adapter
        }

        binding.spinnerExerciseType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                binding.textCaloriesValue.text = "0"
                binding.textPointsValue.text = "0"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupListeners() {
        binding.buttonCalculate.setOnClickListener {
            calculateCaloriesAndPoints()
        }

        binding.buttonSave.setOnClickListener {
            if (binding.textCaloriesValue.text.toString() == "0") {
                Toast.makeText(this, "Please calculate calories first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isSaving) return@setOnClickListener
            isSaving = true
            binding.buttonSave.isEnabled = false
            saveExercise()
        }
    }

    private fun observeViewModel() {
        viewModel.saveResult.observe(this) { success ->
            isSaving = false
            binding.buttonSave.isEnabled = true
            if (success) {
                Toast.makeText(this, "Exercise saved successfully!", Toast.LENGTH_SHORT).show()
                binding.editTextDuration.text.clear()
                binding.editTextNotes.text.clear()
                binding.textCaloriesValue.text = "0"
                binding.textPointsValue.text = "0"
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            isSaving = false
            binding.buttonSave.isEnabled = true
            errorMsg?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
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
        val calories = viewModel.calculateCaloriesBurned(exerciseType, duration)
        val points = viewModel.calculatePointsEarned(calories)

        binding.textCaloriesValue.text = calories.toString()
        binding.textPointsValue.text = points.toString()
    }

    private fun saveExercise() {
        if (!viewModel.isUserAuthenticated()) {
            Toast.makeText(this, "You must be logged in to save exercises", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            return
        }

        val exerciseType = binding.spinnerExerciseType.selectedItem.toString()
        val duration = binding.editTextDuration.text.toString().toIntOrNull() ?: 0
        val calories = binding.textCaloriesValue.text.toString().toIntOrNull() ?: 0
        val points = binding.textPointsValue.text.toString().toIntOrNull() ?: 0
        val notes = binding.editTextNotes.text.toString()

        val exercise = Exercise(
            type = exerciseType,
            durationMinutes = duration,
            caloriesBurned = calories,
            pointsEarned = points,
            notes = notes,
            date = Date()
        )

        viewModel.saveExercise(exercise)

        val firestore = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val timestamp = Timestamp.now()

        // ✅ Save to Habits
        val habitData = hashMapOf(
            "userId" to userId,
            "title" to exerciseType,
            "description" to notes,
            "type" to "exercise",
            "frequency" to 1,
            "points" to points,
            "caloriesBurnedPerCompletion" to calories,
            "completed" to true,
            "createdAt" to timestamp,
            "date" to Date(),
            "completions" to listOf(timestamp)
        )
        firestore.collection("habits").add(habitData)

        // ✅ Update Achievements using FieldValue.increment()
        val userDoc = firestore.collection("achievements").document(userId)
        firestore.runTransaction { transaction ->
            transaction.update(userDoc, mapOf(
                "totalPoints" to FieldValue.increment(points.toLong()),
                "dailyScore" to FieldValue.increment(points.toLong()),
                "weeklyScore" to FieldValue.increment(points.toLong()),
                "caloriesBurned" to FieldValue.increment(calories.toLong())
            ))
        }

        // ✅ Update Goals
        firestore.collection("goals")
            .whereEqualTo("userId", userId)
            .whereEqualTo("completed", false)
            .get()
            .addOnSuccessListener { goalsSnapshot ->
                for (doc in goalsSnapshot) {
                    val goal = doc.data
                    val targetCalories = (goal["targetCalories"] as? Long)?.toInt() ?: continue
                    val targetPoints = (goal["targetPoints"] as? Long)?.toInt() ?: 0
                    val currentCalories = (goal["currentCalories"] as? Long)?.toInt() ?: 0
                    val currentPoints = (goal["currentPoints"] as? Long)?.toInt() ?: 0

                    val newCalories = currentCalories + calories
                    val newPoints = currentPoints + points

                    val updates = mutableMapOf<String, Any>(
                        "currentCalories" to newCalories,
                        "currentPoints" to newPoints
                    )

                    if (newCalories >= targetCalories && newPoints >= targetPoints) {
                        updates["completed"] = true
                        updates["completedAt"] = Timestamp.now()
                    }

                    doc.reference.update(updates)
                }
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
