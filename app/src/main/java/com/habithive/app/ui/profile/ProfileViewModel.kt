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

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender

    private val _health = MutableLiveData<String>()
    val health: LiveData<String> = _health

    private val _height = MutableLiveData<String>()
    val height: LiveData<String> = _height

    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight

    init {
        // Initialize with current user's email
        auth.currentUser?.let { user ->
            _email.value = user.email ?: ""
        }
    }

    fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            _isLoading.value = true

            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _userName.value = document.getString("name") ?: ""
                        _email.value = document.getString("email") ?: ""
                        _gender.value = document.getString("gender") ?: ""
                        _health.value = document.getString("health") ?: ""

                        // Handle height which might be a number
                        try {
                            // Try to get as number first
                            val heightValue = document.get("height")
                            _height.value = heightValue?.toString() ?: ""
                        } catch (e: Exception) {
                            // Fallback to empty string on error
                            _height.value = ""
                        }

                        // Handle weight which might be a number
                        try {
                            // Try to get as number first
                            val weightValue = document.get("weight")
                            _weight.value = weightValue?.toString() ?: ""
                        } catch (e: Exception) {
                            // Fallback to empty string on error
                            _weight.value = ""
                        }
                    }
                    _isLoading.value = false
                }
                .addOnFailureListener { e ->
                    // Handle error
                    _isLoading.value = false
                }
        }
    }

    fun logout() {
        auth.signOut()
    }
}