package com.habithive.app.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.habithive.app.data.model.Exercise
import java.util.Date


/**
 * Constants for user document field names
 */
object UserFields {
    const val POINTS = "points"
    const val UPDATED_AT = "updatedAt"
    const val NAME = "name"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val GENDER = "gender"
    const val HEIGHT = "height"
    const val WEIGHT = "weight"
    const val HEALTH = "health"
    const val CREATED_AT = "createdAt"
}

/**
 * Utility class for Firebase operations related to exercises and points
 */
object FirebaseUtils {

    private const val TAG = "FirebaseUtils"

    /**
     * Save exercise to Firestore and update user points
     * @param exercise The exercise to save
     * @param onSuccess Callback for successful operation
     * @param onFailure Callback for failed operation
     */
    fun saveExerciseAndUpdatePoints(
        exercise: Exercise,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            onFailure("User not authenticated")
            return
        }

        // Save exercise first
        FirebaseFirestore.getInstance().collection(FirebaseCollections.EXERCISES)
            .add(exercise)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Exercise saved with ID: ${documentReference.id}")

                // Now update user points
                updateUserPoints(userId, exercise.pointsEarned, onSuccess, onFailure)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error saving exercise", e)
                onFailure("Failed to save exercise: ${e.message}")
            }
    }

    /**
     * Update user points in Firestore
     */
    private fun updateUserPoints(
        userId: String,
        pointsToAdd: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(FirebaseCollections.USERS).document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Get current points
                    val currentPoints = document.getLong(UserFields.POINTS)?.toInt() ?: 0

                    // Calculate new total
                    val updatedPoints = currentPoints + pointsToAdd

                    // Update in Firestore
                    val updates = mapOf(
                        UserFields.POINTS to updatedPoints,
                        UserFields.UPDATED_AT to Date()
                    )

                    FirebaseFirestore.getInstance().collection(FirebaseCollections.USERS).document(userId)
                        .set(updates, SetOptions.merge())
                        .addOnSuccessListener {
                            Log.d(TAG, "User points updated successfully to $updatedPoints")
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error updating user points", e)
                            onFailure("Failed to update points: ${e.message}")
                        }
                } else {
                    onFailure("User document not found")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting user document", e)
                onFailure("Failed to get user data: ${e.message}")
            }
    }
}