package com.habithive.app.model

import com.google.firebase.Timestamp
import java.util.Calendar
import java.util.Date

data class Habit(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = TYPE_EXERCISE,
    val frequency: Int = FREQUENCY_DAILY,
    val points: Int = 10,
    val caloriesBurnedPerCompletion: Int = 0,
    val createdAt: Timestamp = Timestamp.now(),
    val completions: List<Timestamp> = emptyList(),
    val goal: Int = 1, // Number of times to complete per frequency period
    val reminderTime: String? = null, // Format: "HH:mm"
    val reminderDays: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7) // Days of week (1=Monday, 7=Sunday)
) {
    // No-arg constructor for Firestore
    constructor() : this(
        id = "",
        userId = "",
        title = "",
        description = ""
    )
    
    companion object {
        const val TYPE_EXERCISE = "exercise"
        const val TYPE_NUTRITION = "nutrition"
        const val TYPE_MEDITATION = "meditation"
        const val TYPE_WATER = "water"
        const val TYPE_SLEEP = "sleep"
        const val TYPE_OTHER = "other"
        
        const val FREQUENCY_DAILY = 0
        const val FREQUENCY_WEEKLY = 1
        const val FREQUENCY_MONTHLY = 2
    }
    
    fun isCompletedToday(): Boolean {
        // Check if habit is completed today
        val today = Calendar.getInstance()
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
        today.set(Calendar.SECOND, 0)
        today.set(Calendar.MILLISECOND, 0)
        
        // Get end of day timestamp
        val endOfDay = Calendar.getInstance()
        endOfDay.set(Calendar.HOUR_OF_DAY, 23)
        endOfDay.set(Calendar.MINUTE, 59)
        endOfDay.set(Calendar.SECOND, 59)
        
        return completions.any { timestamp ->
            val date = timestamp.toDate()
            date.after(today.time) && date.before(endOfDay.time)
        }
    }
    
    fun calculatePointsForPeriod(startDate: Date, endDate: Date): Int {
        // Calculate points earned in a specific period
        return completions
            .filter { timestamp ->
                val date = timestamp.toDate()
                date.after(startDate) && date.before(endDate)
            }
            .size * points
    }
    
    fun getCompletionRateForWeek(): Float {
        // Calculate completion rate for current week
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        
        val startOfWeek = calendar.time
        
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        
        val endOfWeek = calendar.time
        
        val completionsThisWeek = completions.count { timestamp ->
            val date = timestamp.toDate()
            date.after(startOfWeek) && date.before(endOfWeek)
        }
        
        val daysInWeek = 7
        
        // For daily habits, expected completions is goal * days in the period
        val expectedCompletions = when (frequency) {
            FREQUENCY_DAILY -> goal * daysInWeek
            FREQUENCY_WEEKLY -> goal
            else -> goal // For monthly, we just use the goal as-is
        }
        
        return if (expectedCompletions > 0) {
            completionsThisWeek.toFloat() / expectedCompletions
        } else {
            0f
        }
    }
    
    fun calculateCaloriesBurned(): Int {
        return completions.size * caloriesBurnedPerCompletion
    }
}