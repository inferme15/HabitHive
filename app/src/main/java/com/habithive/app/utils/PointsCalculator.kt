package com.habithive.app.utils

import com.habithive.app.model.Habit
import java.util.Calendar

// Calculate points for a habit based on type, frequency, etc.
fun calculatePoints(habit: Habit): Int {
    // Base points from the habit
    var points = habit.points
    
    // Add bonus points based on habit type
    points += when (habit.type) {
        "Running" -> 5
        "Cycling" -> 4
        "Weightlifting" -> 3
        else -> 2
    }
    
    // Add frequency bonus
    points += when (habit.frequency) {
        "Daily" -> 3
        "Weekly" -> 7
        else -> 1
    }
    
    // Add streak bonus (if applicable)
    val streakBonus = calculateStreakBonus(habit)
    points += streakBonus
    
    return points
}

// Calculate streak bonus based on consecutive completions
private fun calculateStreakBonus(habit: Habit): Int {
    val completedDates = habit.completedDates
    if (completedDates.isEmpty()) return 0
    
    // Sort dates
    val sortedDates = completedDates.sortedBy { it.seconds }
    
    // Check for streak (consecutive days)
    var currentStreak = 1
    var maxStreak = 1
    
    for (i in 1 until sortedDates.size) {
        val prevDate = Calendar.getInstance().apply {
            timeInMillis = sortedDates[i - 1].seconds * 1000
        }
        
        val currentDate = Calendar.getInstance().apply {
            timeInMillis = sortedDates[i].seconds * 1000
        }
        
        // Calculate days difference
        val daysDiff = (currentDate.timeInMillis - prevDate.timeInMillis) / (24 * 60 * 60 * 1000)
        
        if (daysDiff == 1L) {
            // Consecutive day
            currentStreak++
            maxStreak = maxOf(currentStreak, maxStreak)
        } else if (daysDiff > 1) {
            // Streak broken
            currentStreak = 1
        }
    }
    
    // Return bonus based on streak length
    return when {
        maxStreak >= 30 -> 50  // 30+ day streak
        maxStreak >= 14 -> 25  // 14+ day streak
        maxStreak >= 7 -> 15   // 7+ day streak
        maxStreak >= 3 -> 5    // 3+ day streak
        else -> 0
    }
}

// Calculate calories burned based on activity type and duration
fun calculateCaloriesBurned(type: String, durationMinutes: Int, weightKg: Double): Int {
    val caloriesPerMinute = when (type) {
        "Running" -> 0.076 * weightKg  // ~11.4 cal/min for 70kg person
        "Cycling" -> 0.064 * weightKg  // ~9.6 cal/min for 70kg person
        "Weightlifting" -> 0.039 * weightKg  // ~5.9 cal/min for 70kg person
        else -> 0.035 * weightKg  // ~5.3 cal/min for 70kg person
    }
    
    return (caloriesPerMinute * durationMinutes).toInt()
}
