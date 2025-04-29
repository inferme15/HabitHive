package com.habithive.app.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.model.Habit

class HabitsViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun loadHabits() {
        val userId = auth.currentUser?.uid ?: return
        
        _isLoading.value = true
        
        firestore.collection("habits")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val habitsList = documents.mapNotNull { document ->
                    document.toObject(Habit::class.java).apply {
                        id = document.id
                    }
                }
                _habits.value = habitsList
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }
    
    fun deleteHabit(habitId: String) {
        firestore.collection("habits")
            .document(habitId)
            .delete()
            .addOnSuccessListener {
                // Refresh the habits list after deletion
                loadHabits()
            }
    }
}