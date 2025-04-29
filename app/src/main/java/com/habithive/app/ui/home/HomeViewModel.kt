package com.habithive.app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.habithive.app.model.Habit
import java.util.Calendar

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    private val _totalPoints = MutableLiveData<Int>()
    val totalPoints: LiveData<Int> = _totalPoints
    
    private val _caloriesBurned = MutableLiveData<Int>()
    val caloriesBurned: LiveData<Int> = _caloriesBurned
    
    private val _dailyScore = MutableLiveData<Int>()
    val dailyScore: LiveData<Int> = _dailyScore
    
    private val _weeklyScore = MutableLiveData<Int>()
    val weeklyScore: LiveData<Int> = _weeklyScore
    
    private val _habitsCount = MutableLiveData<Int>()
    val habitsCount: LiveData<Int> = _habitsCount
    
    private val _completedHabits = MutableLiveData<Int>()
    val completedHabits: LiveData<Int> = _completedHabits
    
    private val _completionRate = MutableLiveData<Int>()
    val completionRate: LiveData<Int> = _completionRate
    
    private val _chartData = MutableLiveData<BarData>()
    val chartData: LiveData<BarData> = _chartData
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        loadUserData()
    }
    
    fun loadUserData() {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: return
        
        // Load user profile data
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _userName.value = document.getString("name") ?: "User"
                    _totalPoints.value = document.getLong("totalPoints")?.toInt() ?: 0
                    _caloriesBurned.value = document.getLong("totalCalories")?.toInt() ?: 0
                }
                loadHabitsData(userId)
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }
    
    private fun loadHabitsData(userId: String) {
        // Load habits data
        firestore.collection("habits")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val habits = documents.mapNotNull { it.toObject(Habit::class.java) }
                
                // Calculate habits statistics
                _habitsCount.value = habits.size
                
                val completed = habits.count { it.isCompletedToday() }
                _completedHabits.value = completed
                
                // Calculate completion rate
                _completionRate.value = if (habits.isNotEmpty()) {
                    (completed * 100) / habits.size
                } else {
                    0
                }
                
                // Calculate scores for today and this week
                calculateScores(habits)
                
                // Create chart data
                createProgressChart(userId)
                
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }
    
    private fun calculateScores(habits: List<Habit>) {
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        
        val weekStart = Calendar.getInstance()
        weekStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        weekStart.set(Calendar.HOUR_OF_DAY, 0)
        weekStart.set(Calendar.MINUTE, 0)
        weekStart.set(Calendar.SECOND, 0)
        weekStart.set(Calendar.MILLISECOND, 0)
        
        // Query completions for today and this week
        firestore.collection("habit_completions")
            .whereEqualTo("userId", auth.currentUser?.uid)
            .whereGreaterThanOrEqualTo("completedAt", weekStart.time)
            .orderBy("completedAt", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                // Calculate daily score
                var dailyPoints = 0
                var weeklyPoints = 0
                
                documents.forEach { doc ->
                    val completedAt = doc.getTimestamp("completedAt")?.toDate()
                    val points = doc.getLong("points")?.toInt() ?: 0
                    
                    if (completedAt != null) {
                        val completionCal = Calendar.getInstance()
                        completionCal.time = completedAt
                        
                        // Check if completed today
                        if (completionCal.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                            completionCal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)
                        ) {
                            dailyPoints += points
                        }
                        
                        // All completions in the query are from this week
                        weeklyPoints += points
                    }
                }
                
                _dailyScore.value = dailyPoints
                _weeklyScore.value = weeklyPoints
            }
    }
    
    private fun createProgressChart(userId: String) {
        // Get the last 7 days of data for the chart
        val endDate = Calendar.getInstance()
        endDate.set(Calendar.HOUR_OF_DAY, 23)
        endDate.set(Calendar.MINUTE, 59)
        endDate.set(Calendar.SECOND, 59)
        
        val startDate = Calendar.getInstance()
        startDate.add(Calendar.DAY_OF_MONTH, -6)
        startDate.set(Calendar.HOUR_OF_DAY, 0)
        startDate.set(Calendar.MINUTE, 0)
        startDate.set(Calendar.SECOND, 0)
        
        firestore.collection("daily_stats")
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", startDate.time)
            .whereLessThanOrEqualTo("date", endDate.time)
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val entries = ArrayList<BarEntry>()
                
                // Create entries for each day
                for (i in 0..6) {
                    val day = Calendar.getInstance()
                    day.add(Calendar.DAY_OF_MONTH, -6 + i)
                    
                    // Find matching document for this day
                    val dayPoints = documents.find { doc ->
                        val docDate = doc.getTimestamp("date")?.toDate()
                        if (docDate != null) {
                            val docCal = Calendar.getInstance()
                            docCal.time = docDate
                            return@find (docCal.get(Calendar.YEAR) == day.get(Calendar.YEAR) &&
                                    docCal.get(Calendar.DAY_OF_YEAR) == day.get(Calendar.DAY_OF_YEAR))
                        }
                        false
                    }?.getLong("points")?.toFloat() ?: 0f
                    
                    entries.add(BarEntry(i.toFloat(), dayPoints))
                }
                
                val dataSet = BarDataSet(entries, "Daily Points")
                val barData = BarData(dataSet)
                
                _chartData.value = barData
            }
    }
}