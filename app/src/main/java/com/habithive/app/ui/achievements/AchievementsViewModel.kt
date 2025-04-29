package com.habithive.app.ui.achievements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.model.Achievement
import java.util.UUID

class AchievementsViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>> = _achievements
    
    private val _unlockedAchievements = MutableLiveData<List<Achievement>>()
    val unlockedAchievements: LiveData<List<Achievement>> = _unlockedAchievements
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    private val _newlyUnlockedAchievement = MutableLiveData<Achievement?>()
    val newlyUnlockedAchievement: LiveData<Achievement?> = _newlyUnlockedAchievement
    
    init {
        loadAchievements()
    }
    
    fun loadAchievements() {
        _isLoading.value = true
        _newlyUnlockedAchievement.value = null
        
        val userId = auth.currentUser?.uid ?: run {
            _errorMessage.value = "User not authenticated"
            _isLoading.value = false
            return
        }
        
        firestore.collection("achievements")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                // Convert documents to Achievement objects
                val achievementsList = documents.mapNotNull { document ->
                    document.toObject(Achievement::class.java)
                }
                
                if (achievementsList.isEmpty()) {
                    // If no achievements exist yet, create the default ones
                    createDefaultAchievements(userId)
                } else {
                    _achievements.value = achievementsList
                    _unlockedAchievements.value = achievementsList.filter { it.isUnlocked }
                    _isLoading.value = false
                }
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to load achievements: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    private fun createDefaultAchievements(userId: String) {
        val defaultAchievements = Achievement.createDefaultAchievements(userId)
        
        val batch = firestore.batch()
        
        defaultAchievements.forEach { achievement ->
            val docRef = firestore.collection("achievements").document(achievement.id)
            batch.set(docRef, achievement)
        }
        
        batch.commit()
            .addOnSuccessListener {
                _achievements.value = defaultAchievements
                _unlockedAchievements.value = emptyList()
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to create default achievements: ${exception.message}"
                _isLoading.value = false
            }
    }
    
    fun checkAchievements(
        streakCount: Int,
        completionCount: Int,
        totalPoints: Int,
        totalCalories: Int
    ) {
        val currentAchievements = _achievements.value ?: return
        var achievementsUpdated = false
        val updatedAchievements = currentAchievements.map { achievement ->
            
            // Skip already unlocked achievements
            if (achievement.isUnlocked) {
                return@map achievement
            }
            
            // Create a copy that we might modify
            var updatedAchievement = achievement
            
            // Update progress based on achievement type
            when (achievement.type) {
                Achievement.TYPE_STREAK -> {
                    if (streakCount >= achievement.threshold && !achievement.isUnlocked) {
                        // Unlock the achievement
                        updatedAchievement = achievement.copy(
                            currentProgress = streakCount,
                            isUnlocked = true,
                            unlockedAt = Timestamp.now()
                        )
                        _newlyUnlockedAchievement.value = updatedAchievement
                        achievementsUpdated = true
                    } else if (streakCount > achievement.currentProgress) {
                        // Just update progress
                        updatedAchievement = achievement.copy(currentProgress = streakCount)
                        achievementsUpdated = true
                    }
                }
                Achievement.TYPE_COMPLETION -> {
                    if (completionCount >= achievement.threshold && !achievement.isUnlocked) {
                        // Unlock the achievement
                        updatedAchievement = achievement.copy(
                            currentProgress = completionCount,
                            isUnlocked = true,
                            unlockedAt = Timestamp.now()
                        )
                        _newlyUnlockedAchievement.value = updatedAchievement
                        achievementsUpdated = true
                    } else if (completionCount > achievement.currentProgress) {
                        // Just update progress
                        updatedAchievement = achievement.copy(currentProgress = completionCount)
                        achievementsUpdated = true
                    }
                }
                Achievement.TYPE_POINTS -> {
                    if (totalPoints >= achievement.threshold && !achievement.isUnlocked) {
                        // Unlock the achievement
                        updatedAchievement = achievement.copy(
                            currentProgress = totalPoints,
                            isUnlocked = true,
                            unlockedAt = Timestamp.now()
                        )
                        _newlyUnlockedAchievement.value = updatedAchievement
                        achievementsUpdated = true
                    } else if (totalPoints > achievement.currentProgress) {
                        // Just update progress
                        updatedAchievement = achievement.copy(currentProgress = totalPoints)
                        achievementsUpdated = true
                    }
                }
                Achievement.TYPE_CALORIES -> {
                    if (totalCalories >= achievement.threshold && !achievement.isUnlocked) {
                        // Unlock the achievement
                        updatedAchievement = achievement.copy(
                            currentProgress = totalCalories,
                            isUnlocked = true,
                            unlockedAt = Timestamp.now()
                        )
                        _newlyUnlockedAchievement.value = updatedAchievement
                        achievementsUpdated = true
                    } else if (totalCalories > achievement.currentProgress) {
                        // Just update progress
                        updatedAchievement = achievement.copy(currentProgress = totalCalories)
                        achievementsUpdated = true
                    }
                }
            }
            
            updatedAchievement
        }
        
        if (achievementsUpdated) {
            // Update in Firestore
            updateAchievementsInFirestore(updatedAchievements)
            
            // Update local LiveData
            _achievements.value = updatedAchievements
            _unlockedAchievements.value = updatedAchievements.filter { it.isUnlocked }
            
            // If an achievement was unlocked, add its points to the user's total
            val unlockedAchievement = _newlyUnlockedAchievement.value
            if (unlockedAchievement != null) {
                addAchievementPointsToUser(unlockedAchievement)
            }
        }
    }
    
    private fun updateAchievementsInFirestore(achievements: List<Achievement>) {
        val batch = firestore.batch()
        
        achievements.forEach { achievement ->
            val docRef = firestore.collection("achievements").document(achievement.id)
            batch.set(docRef, achievement)
        }
        
        batch.commit()
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to update achievements: ${exception.message}"
            }
    }
    
    private fun addAchievementPointsToUser(achievement: Achievement) {
        val userId = auth.currentUser?.uid ?: return
        
        firestore.collection("users")
            .document(userId)
            .update("totalPoints", com.google.firebase.firestore.FieldValue.increment(achievement.points.toLong()))
            .addOnFailureListener { exception ->
                _errorMessage.value = "Failed to update user points: ${exception.message}"
            }
    }
    
    fun clearNewlyUnlockedAchievement() {
        _newlyUnlockedAchievement.value = null
    }
    
    fun getTotalAchievementPoints(): Int {
        return _unlockedAchievements.value?.sumOf { it.points } ?: 0
    }
}