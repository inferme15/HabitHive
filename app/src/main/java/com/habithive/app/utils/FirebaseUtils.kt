package com.habithive.app.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.habithive.app.model.Achievement
import com.habithive.app.model.Habit
import com.habithive.app.model.User
import kotlinx.coroutines.tasks.await
import java.util.Date

// Constants
const val TAG = "FirebaseUtils"

// Get user reference
fun getUserReference(userId: String): DocumentReference {
    return FirebaseFirestore.getInstance().collection("users").document(userId)
}

// Get habits reference
fun getHabitsReference(userId: String): DocumentReference {
    return FirebaseFirestore.getInstance().collection("habits").document(userId)
}

// Get achievements reference
fun getAchievementsReference(userId: String): DocumentReference {
    return FirebaseFirestore.getInstance().collection("achievements").document(userId)
}

// Update user points
suspend fun updateUserPoints(userId: String, pointsToAdd: Int) {
    try {
        val achievementRef = getAchievementsReference(userId)
        val achievementDoc = achievementRef.get().await()
        
        if (achievementDoc.exists()) {
            val achievement = achievementDoc.toObject(Achievement::class.java)
            achievement?.let {
                val updatedPoints = it.totalPoints + pointsToAdd
                val updatedDailyScore = it.dailyScore + pointsToAdd
                val updatedWeeklyScore = it.weeklyScore + pointsToAdd
                
                achievementRef.update(
                    mapOf(
                        "totalPoints" to updatedPoints,
                        "dailyScore" to updatedDailyScore,
                        "weeklyScore" to updatedWeeklyScore
                    )
                ).await()
            }
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error updating user points: ${e.message}")
    }
}

// Reset daily scores
suspend fun resetDailyScores() {
    try {
        val achievementsCollection = FirebaseFirestore.getInstance().collection("achievements")
        val documents = achievementsCollection.get().await()
        
        for (document in documents) {
            achievementsCollection.document(document.id)
                .update("dailyScore", 0)
                .await()
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error resetting daily scores: ${e.message}")
    }
}

// Reset weekly scores
suspend fun resetWeeklyScores() {
    try {
        val achievementsCollection = FirebaseFirestore.getInstance().collection("achievements")
        val documents = achievementsCollection.get().await()
        
        for (document in documents) {
            achievementsCollection.document(document.id)
                .update("weeklyScore", 0)
                .await()
        }
    } catch (e: Exception) {
        Log.e(TAG, "Error resetting weekly scores: ${e.message}")
    }
}

// Subscribe to topic for notifications
fun subscribeToTopic(userId: String) {
    FirebaseMessaging.getInstance().subscribeToTopic("user_$userId")
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Failed to subscribe to topic")
            }
        }
}

// Unsubscribe from topic for notifications
fun unsubscribeFromTopic(userId: String) {
    FirebaseMessaging.getInstance().unsubscribeFromTopic("user_$userId")
        .addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Failed to unsubscribe from topic")
            }
        }
}
