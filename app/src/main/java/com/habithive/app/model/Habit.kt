package com.habithive.app.model

import com.google.firebase.Timestamp
import java.util.Date

data class Habit(
    val id: String = "",
    val userId: String = "",
    val title: String = "",
    val description: String = "",
    val type: String = "", // Running, Cycling, Weightlifting, Custom
    val frequency: String = "", // Daily, Weekly, Custom
    val points: Int = 0,
    val caloriesBurned: Int = 0,
    val createdAt: Timestamp = Timestamp(Date()),
    val completed: Boolean = false,
    val completedDates: List<Timestamp> = listOf()
)
