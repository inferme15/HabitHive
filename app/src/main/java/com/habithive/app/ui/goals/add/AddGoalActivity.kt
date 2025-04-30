package com.habithive.app.ui.goals.add

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.habithive.app.R
import com.habithive.app.databinding.ActivityAddGoalBinding
import com.habithive.app.ui.goals.AddGoalViewModel

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
        binding.spinnerDuration.setOnItemSelectedListener { position ->
            viewModel.durationPosition.value = position
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
                    "Goal saved successfully",
                    Toast.LENGTH_SHORT
                ).show()
                finish() // Close activity on success
            }
            AddGoalViewModel.SaveStatus.ERROR_EMPTY_TITLE -> {
                Toast.makeText(
                    this,
                    "Please enter a title for your goal",
                    Toast.LENGTH_LONG
                ).show()
            }
            AddGoalViewModel.SaveStatus.ERROR_NOT_AUTHENTICATED -> {
                Toast.makeText(
                    this,
                    "You must be logged in to save goals",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
            AddGoalViewModel.SaveStatus.ERROR_SAVE_FAILED -> {
                Toast.makeText(
                    this,
                    "Failed to save goal. Please try again.",
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
    
    // Extension function to handle spinner selection
    private fun android.widget.Spinner.setOnItemSelectedListener(onItemSelected: (position: Int) -> Unit) {
        this.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                onItemSelected(position)
            }
            
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}