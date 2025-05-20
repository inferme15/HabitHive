package com.habithive.app.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.habithive.app.R
import com.habithive.app.data.model.Exercise
import com.habithive.app.databinding.ActivityExerciseHistoryBinding
import com.habithive.app.ui.auth.LoginActivity

class ExerciseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseHistoryBinding
    private lateinit var viewModel: ExerciseHistoryViewModel
    private lateinit var adapter: ExerciseHistoryAdapter
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var exerciseListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.exercise_history)

        viewModel = ViewModelProvider(this)[ExerciseHistoryViewModel::class.java]

        if (!viewModel.isUserAuthenticated()) {
            Toast.makeText(this, "You must be logged in to view exercise history", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupRecyclerView()
        setupObservers()
        listenForExerciseChanges()
    }

    private fun setupRecyclerView() {
        adapter = ExerciseHistoryAdapter { exercise ->
            confirmDelete(exercise)
        }
        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(this@ExerciseHistoryActivity)
            adapter = this@ExerciseHistoryActivity.adapter
        }
    }

    private fun setupObservers() {
        viewModel.exercises.observe(this) { exercises ->
            adapter.submitList(exercises)
            binding.emptyStateLayout.visibility = if (exercises.isEmpty()) View.VISIBLE else View.GONE
            binding.recyclerViewExercises.visibility = if (exercises.isNotEmpty()) View.VISIBLE else View.GONE
            binding.errorLayout.visibility = View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                binding.emptyStateLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.GONE
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                binding.errorLayout.visibility = View.VISIBLE
                binding.recyclerViewExercises.visibility = View.GONE
                binding.emptyStateLayout.visibility = View.GONE
                binding.errorMessage.text = errorMessage
            } else {
                binding.errorLayout.visibility = View.GONE
            }
        }
    }

    private fun confirmDelete(exercise: Exercise) {
        AlertDialog.Builder(this)
            .setTitle("Delete Exercise")
            .setMessage("Are you sure you want to delete this exercise?")
            .setPositiveButton("Yes") { _, _ -> deleteExercise(exercise) }
            .setNegativeButton("No", null)
            .show()
    }

    private fun deleteExercise(exercise: Exercise) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val firestore = FirebaseFirestore.getInstance()

        // Delete from exercises
        firestore.collection("exercises")
            .document(exercise.id)
            .delete()
            .addOnSuccessListener {
                // Delete corresponding habit (if needed)
                firestore.collection("habits")
                    .whereEqualTo("userId", userId)
                    .whereEqualTo("title", exercise.type)
                    .whereEqualTo("date", exercise.date)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        snapshot.documents.forEach { it.reference.delete() }
                    }

                // 🔽 Subtract from achievements
                val userDoc = firestore.collection("achievements").document(userId)
                firestore.runTransaction { transaction ->
                    val snapshot = transaction.get(userDoc)
                    val total = (snapshot.getLong("totalPoints") ?: 0L) - exercise.pointsEarned
                    val daily = (snapshot.getLong("dailyScore") ?: 0L) - exercise.pointsEarned
                    val weekly = (snapshot.getLong("weeklyScore") ?: 0L) - exercise.pointsEarned
                    val calories = (snapshot.getLong("caloriesBurned") ?: 0L) - exercise.caloriesBurned

                    transaction.update(userDoc, mapOf(
                        "totalPoints" to total.coerceAtLeast(0),
                        "dailyScore" to daily.coerceAtLeast(0),
                        "weeklyScore" to weekly.coerceAtLeast(0),
                        "caloriesBurned" to calories.coerceAtLeast(0)
                    ))
                }
            }
    }


    private fun updateAchievementsAfterDelete(userId: String, caloriesToSubtract: Int, pointsToSubtract: Int) {
        val ref = firestore.collection("achievements").document(userId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(ref)
            val totalPoints = (snapshot.getLong("totalPoints") ?: 0L) - pointsToSubtract
            val calories = (snapshot.getLong("caloriesBurned") ?: 0L) - caloriesToSubtract
            val dailyScore = (snapshot.getLong("dailyScore") ?: 0L) - pointsToSubtract
            val weeklyScore = (snapshot.getLong("weeklyScore") ?: 0L) - pointsToSubtract

            transaction.update(ref, mapOf(
                "totalPoints" to totalPoints.coerceAtLeast(0),
                "caloriesBurned" to calories.coerceAtLeast(0),
                "dailyScore" to dailyScore.coerceAtLeast(0),
                "weeklyScore" to weeklyScore.coerceAtLeast(0)
            ))
        }
    }

    private fun listenForExerciseChanges() {
        val userId = auth.currentUser?.uid ?: return
        exerciseListener = firestore.collection("exercises")
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener
                viewModel.loadExercises() // Refresh adapter when exercises change
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        exerciseListener?.remove()
    }
}
