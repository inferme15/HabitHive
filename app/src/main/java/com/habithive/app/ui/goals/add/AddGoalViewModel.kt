package com.habithive.app.ui.goals.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.utils.QuotesUtil
import java.util.Date

class AddGoalViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val targetPoints = MutableLiveData<String>()
    val targetCalories = MutableLiveData<String>()
    val durationPosition = MutableLiveData<Int>()
    val isShared = MutableLiveData<Boolean>()
    
    private val _saveStatus = MutableLiveData<SaveStatus>()
    val saveStatus: LiveData<SaveStatus> = _saveStatus
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    // Motivational quote functionality
    private val _currentQuote = MutableLiveData<String>()
    val currentQuote: LiveData<String> = _currentQuote
    
    init {
        // Initialize default values
        durationPosition.value = 0
        isShared.value = false
        
        // Initialize with a random motivational quote
        _currentQuote.value = QuotesUtil.getRandomQuote(QuotesUtil.QuoteCategory.MOTIVATION)
    }
    
    fun saveGoal() {
        _isLoading.value = true
        
        // Validate input
        if (title.value.isNullOrEmpty()) {
            _saveStatus.value = SaveStatus.ERROR_EMPTY_TITLE
            _isLoading.value = false
            return
        }
        
        val userId = auth.currentUser?.uid
        if (userId == null) {
            _saveStatus.value = SaveStatus.ERROR_NOT_AUTHENTICATED
            _isLoading.value = false
            return
        }
        
        // Convert string inputs to appropriate types
        val points = targetPoints.value?.toIntOrNull() ?: 0
        val calories = targetCalories.value?.toIntOrNull() ?: 0
        
        // Create goal object
        val goal = hashMapOf(
            "userId" to userId,
            "title" to title.value,
            "description" to description.value,
            "targetPoints" to points,
            "targetCalories" to calories,
            "duration" to getDurationInDays(durationPosition.value ?: 0),
            "shared" to (isShared.value ?: false),
            "currentPoints" to 0,
            "currentCalories" to 0,
            "progress" to 0.0,
            "createdAt" to Date(),
            "completedAt" to null
        )
        
        // Include the motivational quote with the goal data
        val goalWithQuote = includeQuoteWithGoal(goal)
        
        // Save to Firestore
        firestore.collection("goals")
            .add(goalWithQuote)
            .addOnSuccessListener {
                _saveStatus.value = SaveStatus.SUCCESS
                _isLoading.value = false
            }
            .addOnFailureListener {
                _saveStatus.value = SaveStatus.ERROR_SAVE_FAILED
                _isLoading.value = false
            }
    }
    
    private fun getDurationInDays(position: Int): Int {
        return when (position) {
            0 -> 7     // One week
            1 -> 14    // Two weeks
            2 -> 30    // One month
            3 -> 90    // Three months
            else -> 7  // Default to one week
        }
    }
    
    sealed class SaveStatus {
        object SUCCESS : SaveStatus()
        object ERROR_EMPTY_TITLE : SaveStatus()
        object ERROR_NOT_AUTHENTICATED : SaveStatus()
        object ERROR_SAVE_FAILED : SaveStatus()
    }
    
    /**
     * Update the current quote with the provided quote
     * @param quote The full quote string to store
     */
    fun updateQuote(quote: String) {
        _currentQuote.value = quote
    }
    
    /**
     * Include the inspirational quote with the goal when saving
     * This updates the goal creation process to save the quote that inspired the user
     */
    private fun includeQuoteWithGoal(goalData: HashMap<String, Any>): HashMap<String, Any> {
        val currentQuoteValue = _currentQuote.value
        if (!currentQuoteValue.isNullOrEmpty()) {
            goalData["inspirationalQuote"] = currentQuoteValue
        }
        return goalData
    }
}