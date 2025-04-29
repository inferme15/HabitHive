package com.habithive.app.model

data class Achievement(
    val userId: String = "",
    val totalPoints: Int = 0,
    val caloriesBurned: Int = 0,
    val dailyScore: Int = 0,
    val weeklyScore: Int = 0
)

data class LeaderboardUser(
    val id: String = "",
    val name: String = "",
    val score: Int = 0,
    val rank: Int = 0
)

data class Goal(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val targetPoints: Int = 0,
    val targetCalories: Int = 0,
    val duration: String = "", // Daily, Weekly, Monthly
    val completed: Boolean = false,
    val shared: Boolean = false
)
