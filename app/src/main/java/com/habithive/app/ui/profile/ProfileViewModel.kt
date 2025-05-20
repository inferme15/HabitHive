package com.habithive.app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val userName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val gender = MutableLiveData<String>()
    val health = MutableLiveData<String>()
    val height = MutableLiveData<String>()
    val weight = MutableLiveData<String>()
    val dateOfBirth = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val bmi = MutableLiveData<String>()
    val shareGoal = MutableLiveData<Boolean>() // ✅ Added this

    init {
        auth.currentUser?.let { user ->
            email.value = user.email ?: ""
        }
    }

    fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return
        _isLoading.value = true

        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { doc ->
                userName.value = doc.getString("name") ?: ""
                email.value = doc.getString("email") ?: ""
                gender.value = doc.getString("gender") ?: ""
                health.value = doc.getString("health") ?: ""
                dateOfBirth.value = doc.getString("dateOfBirth") ?: ""
                shareGoal.value = doc.getBoolean("shareGoal") ?: false

                height.value = doc.get("height")?.toString() ?: ""
                weight.value = doc.get("weight")?.toString() ?: ""

                doc.getString("dateOfBirth")?.let {
                    age.value = calculateAge(it)
                }

                calculateAndSetBmi()
                _isLoading.value = false
            }
            .addOnFailureListener {
                _isLoading.value = false
            }
    }

    fun updateShareGoalPreference(share: Boolean) {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId)
            .update("shareGoal", share)
            .addOnSuccessListener {
                shareGoal.value = share
            }
    }

    private fun calculateAge(dateOfBirth: String): String {
        try {
            val parts = dateOfBirth.split("/")
            if (parts.size == 3) {
                val year = parts[2].toInt()
                val month = parts[1].toInt() - 1
                val day = parts[0].toInt()

                val dobCal = java.util.Calendar.getInstance().apply {
                    set(year, month, day)
                }

                val today = java.util.Calendar.getInstance()
                var age = today.get(java.util.Calendar.YEAR) - dobCal.get(java.util.Calendar.YEAR)
                if (today.get(java.util.Calendar.DAY_OF_YEAR) < dobCal.get(java.util.Calendar.DAY_OF_YEAR)) {
                    age--
                }

                return "$age years"
            }
        } catch (_: Exception) { }
        return ""
    }

    private fun calculateAndSetBmi() {
        try {
            val h = height.value?.toDoubleOrNull()?.div(100) ?: return
            val w = weight.value?.toDoubleOrNull() ?: return
            val bmiVal = w / (h * h)
            val category = when {
                bmiVal < 18.5 -> "Underweight"
                bmiVal < 25.0 -> "Normal"
                bmiVal < 30.0 -> "Overweight"
                else -> "Obese"
            }
            bmi.value = String.format("%.1f", bmiVal) + " ($category)"
        } catch (e: Exception) {
            bmi.value = "Not available"
        }
    }

    fun logout() {
        auth.signOut()
    }
}
