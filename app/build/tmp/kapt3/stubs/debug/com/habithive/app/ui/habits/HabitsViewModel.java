package com.habithive.app.ui.habits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.habithive.app.model.Habit;
import com.habithive.app.utils.PointsCalculator;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002JZ\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001c2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0007J\u000e\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0005J\u000e\u0010$\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0005J\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001a\u001a\u00020\u0005J\u0014\u0010&\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\'\u001a\u00020(J\u0006\u0010)\u001a\u00020\u001cJ\u0006\u0010*\u001a\u00020\u001cJ\u0006\u0010+\u001a\u00020\u0017Jb\u0010,\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u001c2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010\u00a8\u0006-"}, d2 = {"Lcom/habithive/app/ui/habits/HabitsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_errorMessage", "Landroidx/lifecycle/MutableLiveData;", "", "_habits", "", "Lcom/habithive/app/model/Habit;", "_isLoading", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "errorMessage", "Landroidx/lifecycle/LiveData;", "getErrorMessage", "()Landroidx/lifecycle/LiveData;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "habits", "getHabits", "isLoading", "addHabit", "", "title", "description", "type", "frequency", "", "points", "caloriesBurnedPerCompletion", "goal", "reminderTime", "reminderDays", "completeHabit", "habitId", "deleteHabit", "getHabitsByType", "getHabitsForDate", "date", "Ljava/util/Date;", "getTotalCaloriesBurned", "getTotalPoints", "loadHabits", "updateHabit", "app_debug"})
public final class HabitsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Habit>> _habits = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> habits = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> errorMessage = null;
    
    public HabitsViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Habit>> getHabits() {
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
    
    public final void loadHabits() {
    }
    
    public final void addHabit(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String type, int frequency, int points, int caloriesBurnedPerCompletion, int goal, @org.jetbrains.annotations.Nullable
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> reminderDays) {
    }
    
    public final void deleteHabit(@org.jetbrains.annotations.NotNull
    java.lang.String habitId) {
    }
    
    public final void updateHabit(@org.jetbrains.annotations.NotNull
    java.lang.String habitId, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String type, int frequency, int points, int caloriesBurnedPerCompletion, int goal, @org.jetbrains.annotations.Nullable
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> reminderDays) {
    }
    
    public final void completeHabit(@org.jetbrains.annotations.NotNull
    java.lang.String habitId) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.habithive.app.model.Habit> getHabitsForDate(@org.jetbrains.annotations.NotNull
    java.util.Date date) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.habithive.app.model.Habit> getHabitsByType(@org.jetbrains.annotations.NotNull
    java.lang.String type) {
        return null;
    }
    
    public final int getTotalPoints() {
        return 0;
    }
    
    public final int getTotalCaloriesBurned() {
        return 0;
    }
}