package com.habithive.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        _loginState.value = LoginState.Idle
    }
    
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and password cannot be empty")
            return
        }
        
        _isLoading.value = true
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _loginState.value = LoginState.Success
                _isLoading.value = false
            }
            .addOnFailureListener { e ->
                _loginState.value = LoginState.Error(e.message ?: "Authentication failed")
                _isLoading.value = false
            }
    }
    
    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and password cannot be empty")
            return
        }
        
        if (password.length < 6) {
            _loginState.value = LoginState.Error("Password should be at least 6 characters")
            return
        }
        
        _isLoading.value = true
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // Create user profile in Firestore
                createUserProfile(it.user?.uid ?: "")
                _loginState.value = LoginState.Success
                _isLoading.value = false
            }
            .addOnFailureListener { e ->
                _loginState.value = LoginState.Error(e.message ?: "Registration failed")
                _isLoading.value = false
            }
    }
    
    private fun createUserProfile(userId: String) {
        if (userId.isBlank()) return
        
        val userMap = hashMapOf(
            "name" to "User",
            "totalPoints" to 0,
            "totalCalories" to 0,
            "gender" to "Not specified",
            "health" to "Good",
            "height" to 175,
            "weight" to 70
        )
        
        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .set(userMap)
    }
    
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}