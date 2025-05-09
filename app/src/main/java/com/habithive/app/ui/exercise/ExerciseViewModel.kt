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
     * Calculate calories burned based on exercise type and duration
     * @param exerciseType The type of exercise
     * @param durationMinutes Duration in minutes
     * @return Total calories burned
     */
    fun calculateCaloriesBurned(exerciseType: String, durationMinutes: Int): Int {
        val burnRate = getBurnRateForExerciseType(exerciseType)
        return burnRate * durationMinutes
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
    private fun updateUserPoints(pointsEarned: Int) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            return
        }

        // Get the current user document
        firestore.collection(FirebaseCollections.USERS)
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document == null || !document.exists()) {
                    // Create user document if it doesn't exist
                    val userData = hashMapOf(
                        "points" to pointsEarned,
                        "userId" to userId
                    )

                    firestore.collection(FirebaseCollections.USERS)
                        .document(userId)
                        .set(userData)
                        .addOnSuccessListener {
                            Log.d(TAG, "User document created with initial points: $pointsEarned")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error creating user document", e)
                        }
                    return@addOnSuccessListener
                }

                // Get current points or default to 0
                val currentPoints = document.getLong("points")?.toInt() ?: 0

                // Calculate new points
                val newPoints = currentPoints + pointsEarned

                // Update points in Firestore
                firestore.collection(FirebaseCollections.USERS)
                    .document(userId)
                    .update("points", newPoints)
                    .addOnSuccessListener {
                        Log.d(TAG, "User points updated: $newPoints")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "Error updating user points", e)
                    }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting user document", e)
            }
    }
}