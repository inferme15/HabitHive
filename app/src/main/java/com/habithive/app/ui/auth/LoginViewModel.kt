package com.habithive.app.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        checkAuthState()
    }
    
    private fun checkAuthState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _loginState.value = LoginState.LoggedIn(currentUser.uid)
        } else {
            _loginState.value = LoginState.LoggedOut
        }
    }
    
    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and password cannot be empty")
            return
        }
        
        _isLoading.value = true
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        _loginState.value = LoginState.LoggedIn(user.uid)
                    }
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }
    
    fun register(email: String, password: String, name: String) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _loginState.value = LoginState.Error("All fields are required")
            return
        }
        
        if (password.length < 6) {
            _loginState.value = LoginState.Error("Password must be at least 6 characters")
            return
        }
        
        _isLoading.value = true
        
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Create user profile in Firestore
                        val userData = hashMapOf(
                            "uid" to user.uid,
                            "name" to name,
                            "email" to email,
                            "totalPoints" to 0,
                            "totalCalories" to 0,
                            "createdAt" to com.google.firebase.Timestamp.now(),
                            "weight" to 70, // Default weight in kg
                            "height" to 170 // Default height in cm
                        )
                        
                        firestore.collection("users")
                            .document(user.uid)
                            .set(userData)
                            .addOnCompleteListener { firestoreTask ->
                                _isLoading.value = false
                                if (firestoreTask.isSuccessful) {
                                    _loginState.value = LoginState.LoggedIn(user.uid)
                                } else {
                                    _loginState.value = LoginState.Error(firestoreTask.exception?.message ?: "Failed to create user profile")
                                }
                            }
                    } else {
                        _isLoading.value = false
                        _loginState.value = LoginState.Error("User registration failed")
                    }
                } else {
                    _isLoading.value = false
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Registration failed")
                }
            }
    }
    
    fun logout() {
        auth.signOut()
        _loginState.value = LoginState.LoggedOut
    }
    
    fun resetPassword(email: String) {
        if (email.isBlank()) {
            _loginState.value = LoginState.Error("Email cannot be empty")
            return
        }
        
        _isLoading.value = true
        
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginState.value = LoginState.ResetEmailSent
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Failed to send reset email")
                }
            }
    }
    
    sealed class LoginState {
        object LoggedOut : LoginState()
        data class LoggedIn(val userId: String) : LoginState()
        data class Error(val message: String) : LoginState()
        object ResetEmailSent : LoginState()
    }
}