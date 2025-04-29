package com.habithive.app.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.model.Habit
import com.habithive.app.utils.PointsCalculator
import java.util.UUID

class AddHabitViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _addHabitState = MutableLiveData<AddHabitState>()
    val addHabitState: LiveData<AddHabitState> = _addHabitState
    
    private val _userWeight = MutableLiveData<Int>()
    val userWeight: LiveData<Int> = _userWeight
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        loadUserData()
    }
    
    private fun loadUserData() {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _addHabitState.value = AddHabitState.Error("User not authenticated")
            _isLoading.value = false
            return
        }
        
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _userWeight.value = document.getLong("weight")?.toInt() ?: 70 // Default to 70kg if not found
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _addHabitState.value = AddHabitState.Error("Failed to load user data: ${exception.message}")
                _isLoading.value = false
            }
    }
    
    fun calculateCaloriesBurned(activityType: String, durationMinutes: Int): Int {
        val weight = _userWeight.value ?: 70 // Default to 70kg if not loaded
        return PointsCalculator.calculateCaloriesBurned(activityType, weight, durationMinutes)
    }
    
    fun calculatePoints(caloriesBurned: Int): Int {
        return PointsCalculator.calculatePointsFromCalories(caloriesBurned)
    }
    
    fun addHabit(
        title: String,
        description: String,
        type: String,
        frequency: Int,
        points: Int,
        caloriesBurnedPerCompletion: Int,
        goal: Int,
        reminderTime: String? = null,
        reminderDays: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7)
    ) {
        if (title.isBlank()) {
            _addHabitState.value = AddHabitState.Error("Title cannot be empty")
            return
        }
        
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _addHabitState.value = AddHabitState.Error("User not authenticated")
            _isLoading.value = false
            return
        }
        
        val habitId = UUID.randomUUID().toString()
        
        val habit = Habit(
            id = habitId,
            userId = userId,
            title = title,
            description = description,
            type = type,
            frequency = frequency,
            points = points,
            caloriesBurnedPerCompletion = caloriesBurnedPerCompletion,
            createdAt = com.google.firebase.Timestamp.now(),
            completions = emptyList(),
            goal = goal,
            reminderTime = reminderTime,
            reminderDays = reminderDays
        )
        
        firestore.collection("habits")
            .document(habitId)
            .set(habit)
            .addOnSuccessListener {
                _addHabitState.value = AddHabitState.Success
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _addHabitState.value = AddHabitState.Error("Failed to add habit: ${exception.message}")
                _isLoading.value = false
            }
    }
    
    fun resetState() {
        _addHabitState.value = AddHabitState.Initial
    }
    
    sealed class AddHabitState {
        object Initial : AddHabitState()
        object Success : AddHabitState()
        data class Error(val message: String) : AddHabitState()
    }
}