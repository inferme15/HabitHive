package com.habithive.app.ui.goals.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.habithive.app.model.Goal
import com.google.firebase.Timestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddGoalViewModel : ViewModel() {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val targetPoints = MutableLiveData<String>()
    val targetCalories = MutableLiveData<String>()
    val durationPosition = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val saveStatus = MutableLiveData<SaveStatus>()

    val motivationalQuote = MutableLiveData<String>()

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    enum class SaveStatus {
        SUCCESS,
        ERROR_EMPTY_TITLE,
        ERROR_NOT_AUTHENTICATED,
        ERROR_SAVE_FAILED
    }

    fun saveGoal() {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            saveStatus.value = SaveStatus.ERROR_NOT_AUTHENTICATED
            return
        }

        val goalTitle = title.value.orEmpty().trim()
        val goalDescription = description.value.orEmpty().trim()
        val points = targetPoints.value.orEmpty().toIntOrNull() ?: 0
        val calories = targetCalories.value.orEmpty().toIntOrNull() ?: 0
        val duration = getDurationInDays(durationPosition.value ?: 0)
        val quote = motivationalQuote.value.orEmpty()

        if (goalTitle.isEmpty()) {
            saveStatus.value = SaveStatus.ERROR_EMPTY_TITLE
            return
        }

        isLoading.value = true

        val goal = Goal(
            id = "",
            userId = userId,
            title = goalTitle,
            description = goalDescription,
            targetPoints = points,
            targetCalories = calories,
            duration = duration,
            shared = false,
            currentPoints = 0,
            currentCalories = 0,
            progress = 0.0,
            createdAt = Timestamp.now(),
            completedAt = null,
            completed = false,
            inspirationalQuote = quote
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                firestore.collection("goals")
                    .add(goal)
                    .addOnSuccessListener {
                        saveStatus.postValue(SaveStatus.SUCCESS)
                        isLoading.postValue(false)
                    }
                    .addOnFailureListener {
                        saveStatus.postValue(SaveStatus.ERROR_SAVE_FAILED)
                        isLoading.postValue(false)
                    }
            } catch (e: Exception) {
                saveStatus.postValue(SaveStatus.ERROR_SAVE_FAILED)
                isLoading.postValue(false)
            }
        }
    }

    fun updateQuote(quote: String) {
        motivationalQuote.value = quote
    }

    private fun getDurationInDays(position: Int): Int {
        return when (position) {
            0 -> 1  // Daily = 1 day
            1 -> 7
            2 -> 14
            3 -> 30
            4 -> 90
            else -> 7
        }
    }
}
