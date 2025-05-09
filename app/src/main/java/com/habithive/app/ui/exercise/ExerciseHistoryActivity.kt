package com.habithive.app.ui.exercise

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.habithive.app.R
import com.habithive.app.databinding.ActivityExerciseHistoryBinding
import com.habithive.app.ui.auth.LoginActivity

/**
 * Activity showing the user's exercise history
 * Displays exercises in a list ordered by date (newest first)
 */
class ExerciseHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseHistoryBinding
    private lateinit var viewModel: ExerciseHistoryViewModel
    private lateinit var adapter: ExerciseHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityExerciseHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.exercise_history)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this)[ExerciseHistoryViewModel::class.java]

        // Check authentication first
        if (!viewModel.isUserAuthenticated()) {
            Toast.makeText(this, "You must be logged in to view exercise history", Toast.LENGTH_SHORT).show()
            // Navigate to login screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
            return
        }

        // Setup RecyclerView
        setupRecyclerView()

        // Setup observers
        setupObservers()

        // Load exercises
        viewModel.loadExercises()
    }

    private fun setupRecyclerView() {
        adapter = ExerciseHistoryAdapter()
        binding.recyclerViewExercises.apply {
            layoutManager = LinearLayoutManager(this@ExerciseHistoryActivity)
            adapter = this@ExerciseHistoryActivity.adapter
        }
    }

    private fun setupObservers() {
        // Observe exercises
        viewModel.exercises.observe(this) { exercises ->
            adapter.submitList(exercises)

            // Show empty state if there are no exercises
            if (exercises.isEmpty()) {
                binding.emptyStateLayout.visibility = View.VISIBLE
                binding.recyclerViewExercises.visibility = View.GONE
                binding.errorLayout.visibility = View.GONE
            } else {
                binding.emptyStateLayout.visibility = View.GONE
                binding.recyclerViewExercises.visibility = View.VISIBLE
                binding.errorLayout.visibility = View.GONE
            }
        }

        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

            // Hide other layouts during loading
            if (isLoading) {
                binding.emptyStateLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.GONE
            }
        }

        // Observe error state
        viewModel.error.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                // Show error toast
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

                // Show error layout
                binding.errorLayout.visibility = View.VISIBLE
                binding.recyclerViewExercises.visibility = View.GONE
                binding.emptyStateLayout.visibility = View.GONE

                // Set error message
                binding.errorMessage.text = errorMessage
            } else {
                binding.errorLayout.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}