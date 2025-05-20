package com.habithive.app.data.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

/**
 * Model class for Exercise data
 */
data class Exercise(
    @DocumentId
    val id: String = "",
    val userId: String = "", // ID of the user who logged this exercise
    val type: String = "",   // Type of exercise (Running, Swimming, etc.)
    val durationMinutes: Int = 0, // Duration in minutes
    val caloriesBurned: Int = 0,  // Total calories burned
    val pointsEarned: Int = 0,    // Points earned (1 point per 10 calories)
    val notes: String = "",       // Optional notes
    val date: Date = Date() ,      // Date and time of exercise
    val completed: Boolean = true // ✅ Add this line
)