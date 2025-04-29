package com.habithive.app.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class MainViewModel : ViewModel() {
    
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    
    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn
    
    private val _selectedTab = MutableLiveData<Int>()
    val selectedTab: LiveData<Int> = _selectedTab
    
    private val _selectedDate = MutableLiveData<Calendar>()
    val selectedDate: LiveData<Calendar> = _selectedDate
    
    init {
        // Check if user is logged in
        checkAuthState()
        
        // Initialize with today's date
        _selectedDate.value = Calendar.getInstance()
        
        // Start at Home tab (index 0)
        _selectedTab.value = 0
    }
    
    private fun checkAuthState() {
        val currentUser = auth.currentUser
        _isLoggedIn.value = (currentUser != null)
    }
    
    fun setSelectedTab(tabIndex: Int) {
        _selectedTab.value = tabIndex
    }
    
    fun setSelectedDate(date: Calendar) {
        _selectedDate.value = date
    }
    
    fun navigateToToday() {
        _selectedDate.value = Calendar.getInstance()
    }
    
    fun navigateToPreviousDay() {
        val calendar = _selectedDate.value ?: Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1)
        _selectedDate.value = calendar
    }
    
    fun navigateToNextDay() {
        val calendar = _selectedDate.value ?: Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        _selectedDate.value = calendar
    }
    
    fun navigateToPreviousWeek() {
        val calendar = _selectedDate.value ?: Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        _selectedDate.value = calendar
    }
    
    fun navigateToNextWeek() {
        val calendar = _selectedDate.value ?: Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
        _selectedDate.value = calendar
    }
    
    fun isDateToday(date: Calendar): Boolean {
        val today = Calendar.getInstance()
        return (date.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                date.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR))
    }
    
    fun getFormattedDate(date: Calendar): String {
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        val month = date.getDisplayName(Calendar.MONTH, Calendar.SHORT, java.util.Locale.getDefault())
        val year = date.get(Calendar.YEAR)
        return "$dayOfMonth $month $year"
    }
    
    fun getShortDayName(date: Calendar): String {
        return date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, java.util.Locale.getDefault()) ?: ""
    }
}