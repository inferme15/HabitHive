package com.habithive.app.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.habithive.app.model.Achievement;
import com.habithive.app.model.Goal;
import com.habithive.app.model.Habit;
import com.habithive.app.utils.PointsCalculator;
import java.util.Calendar;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010:\u001a\u00020;2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\f0\u0005H\u0002J\u000e\u0010=\u001a\u00020;2\u0006\u0010>\u001a\u00020\bJ\u000e\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u0014J\b\u0010A\u001a\u00020;H\u0002J\b\u0010B\u001a\u00020;H\u0002J\b\u0010C\u001a\u00020;H\u0002J\b\u0010D\u001a\u00020;H\u0002J\u0006\u0010E\u001a\u00020;J\u000e\u0010F\u001a\u00020;2\u0006\u0010@\u001a\u00020\u0014J\u0018\u0010G\u001a\u00020\u00102\u0006\u0010H\u001a\u00020\f2\u0006\u0010@\u001a\u00020\u0014H\u0002J\u001e\u0010I\u001a\u00020;2\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\f0\u00052\u0006\u0010@\u001a\u00020\u0014H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001eR\u001d\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0017\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u001d\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00050\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001eR\u0017\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00140\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001eR\u0017\u0010.\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010\u001eR\u001d\u00100\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00050\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010\u001eR\u0017\u00102\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b3\u0010\u001eR\u0017\u00104\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b5\u0010\u001eR\u0017\u00106\u001a\b\u0012\u0004\u0012\u00020\n0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b7\u0010\u001eR\u0017\u00108\u001a\b\u0012\u0004\u0012\u00020\b0\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b9\u0010\u001e\u00a8\u0006J"}, d2 = {"Lcom/habithive/app/ui/home/HomeViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_activeGoals", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/habithive/app/model/Goal;", "_errorMessage", "", "_goalsCompletedCount", "", "_habits", "Lcom/habithive/app/model/Habit;", "_habitsForSelectedDate", "Landroidx/lifecycle/MediatorLiveData;", "_isLoading", "", "_recentAchievements", "Lcom/habithive/app/model/Achievement;", "_selectedDate", "Ljava/util/Calendar;", "_streak", "_todaysHabits", "_totalCalories", "_totalPoints", "_userLevel", "_userName", "activeGoals", "Landroidx/lifecycle/LiveData;", "getActiveGoals", "()Landroidx/lifecycle/LiveData;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "errorMessage", "getErrorMessage", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "goalsCompletedCount", "getGoalsCompletedCount", "habitsForSelectedDate", "getHabitsForSelectedDate", "isLoading", "recentAchievements", "getRecentAchievements", "selectedDate", "getSelectedDate", "streak", "getStreak", "todaysHabits", "getTodaysHabits", "totalCalories", "getTotalCalories", "totalPoints", "getTotalPoints", "userLevel", "getUserLevel", "userName", "getUserName", "calculateStreak", "", "habits", "completeHabit", "habitId", "getProgressForDate", "date", "loadGoals", "loadHabits", "loadRecentAchievements", "loadUserData", "refreshData", "setSelectedDate", "shouldShowHabitOnDate", "habit", "updateHabitsForSelectedDate", "app_debug"})
public final class HomeViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _userName = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> userName = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _userLevel = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> userLevel = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _totalPoints = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> totalPoints = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _totalCalories = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> totalCalories = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Habit>> _todaysHabits = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> todaysHabits = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.Calendar> _selectedDate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.Calendar> selectedDate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Habit>> _habits = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MediatorLiveData<java.util.List<com.habithive.app.model.Habit>> _habitsForSelectedDate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> habitsForSelectedDate = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Achievement>> _recentAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> recentAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Goal>> _activeGoals = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Goal>> activeGoals = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _goalsCompletedCount = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> goalsCompletedCount = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _streak = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> streak = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> errorMessage = null;
    
    public HomeViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getUserLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalPoints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getTotalCalories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> getTodaysHabits() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.Calendar> getSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> getHabitsForSelectedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> getRecentAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Goal>> getActiveGoals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getGoalsCompletedCount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getStreak() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getErrorMessage() {
        return null;
    }
    
    private final void updateHabitsForSelectedDate(java.util.List<com.habithive.app.model.Habit> habits, java.util.Calendar date) {
    }
    
    private final boolean shouldShowHabitOnDate(com.habithive.app.model.Habit habit, java.util.Calendar date) {
        return false;
    }
    
    public final void setSelectedDate(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
    }
    
    private final void loadUserData() {
    }
    
    private final void loadHabits() {
    }
    
    private final void calculateStreak(java.util.List<com.habithive.app.model.Habit> habits) {
    }
    
    private final void loadRecentAchievements() {
    }
    
    public final void completeHabit(@org.jetbrains.annotations.NotNull
    java.lang.String habitId) {
    }
    
    public final int getProgressForDate(@org.jetbrains.annotations.NotNull
    java.util.Calendar date) {
        return 0;
    }
    
    private final void loadGoals() {
    }
    
    public final void refreshData() {
    }
}