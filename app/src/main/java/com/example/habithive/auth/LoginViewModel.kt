package com.example.habithive.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus> = _loginStatus
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun login(email: String, password: String) {
        _isLoading.value = true
        
        if (email.isEmpty() || password.isEmpty()) {
            _loginStatus.value = LoginStatus.ERROR_EMPTY_FIELDS
            _isLoading.value = false
            return
        }
        
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _loginStatus.value = LoginStatus.SUCCESS
                } else {
                    _loginStatus.value = LoginStatus.ERROR_FAILED_LOGIN
                }
            }
    }
    
    sealed class LoginStatus {
        object SUCCESS : LoginStatus()
        object ERROR_EMPTY_FIELDS : LoginStatus()
        object ERROR_FAILED_LOGIN : LoginStatus()
    }
}