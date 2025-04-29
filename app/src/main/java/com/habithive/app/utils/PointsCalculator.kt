package com.habithive.app.utils

import com.habithive.app.model.Habit

/**
 * Utility class for calculating points and calories for different activities
 * Uses formula: weight×activity-specific coefficient×duration
 */
object PointsCalculator {
    
    // Activity type constants
    const val ACTIVITY_RUNNING = "running"
    const val ACTIVITY_CYCLING = "cycling"
    const val ACTIVITY_WEIGHTLIFTING = "weightlifting"
    const val ACTIVITY_OTHER = "other"
    
    // Coefficients for calorie calculation
    private const val COEFFICIENT_RUNNING = 0.076
    private const val COEFFICIENT_CYCLING = 0.064
    private const val COEFFICIENT_WEIGHTLIFTING = 0.039
    private const val COEFFICIENT_OTHER = 0.035
    
    /**
     * Calculate calories burned based on activity type, weight (kg), and duration (minutes)
     * 
     * @param activityType Type of exercise
     * @param weightKg User's weight in kilograms
     * @param durationMinutes Duration of exercise in minutes
     * @return Estimated calories burned
     */
    fun calculateCaloriesBurned(activityType: String, weightKg: Int, durationMinutes: Int): Int {
        val coefficient = when (activityType) {
            ACTIVITY_RUNNING -> COEFFICIENT_RUNNING
            ACTIVITY_CYCLING -> COEFFICIENT_CYCLING
            ACTIVITY_WEIGHTLIFTING -> COEFFICIENT_WEIGHTLIFTING
            else -> COEFFICIENT_OTHER
        }
        
        return (weightKg * coefficient * durationMinutes).toInt()
    }
    
    /**
     * Calculate points based on calories burned
     * 
     * @param caloriesBurned Calories burned during activity
     * @return Reward points (1 point per 10 calories)
     */
    fun calculatePointsFromCalories(caloriesBurned: Int): Int {
        return caloriesBurned / 10
    }
    
    /**
     * Calculate habit streak (consecutive days of completion)
     * 
     * @param habit The habit to calculate streak for
     * @return Current streak length
     */
    fun calculateStreak(habit: Habit): Int {
        val completionDates = habit.completions
            .map { it.toDate() }
            .sortedDescending() // Sort from most recent to oldest
        
        if (completionDates.isEmpty()) {
            return 0
        }
        
        var streak = 1
        var previousDate = completionDates[0]
        
        for (i in 1 until completionDates.size) {
            val currentDate = completionDates[i]
            val daysDifference = (previousDate.time - currentDate.time) / (1000 * 60 * 60 * 24)
            
            if (daysDifference == 1L) {
                // Consecutive day
                streak++
                previousDate = currentDate
            } else {
                // Streak broken
                break
            }
        }
        
        return streak
    }
    
    /**
     * Calculate achievement points based on streak length
     * 
     * @param streakLength Length of current streak
     * @return Bonus points for achievement
     */
    fun calculateAchievementPoints(streakLength: Int): Int {
        return when {
            streakLength >= 30 -> 500  // Monthly achiever
            streakLength >= 7 -> 100   // Weekly achiever
            streakLength >= 3 -> 30    // Getting started
            else -> 0
        }
    }
}