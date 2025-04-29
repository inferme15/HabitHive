package com.habithive.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.model.Achievement
import com.habithive.app.model.Habit
import com.habithive.app.utils.PointsCalculator
import java.util.Calendar
import java.util.Date

class HomeViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    private val _userLevel = MutableLiveData<Int>()
    val userLevel: LiveData<Int> = _userLevel
    
    private val _totalPoints = MutableLiveData<Int>()
    val totalPoints: LiveData<Int> = _totalPoints
    
    private val _totalCalories = MutableLiveData<Int>()
    val totalCalories: LiveData<Int> = _totalCalories
    
    private val _todaysHabits = MutableLiveData<List<Habit>>()
    val todaysHabits: LiveData<List<Habit>> = _todaysHabits
    
    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate
    
    private val _habits = MutableLiveData<List<Habit>>()
    
    private val _habitsForSelectedDate = MediatorLiveData<List<Habit>>()
    val habitsForSelectedDate: LiveData<List<Habit>> = _habitsForSelectedDate
    
    private val _recentAchievements = MutableLiveData<List<Achievement>>()
    val recentAchievements: LiveData<List<Achievement>> = _recentAchievements
    
    private val _streak = MutableLiveData<Int>()
    val streak: LiveData<Int> = _streak
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    init {
        // Set today as the default selected date
        _selectedDate.value = Calendar.getInstance()
        
        // Setup the mediator to update habits when either all habits or selected date changes
        _habitsForSelectedDate.addSource(_habits) { habits ->
            updateHabitsForSelectedDate(habits, _selectedDate.value ?: Calendar.getInstance())
        }
        
        _habitsForSelectedDate.addSource(_selectedDate) { date ->
            updateHabitsForSelectedDate(_habits.value ?: emptyList(), date)
        }
        
        loadUserData()
        loadHabits()
        loadRecentAchievements()
    }
    
    private fun updateHabitsForSelectedDate(habits: List<Habit>, date: Calendar) {
        val habitsForDate = habits.filter { habit ->
            shouldShowHabitOnDate(habit, date)
        }
        _habitsForSelectedDate.value = habitsForDate
    }
    
    private fun shouldShowHabitOnDate(habit: Habit, date: Calendar): Boolean {
        return when (habit.frequency) {
            Habit.FREQUENCY_DAILY -> true // Daily habits always show
            Habit.FREQUENCY_WEEKLY -> {
                // Check if the selected date is within the habit's reminder days
                val dayOfWeek = date.get(Calendar.DAY_OF_WEEK)
                // Convert Sunday(1) to 7, Monday(2) to 1, etc.
                val adjustedDayOfWeek = if (dayOfWeek == Calendar.SUNDAY) 7 else dayOfWeek - 1
                habit.reminderDays.contains(adjustedDayOfWeek)
            }
            Habit.FREQUENCY_MONTHLY -> {
                // Check if the day of month matches
                val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
                val habitCreationCal = Calendar.getInstance()
                habitCreationCal.time = habit.createdAt.toDate()
                val habitDayOfMonth = habitCreationCal.get(Calendar.DAY_OF_MONTH)
                dayOfMonth == habitDayOfMonth
            }
            else -> false
        }
    }
    
    fun setSelectedDate(date: Calendar) {
        _selectedDate.value = date
    }
    
    private fun loadUserData() {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
            _isLoading.value = false
            return
        }
        
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _userName.value = document.getString("name") ?: ""
                    _totalPoints.value = document.getLong("totalPoints")?.toInt() ?: 0
                    _totalCalories.value = document.getLong("totalCalories")?.toInt() ?: 0
                    
                    // Calculate level
                    val totalPoints = _totalPoints.value ?: 0
                    _userLevel.value = PointsCalculator.calculateLevel(totalPoints)
                } else {
                    _errorMessage.value = "User profile not found"
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load profile: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    private fun loadHabits() {
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
                
                // Calculate streak from habits
                calculateStreak(habitsList)
                
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load habits: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    private fun calculateStreak(habits: List<Habit>) {
        // Find the maximum streak among all habits
        val maxStreak = habits.maxOfOrNull { it.calculateCurrentStreak() } ?: 0
        _streak.value = maxStreak
    }
    
    private fun loadRecentAchievements() {
        val userId = auth.currentUser?.uid ?: return
        
        firestore.collection("achievements")
            .whereEqualTo("userId", userId)
            .whereEqualTo("isUnlocked", true)
            .orderBy("unlockedAt", Query.Direction.DESCENDING)
            .limit(3) // Get only the 3 most recent achievements
            .get()
            .addOnSuccessListener { documents ->
                val achievementsList = documents.mapNotNull { document ->
                    document.toObject(Achievement::class.java)
                }
                _recentAchievements.value = achievementsList
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load achievements: ${exception.message}"
            }
    }
    
    fun completeHabit(habitId: String) {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
            _isLoading.value = false
            return
        }
        
        // Get the habit
        firestore.collection("habits")
            .document(habitId)
            .get()
            .addOnSuccessListener { document ->
                val habit = document.toObject(Habit::class.java)
                if (habit != null) {
                    // Check if already completed today
                    if (habit.isCompletedToday()) {
                        _errorMessage.value = "Habit already completed today"
                        _isLoading.value = false
                        return@addOnSuccessListener
                    }
                    
                    // Add to completions
                    val completionTimestamp = com.google.firebase.Timestamp.now()
                    
                    // Update the habit's completions list
                    firestore.collection("habits")
                        .document(habitId)
                        .update("completions", com.google.firebase.firestore.FieldValue.arrayUnion(completionTimestamp))
                        .addOnSuccessListener {
                            // Record completion in separate collection for easier querying
                            val completionId = java.util.UUID.randomUUID().toString()
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
                                    val streak = habit.calculateCurrentStreak() + 1 // +1 for the completion we just added
                                    val bonusPoints = PointsCalculator.calculateAchievementPoints(streak)
                                    
                                    // Update user's total points and calories
                                    firestore.collection("users")
                                        .document(userId)
                                        .update(
                                            "totalPoints", com.google.firebase.firestore.FieldValue.increment(habit.points + bonusPoints.toLong()),
                                            "totalCalories", com.google.firebase.firestore.FieldValue.increment(habit.caloriesBurnedPerCompletion.toLong())
                                        )
                                        .addOnSuccessListener {
                                            // Reload data
                                            loadUserData()
                                            loadHabits()
                                            _isLoading.value = false
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
    
    fun getProgressForDate(date: Calendar): Int {
        val habitsForDate = _habits.value?.filter { habit ->
            shouldShowHabitOnDate(habit, date)
        } ?: emptyList()
        
        if (habitsForDate.isEmpty()) {
            return 100 // No habits scheduled for this date means 100% complete
        }
        
        val completedCount = habitsForDate.count { habit ->
            habit.isCompletedOnDate(date.time)
        }
        
        return ((completedCount.toFloat() / habitsForDate.size.toFloat()) * 100).toInt()
    }
    
    fun refreshData() {
        loadUserData()
        loadHabits()
        loadRecentAchievements()
    }
}