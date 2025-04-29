package com.habithive.app.utils

import com.habithive.app.model.Habit
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Utility class for calculating calories burned, fitness points, and achievement bonuses
 */
object PointsCalculator {
    
    // Calorie calculation multipliers based on exercise type (per kg of body weight per minute)
    private const val RUNNING_MULTIPLIER = 0.076
    private const val CYCLING_MULTIPLIER = 0.064
    private const val WEIGHTLIFTING_MULTIPLIER = 0.039
    private const val OTHER_EXERCISE_MULTIPLIER = 0.035
    
    // Base points per calorie burned
    private const val POINTS_PER_CALORIE = 0.1
    
    /**
     * Calculate calories burned based on exercise type, user weight, and duration
     */
    fun calculateCaloriesBurned(exerciseType: String, weightKg: Int, durationMinutes: Int): Int {
        val multiplier = when (exerciseType) {
            Habit.EXERCISE_RUNNING -> RUNNING_MULTIPLIER
            Habit.EXERCISE_CYCLING -> CYCLING_MULTIPLIER
            Habit.EXERCISE_WEIGHTLIFTING -> WEIGHTLIFTING_MULTIPLIER
            else -> OTHER_EXERCISE_MULTIPLIER
        }
        
        return (multiplier * weightKg * durationMinutes).roundToInt()
    }
    
    /**
     * Calculate fitness points based on calories burned
     */
    fun calculatePointsFromCalories(caloriesBurned: Int): Int {
        return (caloriesBurned * POINTS_PER_CALORIE).roundToInt()
    }
    
    /**
     * Calculate streak for habit
     */
    fun calculateStreak(habit: Habit): Int {
        return habit.calculateCurrentStreak()
    }
    
    /**
     * Calculate bonus points for achievements based on streak length
     * Uses a progressive reward system: longer streaks give exponentially more points
     */
    fun calculateAchievementPoints(streak: Int): Int {
        // No bonus for streaks less than 3
        if (streak < 3) return 0
        
        // Base points for achieving a 3-day streak
        val basePoints = 10
        
        // Calculate using a power function to reward longer streaks exponentially
        val bonus = basePoints * (1.1).pow(streak - 3)
        
        return max(bonus.roundToInt(), 0)
    }
    
    /**
     * Calculate level based on total points
     * Levels increase logarithmically, making higher levels harder to achieve
     */
    fun calculateLevel(totalPoints: Int): Int {
        if (totalPoints <= 0) return 1
        
        // Points required for level 2
        val basePointsForLevel2 = 100
        
        // Calculate level using a logarithmic function
        // Each level requires progressively more points
        val level = 1 + (kotlin.math.ln(totalPoints.toDouble() / basePointsForLevel2) / kotlin.math.ln(1.5)).toInt()
        
        return max(level, 1)
    }
    
    /**
     * Calculate points needed for next level
     */
    fun pointsForNextLevel(currentLevel: Int): Int {
        val basePointsForLevel2 = 100
        return (basePointsForLevel2 * (1.5).pow(currentLevel - 1)).roundToInt()
    }
}