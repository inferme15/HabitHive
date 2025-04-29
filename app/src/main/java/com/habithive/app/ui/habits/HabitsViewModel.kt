package com.habithive.app.ui.habits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.model.Habit
import com.habithive.app.utils.PointsCalculator
import java.util.Calendar
import java.util.Date
import java.util.UUID

class HabitsViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _habits = MutableLiveData<List<Habit>>()
    val habits: LiveData<List<Habit>> = _habits
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    init {
        loadHabits()
    }
    
    fun loadHabits() {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
            _isLoading.value = false
            return
        }
        
        firestore.collection("habits")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val habitsList = documents.mapNotNull { document ->
                    document.toObject(Habit::class.java)
                }
                _habits.value = habitsList
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load habits: ${exception.message}"
                _isLoading.value = false
            }
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
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
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
            createdAt = Timestamp.now(),
            completions = emptyList(),
            goal = goal,
            reminderTime = reminderTime,
            reminderDays = reminderDays
        )
        
        firestore.collection("habits")
            .document(habitId)
            .set(habit)
            .addOnSuccessListener {
                loadHabits()
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to add habit: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    fun deleteHabit(habitId: String) {
        _isLoading.value = true
        
        firestore.collection("habits")
            .document(habitId)
            .delete()
            .addOnSuccessListener {
                loadHabits()
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to delete habit: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    fun updateHabit(
        habitId: String,
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
        _isLoading.value = true
        
        val updates = hashMapOf<String, Any>(
            "title" to title,
            "description" to description,
            "type" to type,
            "frequency" to frequency,
            "points" to points,
            "caloriesBurnedPerCompletion" to caloriesBurnedPerCompletion,
            "goal" to goal
        )
        
        if (reminderTime != null) {
            updates["reminderTime"] = reminderTime
        }
        
        updates["reminderDays"] = reminderDays
        
        firestore.collection("habits")
            .document(habitId)
            .update(updates)
            .addOnSuccessListener {
                loadHabits()
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to update habit: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    fun completeHabit(habitId: String) {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
            _isLoading.value = false
            return
        }
        
        // Get the habit first to calculate points
        firestore.collection("habits")
            .document(habitId)
            .get()
            .addOnSuccessListener { document ->
                val habit = document.toObject(Habit::class.java)
                if (habit != null) {
                    // Add to completions
                    val completionTimestamp = Timestamp.now()
                    
                    // Update the habit's completions list
                    firestore.collection("habits")
                        .document(habitId)
                        .update("completions", com.google.firebase.firestore.FieldValue.arrayUnion(completionTimestamp))
                        .addOnSuccessListener {
                            // Record completion in separate collection for easier querying
                            val completionId = UUID.randomUUID().toString()
                            val completion = hashMapOf(
                                "id" to completionId,
                                "habitId" to habitId,
                                "userId" to userId,
                                "completedAt" to completionTimestamp,
                                "points" to habit.points,
                                "caloriesBurned" to habit.caloriesBurnedPerCompletion
                            )
                            
                            firestore.collection("habit_completions")
                                .document(completionId)
                                .set(completion)
                                .addOnSuccessListener {
                                    // Calculate streak for bonus points
                                    val streak = PointsCalculator.calculateStreak(habit)
                                    val bonusPoints = PointsCalculator.calculateAchievementPoints(streak)
                                    
                                    // Update user's total points and calories
                                    firestore.collection("users")
                                        .document(userId)
                                        .update(
                                            "totalPoints", com.google.firebase.firestore.FieldValue.increment(habit.points + bonusPoints.toLong()),
                                            "totalCalories", com.google.firebase.firestore.FieldValue.increment(habit.caloriesBurnedPerCompletion.toLong())
                                        )
                                        .addOnSuccessListener {
                                            loadHabits()
                                        }
                                        .addOnFailureListener { exception ->
                                            _errorMessage.value = "Failed to update user stats: ${exception.message}"
                                            _isLoading.value = false
                                        }
                                }
                                .addOnFailureListener { exception ->
                                    _errorMessage.value = "Failed to record completion: ${exception.message}"
                                    _isLoading.value = false
                                }
                        }
                        .addOnFailureListener { exception ->
                            _errorMessage.value = "Failed to update habit: ${exception.message}"
                            _isLoading.value = false
                        }
                } else {
                    _errorMessage.value = "Habit not found"
                    _isLoading.value = false
                }
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load habit: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    fun getHabitsForDate(date: Date): List<Habit> {
        // Filter habits by date based on frequency
        return _habits.value?.filter { habit ->
            when (habit.frequency) {
                Habit.FREQUENCY_DAILY -> true // Daily habits always show
                Habit.FREQUENCY_WEEKLY -> {
                    // Check if the selected date is within the habit's reminder days
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                    // Convert Sunday(1) to 7, Monday(2) to 1, etc.
                    val adjustedDayOfWeek = if (dayOfWeek == Calendar.SUNDAY) 7 else dayOfWeek - 1
                    habit.reminderDays.contains(adjustedDayOfWeek)
                }
                Habit.FREQUENCY_MONTHLY -> {
                    // Check if the day of month matches
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
                    val habitCreationCal = Calendar.getInstance()
                    habitCreationCal.time = habit.createdAt.toDate()
                    val habitDayOfMonth = habitCreationCal.get(Calendar.DAY_OF_MONTH)
                    dayOfMonth == habitDayOfMonth
                }
                else -> false
            }
        } ?: emptyList()
    }
    
    fun getHabitsByType(type: String): List<Habit> {
        return _habits.value?.filter { it.type == type } ?: emptyList()
    }
    
    fun getTotalPoints(): Int {
        return _habits.value?.sumOf { habit ->
            val completionsCount = habit.completions.size
            completionsCount * habit.points
        } ?: 0
    }
    
    fun getTotalCaloriesBurned(): Int {
        return _habits.value?.sumOf { habit ->
            habit.calculateCaloriesBurned()
        } ?: 0
    }
}