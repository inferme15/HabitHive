package com.habithive.app.ui.exercise

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.data.model.Exercise
import com.habithive.app.utils.FirebaseCollections

/**
 * ViewModel for Exercise screen
 * Handles exercise calculations and Firebase operations
 * Now includes BMI-based calorie calculations
 */
class ExerciseViewModel : ViewModel() {

    private val TAG = "ExerciseViewModel"

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // LiveData
    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    private val _userBmi = MutableLiveData<Float?>(null)
    val userBmi: LiveData<Float?> = _userBmi

    // Exercise burn rates (calories per minute)
    private val exerciseBurnRates = mapOf(
        "Walking" to 4,
        "Running" to 10,
        "Cycling" to 7,
        "Swimming" to 8,
        "Hiking" to 6,
        "Yoga" to 3,
        "Weight Training" to 5,
        "HIIT" to 12,
        "Pilates" to 4,
        "Dancing" to 7,
        "Rock Climbing" to 9,
        "Boxing" to 11,
        "Tennis" to 8,
        "Basketball" to 9,
        "Soccer" to 10,
        "Rowing" to 8,
        "Martial Arts" to 9,
        "CrossFit" to 12,
        "Skiing" to 7,
        "Snowboarding" to 7,
        "Other" to 5
    )

    // BMI adjustment factors
    private val bmiAdjustmentFactors = mapOf(
        "underweight" to 0.8f,  // BMI < 18.5
        "normal" to 1.0f,       // BMI 18.5-24.9
        "overweight" to 1.2f,   // BMI 25-29.9
        "obese" to 1.4f         // BMI ≥ 30
    )

    init {
        // Load user BMI on initialization
        loadUserBMI()
    }

    /**
     * Load the user's BMI from Firestore
     */
    fun loadUserBMI() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _error.value = "User not authenticated"
            return
        }

        firestore.collection(FirebaseCollections.USERS)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Try to get BMI directly if available
                    val bmi = document.getDouble("bmi")?.toFloat()

                    if (bmi != null) {
                        _userBmi.value = bmi
                        Log.d(TAG, "User BMI loaded: $bmi")
                    } else {
                        // Calculate BMI if height and weight are available
                        val height = document.getDouble("height")?.toFloat()
                        val weight = document.getDouble("weight")?.toFloat()

                        if (height != null && weight != null && height > 0) {
                            // BMI = weight (kg) / height^2 (m)
                            val heightInMeters = height / 100  // Convert from cm to m
                            val calculatedBmi = weight / (heightInMeters * heightInMeters)
                            _userBmi.value = calculatedBmi
                            Log.d(TAG, "User BMI calculated: $calculatedBmi")
                        } else {
                            Log.d(TAG, "Insufficient data to calculate BMI")
                            _userBmi.value = null
                        }
                    }
                } else {
                    Log.d(TAG, "User document doesn't exist")
                    _userBmi.value = null
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting user BMI", e)
                _error.value = "Failed to get user data: ${e.message}"
                _userBmi.value = null
            }
    }

    /**
     * Check if user is authenticated
     * @return True if user is authenticated, false otherwise
     */
    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Get the burn rate for a specific exercise type
     * @param exerciseType The type of exercise
     * @return Burn rate in calories per minute
     */
    fun getBurnRateForExerciseType(exerciseType: String): Int {
        return exerciseBurnRates[exerciseType] ?: 5 // Default to 5 if not found
    }

    /**
     * Get the BMI adjustment factor based on the BMI value
     * @param bmi The user's BMI
     * @return The adjustment factor
     */
    private fun getBmiAdjustmentFactor(bmi: Float?): Float {
        if (bmi == null) return 1.0f  // Default to normal if BMI not available

        return when {
            bmi < 18.5 -> bmiAdjustmentFactors["underweight"] ?: 0.8f
            bmi < 25 -> bmiAdjustmentFactors["normal"] ?: 1.0f
            bmi < 30 -> bmiAdjustmentFactors["overweight"] ?: 1.2f
            else -> bmiAdjustmentFactors["obese"] ?: 1.4f
        }
    }

    /**
     * Calculate calories burned based on exercise type, duration, and user BMI
     * @param exerciseType The type of exercise
     * @param durationMinutes Duration in minutes
     * @return Total calories burned
     */
    fun calculateCaloriesBurned(exerciseType: String, durationMinutes: Int): Int {
        val burnRate = getBurnRateForExerciseType(exerciseType)
        val bmiAdjustment = getBmiAdjustmentFactor(_userBmi.value)

        // Apply BMI adjustment factor to the calculation
        val caloriesBurned = (burnRate * durationMinutes * bmiAdjustment).toInt()

        Log.d(TAG, "Calories calculation: $burnRate cal/min × $durationMinutes min × $bmiAdjustment (BMI adj) = $caloriesBurned calories")

        return caloriesBurned
    }

    /**
     * Calculate points earned based on calories burned
     * 1 point per 10 calories
     * @param caloriesBurned Total calories burned
     * @return Points earned
     */
    fun calculatePointsEarned(caloriesBurned: Int): Int {
        return caloriesBurned / 10
    }

    /**
     * Save exercise to Firestore
     * @param exercise The exercise to save
     */
    fun saveExercise(exercise: Exercise) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _error.value = "User not authenticated"
            _saveResult.value = false
            return
        }

        // Create a copy with the user ID
        val exerciseWithUser = exercise.copy(userId = userId)

        // Save to Firestore
        firestore.collection(FirebaseCollections.EXERCISES)
            .add(exerciseWithUser)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Exercise saved with ID: ${documentReference.id}")

                // Update user points in Firestore
                updateUserPoints(exerciseWithUser.pointsEarned)

                _saveResult.value = true
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error saving exercise", e)
                _error.value = "Failed to save exercise: ${e.message}"
                _saveResult.value = false
            }
    }

    /**
     * Update user points in Firestore
     * @param pointsEarned Points earned from the exercise
     */
    /**
     * Update achievements stats in Firestore
     */
    private fun updateUserPoints(pointsEarned: Int) {
        val userId = auth.currentUser?.uid ?: return

        val achievementRef = firestore.collection("achievements").document(userId)

        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(achievementRef)

            val currentTotalPoints = snapshot.getLong("totalPoints") ?: 0
            val currentDailyScore = snapshot.getLong("dailyScore") ?: 0
            val currentWeeklyScore = snapshot.getLong("weeklyScore") ?: 0
            val currentCalories = snapshot.getLong("caloriesBurned") ?: 0

            transaction.update(achievementRef, mapOf(
                "totalPoints" to currentTotalPoints + pointsEarned,
                "dailyScore" to currentDailyScore + pointsEarned,
                "weeklyScore" to currentWeeklyScore + pointsEarned,
                "caloriesBurned" to currentCalories + (pointsEarned * 10)
            ))
        }.addOnSuccessListener {
            Log.d(TAG, "Achievements updated successfully")
        }.addOnFailureListener { e ->
            Log.e(TAG, "Failed to update achievements: ${e.message}")
        }
    }

}