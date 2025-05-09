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
 * ViewModel for the Exercise History screen
 * Handles loading exercise history from Firestore
 */
class ExerciseHistoryViewModel : ViewModel() {

    private val TAG = "ExerciseHistoryViewModel"

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    // LiveData for exercises
    private val _exercises = MutableLiveData<List<Exercise>>()
    val exercises: LiveData<List<Exercise>> = _exercises

    // LiveData for loading state
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData for error state
    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    /**
     * Check if user is authenticated
     * @return True if user is authenticated, false otherwise
     */
    fun isUserAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Load exercises for the current user
     * This query doesn't require a composite index
     */
    fun loadExercises() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _error.value = "User not authenticated"
            return
        }

        _isLoading.value = true
        _error.value = null

        try {
            // Only filter by userId and don't use orderBy to avoid index requirements
            firestore.collection(FirebaseCollections.EXERCISES)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    try {
                        val exerciseList = documents.mapNotNull { doc ->
                            try {
                                doc.toObject(Exercise::class.java)
                            } catch (e: Exception) {
                                Log.e(TAG, "Error converting document to Exercise", e)
                                null
                            }
                        }

                        // Sort the list in memory instead of in the query
                        val sortedList = exerciseList.sortedByDescending {
                            it.date?.time ?: 0L
                        }

                        _exercises.value = sortedList
                        _isLoading.value = false

                        // Log success for debugging
                        Log.d(TAG, "Successfully loaded ${sortedList.size} exercises")
                    } catch (e: Exception) {
                        Log.e(TAG, "Error processing exercise documents", e)
                        _error.value = "Error processing data: ${e.message}"
                        _isLoading.value = false
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error loading exercises", e)
                    _error.value = "Failed to load exercises: ${e.message}"
                    _isLoading.value = false
                }
        } catch (e: Exception) {
            Log.e(TAG, "Exception when loading exercises", e)
            _error.value = "An unexpected error occurred: ${e.message}"
            _isLoading.value = false
        }
    }

    /**
     * Retry loading exercises
     * Clears error state and attempts to load again
     */
    fun retryLoading() {
        _error.value = null
        loadExercises()
    }
}