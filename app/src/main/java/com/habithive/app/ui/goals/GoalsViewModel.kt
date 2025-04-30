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
        
        // Query Firestore for user's goals
        firestore.collection("goals")
            .whereEqualTo("userId", currentUser.uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { snapshot ->
                val goalsList = snapshot.documents.mapNotNull { document ->
                    val goal = document.toObject(Goal::class.java)
                    goal?.copy(id = document.id)
                }
                _goals.value = goalsList
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
        
        // Update the completed field and completedAt timestamp if needed
        val updates = hashMapOf<String, Any?>(
            "completed" to completed,
            "completedAt" to if (completed) Timestamp.now() else null
        )
        
        goalRef.update(updates)
            .addOnSuccessListener {
                loadGoals() // Reload goals after update
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }
    
    fun deleteGoal(goalId: String) {
        _isLoading.value = true
        
        firestore.collection("goals").document(goalId)
            .delete()
            .addOnSuccessListener {
                loadGoals() // Reload goals after deletion
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = exception.message
                _isLoading.value = false
            }
    }
}