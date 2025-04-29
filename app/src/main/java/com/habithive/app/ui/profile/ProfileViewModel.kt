package com.habithive.app.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> = _userEmail
    
    private val _gender = MutableLiveData<String>()
    val gender: LiveData<String> = _gender
    
    private val _health = MutableLiveData<String>()
    val health: LiveData<String> = _health
    
    private val _height = MutableLiveData<String>()
    val height: LiveData<String> = _height
    
    private val _weight = MutableLiveData<String>()
    val weight: LiveData<String> = _weight
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        loadUserProfile()
    }
    
    private fun loadUserProfile() {
        _isLoading.value = true
        
        // Set initial values from Firebase Auth
        val user = auth.currentUser
        if (user != null) {
            _userEmail.value = user.email ?: ""
            _userName.value = user.displayName ?: ""
            
            // Load additional profile data from Firestore
            firestore.collection("users")
                .document(user.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        _userName.value = document.getString("name") ?: user.displayName ?: ""
                        _gender.value = document.getString("gender") ?: "Not specified"
                        _health.value = document.getString("health") ?: "Good"
                        _height.value = document.getLong("height")?.toString() + " cm" ?: "Not specified"
                        _weight.value = document.getLong("weight")?.toString() + " kg" ?: "Not specified"
                    }
                    _isLoading.value = false
                }
                .addOnFailureListener {
                    _isLoading.value = false
                }
        } else {
            _isLoading.value = false
        }
    }
    
    fun logout() {
        auth.signOut()
    }
    
    fun updateProfile(name: String, gender: String, health: String, height: Int, weight: Int) {
        _isLoading.value = true
        
        val user = auth.currentUser
        if (user != null) {
            val userData = hashMapOf(
                "name" to name,
                "email" to user.email,
                "gender" to gender,
                "health" to health,
                "height" to height,
                "weight" to weight
            )
            
            firestore.collection("users")
                .document(user.uid)
                .set(userData)
                .addOnSuccessListener {
                    loadUserProfile()
                }
                .addOnFailureListener {
                    _isLoading.value = false
                }
        } else {
            _isLoading.value = false
        }
    }
}