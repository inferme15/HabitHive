package com.habithive.app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.utils.PointsCalculator
import java.util.Date

class ProfileViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _profileState = MutableLiveData<ProfileState>()
    val profileState: LiveData<ProfileState> = _profileState
    
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail
    
    private val _userWeight = MutableLiveData<Int>()
    val userWeight: LiveData<Int> = _userWeight
    
    private val _userHeight = MutableLiveData<Int>()
    val userHeight: LiveData<Int> = _userHeight
    
    private val _totalPoints = MutableLiveData<Int>()
    val totalPoints: LiveData<Int> = _totalPoints
    
    private val _totalCalories = MutableLiveData<Int>()
    val totalCalories: LiveData<Int> = _totalCalories
    
    private val _userLevel = MutableLiveData<Int>()
    val userLevel: LiveData<Int> = _userLevel
    
    private val _pointsToNextLevel = MutableLiveData<Int>()
    val pointsToNextLevel: LiveData<Int> = _pointsToNextLevel
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _joinDate = MutableLiveData<Date>()
    val joinDate: LiveData<Date> = _joinDate
    
    init {
        loadUserProfile()
    }
    
    fun loadUserProfile() {
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _profileState.value = ProfileState.Error("User not authenticated")
            _isLoading.value = false
            return
        }
        
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    _userName.value = document.getString("name") ?: ""
                    _userEmail.value = document.getString("email") ?: ""
                    _userWeight.value = document.getLong("weight")?.toInt() ?: 70
                    _userHeight.value = document.getLong("height")?.toInt() ?: 170
                    _totalPoints.value = document.getLong("totalPoints")?.toInt() ?: 0
                    _totalCalories.value = document.getLong("totalCalories")?.toInt() ?: 0
                    
                    // Calculate level based on total points
                    val totalPoints = _totalPoints.value ?: 0
                    val userLevel = PointsCalculator.calculateLevel(totalPoints)
                    _userLevel.value = userLevel
                    
                    // Calculate points needed for next level
                    val pointsForNextLevel = PointsCalculator.pointsForNextLevel(userLevel)
                    _pointsToNextLevel.value = pointsForNextLevel
                    
                    // Get join date
                    val createdAt = document.getTimestamp("createdAt")
                    if (createdAt != null) {
                        _joinDate.value = createdAt.toDate()
                    }
                    
                    _profileState.value = ProfileState.Success
                } else {
                    _profileState.value = ProfileState.Error("User profile not found")
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _profileState.value = ProfileState.Error("Failed to load profile: ${exception.message}")
                _isLoading.value = false
            }
    }
    
    fun updateProfile(name: String, weight: Int, height: Int) {
        if (name.isBlank()) {
            _profileState.value = ProfileState.Error("Name cannot be empty")
            return
        }
        
        if (weight <= 0 || height <= 0) {
            _profileState.value = ProfileState.Error("Weight and height must be greater than 0")
            return
        }
        
        _isLoading.value = true
        
        val userId = auth.currentUser?.uid ?: run {
            _profileState.value = ProfileState.Error("User not authenticated")
            _isLoading.value = false
            return
        }
        
        val updates = hashMapOf<String, Any>(
            "name" to name,
            "weight" to weight,
            "height" to height
        )
        
        firestore.collection("users")
            .document(userId)
            .update(updates)
            .addOnSuccessListener {
                _userName.value = name
                _userWeight.value = weight
                _userHeight.value = height
                _profileState.value = ProfileState.Success
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _profileState.value = ProfileState.Error("Failed to update profile: ${exception.message}")
                _isLoading.value = false
            }
    }
    
    fun calculateBMI(): Float {
        val weightKg = _userWeight.value?.toFloat() ?: 70f
        val heightCm = _userHeight.value?.toFloat() ?: 170f
        
        // BMI = weight(kg) / (height(m) * height(m))
        val heightM = heightCm / 100
        return weightKg / (heightM * heightM)
    }
    
    fun getBMICategory(): String {
        val bmi = calculateBMI()
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal weight"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }
    }
    
    fun getLevelProgress(): Int {
        val currentPoints = _totalPoints.value ?: 0
        val currentLevel = _userLevel.value ?: 1
        val previousLevelPoints = PointsCalculator.pointsForNextLevel(currentLevel - 1)
        val nextLevelPoints = PointsCalculator.pointsForNextLevel(currentLevel)
        
        val pointsInCurrentLevel = currentPoints - previousLevelPoints
        val pointsRequiredForLevel = nextLevelPoints - previousLevelPoints
        
        return ((pointsInCurrentLevel.toFloat() / pointsRequiredForLevel.toFloat()) * 100).toInt().coerceIn(0, 100)
    }
    
    sealed class ProfileState {
        object Success : ProfileState()
        data class Error(val message: String) : ProfileState()
    }
}