package com.habithive.app.model

import com.google.firebase.Timestamp
import java.util.Date

data class Goal(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val targetPoints: Int = 0,
    val targetCalories: Int = 0,
    val duration: Int = 7, // Default to 7 days
    val shared: Boolean = false,
    val currentPoints: Int = 0,
    val currentCalories: Int = 0,
    val progress: Double = 0.0,
    val createdAt: Timestamp = Timestamp.now(),
    val completedAt: Timestamp? = null,
    val completed: Boolean = false,
    val inspirationalQuote: String = "" // Motivational quote that inspired this goal
) {
    // No-arg constructor for Firestore
    constructor() : this(
        id = "",
        userId = "",
        title = "",
        description = ""
    )
    
    fun calculateProgress(): Double {
        if (targetPoints <= 0 && targetCalories <= 0) return 0.0
        
        val pointsProgress = if (targetPoints > 0) (currentPoints.toDouble() / targetPoints) else 0.0
        val caloriesProgress = if (targetCalories > 0) (currentCalories.toDouble() / targetCalories) else 0.0
        
        // If both targets are set, use the average progress
        return if (targetPoints > 0 && targetCalories > 0) {
            (pointsProgress + caloriesProgress) / 2
        } else if (targetPoints > 0) {
            pointsProgress
        } else {
            caloriesProgress
        }
    }
    
    fun getRemainingDays(): Int {
        val now = Date()
        val endDate = Date(createdAt.toDate().time + (duration * 24 * 60 * 60 * 1000))
        val diff = endDate.time - now.time
        val remainingDays = (diff / (24 * 60 * 60 * 1000)).toInt()
        return if (remainingDays < 0) 0 else remainingDays
    }
    
    fun isExpired(): Boolean {
        return getRemainingDays() <= 0
    }
    
    fun getProgressPercentage(): Int {
        return (calculateProgress() * 100).toInt().coerceIn(0, 100)
    }
}