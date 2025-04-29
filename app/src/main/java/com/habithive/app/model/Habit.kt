package com.habithive.app.model

import com.google.firebase.Timestamp
import java.util.Date

data class Habit(
    var id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val frequency: String = "",
    val streakTarget: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val completedCount: Int = 0,
    val pointsPerCompletion: Int = 10,
    val caloriesPerCompletion: Int = 0,
    val type: String = "",
    val activityType: String = "",
    val duration: Int = 0,
    val createdAt: Timestamp = Timestamp(Date()),
    val lastCompletedAt: Timestamp? = null,
    var lastCompletionStatus: Boolean = false
) {
    // Method to check if habit was completed today
    fun isCompletedToday(): Boolean {
        val lastCompleted = lastCompletedAt?.toDate() ?: return false
        val today = Date()
        
        // Compare year, month, and day
        val lastCalendar = java.util.Calendar.getInstance().apply { time = lastCompleted }
        val todayCalendar = java.util.Calendar.getInstance().apply { time = today }
        
        return lastCalendar.get(java.util.Calendar.YEAR) == todayCalendar.get(java.util.Calendar.YEAR) &&
               lastCalendar.get(java.util.Calendar.MONTH) == todayCalendar.get(java.util.Calendar.MONTH) &&
               lastCalendar.get(java.util.Calendar.DAY_OF_MONTH) == todayCalendar.get(java.util.Calendar.DAY_OF_MONTH)
    }
}