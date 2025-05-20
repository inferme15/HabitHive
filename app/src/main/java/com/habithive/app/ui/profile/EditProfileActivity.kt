package com.habithive.app.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.R
import com.habithive.app.databinding.ActivityEditProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var selectedDate: Calendar? = null

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

        // Setup date picker
        setupDatePicker()

        // Setup BMI calculation
        setupBmiCalculation()

        // Load current user data
        loadUserData()

        // Setup save button
        binding.buttonSave.setOnClickListener {
            saveUserData()
        }
    }

    private fun setupDatePicker() {
        binding.editTextDob.setOnClickListener {
            val calendar = Calendar.getInstance()

            // Use existing date if available, otherwise use current date
            val initialCalendar = selectedDate ?: calendar

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, month, day ->
                    // Save selected date
                    selectedDate = Calendar.getInstance().apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }

                    // Update date display
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    binding.editTextDob.setText(dateFormat.format(selectedDate!!.time))

                    // Calculate and display age
                    calculateAndDisplayAge()
                },
                initialCalendar.get(Calendar.YEAR),
                initialCalendar.get(Calendar.MONTH),
                initialCalendar.get(Calendar.DAY_OF_MONTH)
            )

            // Set max date to today
            datePickerDialog.datePicker.maxDate = calendar.timeInMillis

            datePickerDialog.show()
        }
    }

    private fun calculateAndDisplayAge() {
        selectedDate?.let { dob ->
            val today = Calendar.getInstance()

            var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

            // Adjust age if birthday hasn't occurred yet this year
            if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
                age--
            }

            binding.textAge.text = "Age: $age"
        }
    }

    private fun setupBmiCalculation() {
        binding.buttonCalculateBmi.setOnClickListener {
            calculateAndDisplayBmi()
        }

        // Auto-calculate BMI when height or weight changes
        binding.editTextHeight.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) calculateAndDisplayBmi()
        }

        binding.editTextWeight.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) calculateAndDisplayBmi()
        }
    }

    private fun calculateAndDisplayBmi() {
        try {
            val heightText = binding.editTextHeight.text.toString()
            val weightText = binding.editTextWeight.text.toString()
            val gender = binding.spinnerGender.selectedItem.toString()

            if (heightText.isNotEmpty() && weightText.isNotEmpty()) {
                val height = heightText.toDouble() / 100 // Convert cm to m
                val weight = weightText.toDouble()

                if (height > 0 && weight > 0) {
                    val bmiValue = weight / (height * height)
                    val bmiFormatted = String.format("%.1f", bmiValue)

                    val category = when {
                        bmiValue < 18.5 -> "Underweight"
                        bmiValue < 25.0 -> "Normal"
                        bmiValue < 30.0 -> "Overweight"
                        else -> "Obese"
                    }

                    binding.textBmiValue.text = "BMI: $bmiFormatted"
                    binding.textBmiCategory.text = "($category)"
                } else {
                    binding.textBmiValue.text = "BMI: N/A"
                    binding.textBmiCategory.text = ""
                }
            } else {
                binding.textBmiValue.text = "BMI: N/A"
                binding.textBmiCategory.text = ""
            }
        } catch (e: Exception) {
            binding.textBmiValue.text = "BMI: N/A"
            binding.textBmiCategory.text = ""
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

                        // Handle date of birth
                        val dob = document.getString("dateOfBirth")
                        if (!dob.isNullOrEmpty()) {
                            binding.editTextDob.setText(dob)

                            // Parse the date to setup the selectedDate
                            try {
                                val parts = dob.split("/")
                                if (parts.size == 3) {
                                    val day = parts[0].toInt()
                                    val month = parts[1].toInt() - 1 // 0-based month
                                    val year = parts[2].toInt()

                                    selectedDate = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month)
                                        set(Calendar.DAY_OF_MONTH, day)
                                    }

                                    // Update age display
                                    calculateAndDisplayAge()
                                }
                            } catch (e: Exception) {
                                // Ignore parsing error
                            }
                        }

                        // Calculate BMI
                        calculateAndDisplayBmi()
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
            val dobText = binding.editTextDob.text.toString().trim()

            binding.progressBar.visibility = View.VISIBLE

            // Create user data map with appropriate types
            val userData = HashMap<String, Any>().apply {
                put("name", name)
                put("gender", gender)
                put("health", health)

                // Add date of birth
                if (dobText.isNotEmpty()) {
                    put("dateOfBirth", dobText)
                }

                // Calculate BMI for storage
                if (heightText.isNotEmpty() && weightText.isNotEmpty()) {
                    try {
                        val height = heightText.toDouble() / 100 // Convert cm to m
                        val weight = weightText.toDouble()
                        if (height > 0 && weight > 0) {
                            val bmi = weight / (height * height)
                            put("bmi", bmi)
                        }
                    } catch (e: Exception) {
                        // Skip BMI calculation on error
                    }
                }

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