package com.habithive.app.model

import com.google.firebase.Timestamp

data class Achievement(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = TYPE_STREAK,
    val iconResId: Int = 0,
    val points: Int = 0,
    val threshold: Int = 0,
    val currentProgress: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedAt: Timestamp? = null,

    // ➕ Add these fields for stats
    val totalPoints: Int = 0,
    val caloriesBurned: Int = 0,
    val dailyScore: Int = 0,
    val weeklyScore: Int = 0
)
{
    // No-arg constructor for Firestore
    constructor() : this(
        id = "",
        userId = "",
        title = "",
        description = ""
    )
    
    companion object {
        const val TYPE_STREAK = "streak"
        const val TYPE_COMPLETION = "completion"
        const val TYPE_POINTS = "points"
        const val TYPE_CALORIES = "calories"
        
        // Create predefined achievements
        fun createDefaultAchievements(userId: String): List<Achievement> {
            return listOf(
                // Streak achievements
                Achievement(
                    id = "streak_3",
                    userId = userId,
                    title = "Getting Started",
                    description = "Complete habits for 3 consecutive days",
                    type = TYPE_STREAK,
                    points = 30,
                    threshold = 3,
                    currentProgress = 0
                ),
                Achievement(
                    id = "streak_7",
                    userId = userId,
                    title = "Consistent!",
                    description = "Complete habits for 7 consecutive days",
                    type = TYPE_STREAK,
                    points = 100,
                    threshold = 7,
                    currentProgress = 0
                ),
                Achievement(
                    id = "streak_30",
                    userId = userId,
                    title = "Habit Master",
                    description = "Complete habits for 30 consecutive days",
                    type = TYPE_STREAK,
                    points = 500,
                    threshold = 30,
                    currentProgress = 0
                ),
                
                // Completion achievements
                Achievement(
                    id = "complete_10",
                    userId = userId,
                    title = "Beginner",
                    description = "Complete any habit 10 times",
                    type = TYPE_COMPLETION,
                    points = 50,
                    threshold = 10,
                    currentProgress = 0
                ),
                Achievement(
                    id = "complete_50",
                    userId = userId,
                    title = "Dedicated",
                    description = "Complete any habit 50 times",
                    type = TYPE_COMPLETION,
                    points = 200,
                    threshold = 50,
                    currentProgress = 0
                ),
                Achievement(
                    id = "complete_100",
                    userId = userId,
                    title = "Centurion",
                    description = "Complete any habit 100 times",
                    type = TYPE_COMPLETION,
                    points = 500,
                    threshold = 100,
                    currentProgress = 0
                ),
                
                // Points achievements
                Achievement(
                    id = "points_500",
                    userId = userId,
                    title = "Point Collector",
                    description = "Earn 500 points",
                    type = TYPE_POINTS,
                    points = 100,
                    threshold = 500,
                    currentProgress = 0
                ),
                Achievement(
                    id = "points_2000",
                    userId = userId,
                    title = "Point Hoarder",
                    description = "Earn 2000 points",
                    type = TYPE_POINTS,
                    points = 300,
                    threshold = 2000,
                    currentProgress = 0
                ),
                Achievement(
                    id = "points_5000",
                    userId = userId,
                    title = "Point Master",
                    description = "Earn 5000 points",
                    type = TYPE_POINTS,
                    points = 750,
                    threshold = 5000,
                    currentProgress = 0
                ),
                
                // Calories achievements
                Achievement(
                    id = "calories_1000",
                    userId = userId,
                    title = "Calorie Burner",
                    description = "Burn 1000 calories",
                    type = TYPE_CALORIES,
                    points = 150,
                    threshold = 1000,
                    currentProgress = 0
                ),
                Achievement(
                    id = "calories_5000",
                    userId = userId,
                    title = "Fitness Enthusiast",
                    description = "Burn 5000 calories",
                    type = TYPE_CALORIES,
                    points = 400,
                    threshold = 5000,
                    currentProgress = 0
                ),
                Achievement(
                    id = "calories_10000",
                    userId = userId,
                    title = "Fitness Guru",
                    description = "Burn 10000 calories",
                    type = TYPE_CALORIES,
                    points = 800,
                    threshold = 10000,
                    currentProgress = 0
                )
            )
        }
    }
    
    fun getProgressPercentage(): Int {
        return if (threshold > 0) {
            ((currentProgress.toFloat() / threshold.toFloat()) * 100).toInt().coerceAtMost(100)
        } else {
            0
        }
    }
}