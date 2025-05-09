package com.habithive.app.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Calendar;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\u000e\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001a\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\u001b\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u0007J\u0006\u0010\u001c\u001a\u00020\u0016J\u0006\u0010\u001d\u001a\u00020\u0016J\u0006\u0010\u001e\u001a\u00020\u0016J\u0006\u0010\u001f\u001a\u00020\u0016J\u0006\u0010 \u001a\u00020\u0016J\u000e\u0010!\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0007J\u000e\u0010\"\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0010R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010\u00a8\u0006$"}, d2 = {"Lcom/habithive/app/ui/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_isLoggedIn", "Landroidx/lifecycle/MutableLiveData;", "", "_selectedDate", "Ljava/util/Calendar;", "_selectedTab", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoggedIn", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "selectedDate", "getSelectedDate", "selectedTab", "getSelectedTab", "checkAuthState", "", "getFormattedDate", "", "date", "getShortDayName", "isDateToday", "navigateToNextDay", "navigateToNextWeek", "navigateToPreviousDay", "navigateToPreviousWeek", "navigateToToday", "setSelectedDate", "setSelectedTab", "tabIndex", "app_debug"})
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoggedIn = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoggedIn = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _selectedTab = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> selectedTab = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.Calendar> _selectedDate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.Calendar> selectedDate = null;
    
    public MainViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoggedIn() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getSelectedTab() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.Calendar> getSelectedDate() {
        return null;
    }
    
    private final void checkAuthState() {
    }
    
    public final void setSelectedTab(int tabIndex) {
    }
    
    public final void setSelectedDate(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
    }
    
    public final void navigateToToday() {
    }
    
    public final void navigateToPreviousDay() {
    }
    
    public final void navigateToNextDay() {
    }
    
    public final void navigateToPreviousWeek() {
    }
    
    public final void navigateToNextWeek() {
    }
    
    public final boolean isDateToday(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getFormattedDate(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getShortDayName(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
        return null;
    }
}