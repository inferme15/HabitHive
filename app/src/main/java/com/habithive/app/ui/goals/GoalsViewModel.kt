package com.habithive.app.ui.goals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.model.Goal

class GoalsViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _goals = MutableLiveData<List<Goal>>()
    val goals: LiveData<List<Goal>> = _goals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        loadGoals()
    }

    fun loadGoals() {
        val currentUser = auth.currentUser ?: return

        _isLoading.value = true
        _errorMessage.value = null

        firestore.collection("goals")
            .whereEqualTo("userId", currentUser.uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val updatedGoals = mutableListOf<Goal>()
                val batch = firestore.batch()

                snapshot.documents.forEach { doc ->
                    val goal = doc.toObject(Goal::class.java)
                    if (goal != null) {
                        val goalWithId = goal.copy(id = doc.id)

                        // ✅ Check for auto-completion
                        if (!goalWithId.completed && !goalWithId.isExpired() &&
                            goalWithId.currentCalories >= goalWithId.targetCalories
                        ) {
                            val goalRef = firestore.collection("goals").document(doc.id)
                            batch.update(goalRef, mapOf(
                                "completed" to true,
                                "completedAt" to Timestamp.now()
                            ))
                            updatedGoals.add(goalWithId.copy(completed = true))
                        } else {
                            updatedGoals.add(goalWithId)
                        }
                    }
                }

                if (updatedGoals.isNotEmpty()) {
                    batch.commit()
                        .addOnSuccessListener { _goals.value = updatedGoals }
                        .addOnFailureListener { _goals.value = updatedGoals }
                } else {
                    _goals.value = updatedGoals
                }

                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }

    fun updateGoalCompletion(goal: Goal, completed: Boolean) {
        _isLoading.value = true

        val goalRef = firestore.collection("goals").document(goal.id)
        val updates = hashMapOf<String, Any?>(
            "completed" to completed,
            "completedAt" to if (completed) Timestamp.now() else null
        )

        goalRef.update(updates)
            .addOnSuccessListener {
                loadGoals()
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }

    fun deleteGoal(goal: Goal) {
        _isLoading.value = true
        firestore.collection("goals").document(goal.id)
            .delete()
            .addOnSuccessListener {
                loadGoals()
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }
}
