package com.habithive.app.ui.goals.add

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.habithive.app.R
import com.habithive.app.databinding.ActivityAddGoalBinding
import com.habithive.app.utils.QuotesUtil

class AddGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGoalBinding
    private lateinit var viewModel: AddGoalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_goal)
        viewModel = ViewModelProvider(this).get(AddGoalViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Setup actionbar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.add_goal)
        }

        // Setup duration spinner
        setupDurationSpinner()

        // Setup motivational quotes
        setupMotivationalQuote()

        // Setup Save button
        binding.buttonSave.setOnClickListener {
            saveGoal()
        }

        // Observe save status
        viewModel.saveStatus.observe(this) { status ->
            handleSaveStatus(status)
        }

        // Observe loading state
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.buttonSave.isEnabled = !isLoading
        }
    }

    private fun setupDurationSpinner() {
        val durationOptions = arrayOf("1 week", "2 weeks", "1 month", "3 months")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, durationOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDuration.adapter = adapter

        // Set selection change listener
        binding.spinnerDuration.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.durationPosition.value = position
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun saveGoal() {
        // Transfer UI input to ViewModel
        viewModel.title.value = binding.editTextTitle.text.toString()
        viewModel.description.value = binding.editTextDescription.text.toString()
        viewModel.targetPoints.value = binding.editTextTargetPoints.text.toString()
        viewModel.targetCalories.value = binding.editTextTargetCalories.text.toString()
        viewModel.durationPosition.value = binding.spinnerDuration.selectedItemPosition
        viewModel.isShared.value = binding.switchShare.isChecked

        // Call save
        viewModel.saveGoal()
    }

    private fun handleSaveStatus(status: AddGoalViewModel.SaveStatus) {
        when (status) {
            AddGoalViewModel.SaveStatus.SUCCESS -> {
                Toast.makeText(
                    this,
                    R.string.goal_saved_successfully,
                    Toast.LENGTH_SHORT
                ).show()
                finish() // Close activity on success
            }
            AddGoalViewModel.SaveStatus.ERROR_EMPTY_TITLE -> {
                Toast.makeText(
                    this,
                    R.string.error_empty_goal_title,
                    Toast.LENGTH_LONG
                ).show()
            }
            AddGoalViewModel.SaveStatus.ERROR_NOT_AUTHENTICATED -> {
                Toast.makeText(
                    this,
                    R.string.error_not_authenticated,
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
            AddGoalViewModel.SaveStatus.ERROR_SAVE_FAILED -> {
                Toast.makeText(
                    this,
                    R.string.error_save_failed,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Sets up the motivational quote component on the goal creation screen
     * Displays a random goal-setting quote and allows user to refresh for a new quote
     */
    private fun setupMotivationalQuote() {
        // Find views within the included layout
        val quoteView = findViewById<View>(R.id.quoteCard)
        if (quoteView != null) {
            val tvQuoteText = quoteView.findViewById<TextView>(R.id.tvQuoteText)
            val tvQuoteAuthor = quoteView.findViewById<TextView>(R.id.tvQuoteAuthor)
            val btnNewQuote = quoteView.findViewById<Button>(R.id.btnNewQuote)

            if (tvQuoteText != null && tvQuoteAuthor != null && btnNewQuote != null) {
                // Set initial quote
                updateQuoteDisplay(tvQuoteText, tvQuoteAuthor)

                // Set up new quote button
                btnNewQuote.setOnClickListener {
                    updateQuoteDisplay(tvQuoteText, tvQuoteAuthor)
                }
            }
        }
    }

    /**
     * Updates the quote display with a new random goal-setting quote
     * Uses the goal title and description to generate a contextually appropriate quote
     */
    private fun updateQuoteDisplay(tvQuoteText: TextView, tvQuoteAuthor: TextView) {
        // Get current goal title and description for context-aware quote generation
        val goalTitle = binding.editTextTitle.text.toString()
        val goalDescription = binding.editTextDescription.text.toString()

        // Generate a contextually appropriate quote based on goal content
        val fullQuote = if (goalTitle.isNotEmpty() || goalDescription.isNotEmpty()) {
            // Use contextual quote if goal has content
            QuotesUtil.getQuoteForGoal(goalTitle, goalDescription)
        } else {
            // Use motivation quote if no goal content yet
            QuotesUtil.getRandomQuote(QuotesUtil.QuoteCategory.MOTIVATION)
        }

        // Store in ViewModel for persistence
        viewModel.updateQuote(fullQuote)

        // Split quote into text and author
        val parts = fullQuote.split(" - ", limit = 2)

        if (parts.size == 2) {
            // Update UI with separated components
            val quoteText = parts[0].trim().let {
                if (!it.startsWith("\"") && !it.endsWith("\"")) "\"$it\"" else it
            }
            tvQuoteText.text = quoteText
            tvQuoteAuthor.text = "- ${parts[1].trim()}"
        } else {
            // Just show the whole quote in the text field
            tvQuoteText.text = "\"${fullQuote}\""
            tvQuoteAuthor.text = ""
        }

        // Add animation for better UX
        tvQuoteText.alpha = 0f
        tvQuoteAuthor.alpha = 0f

        tvQuoteText.animate().alpha(1f).setDuration(500).start()
        tvQuoteAuthor.animate().alpha(1f).setDuration(500).setStartDelay(100).start()
    }
}