package com.habithive.app.ui.habits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habithive.app.model.Habit;
import com.habithive.app.utils.PointsCalculator;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010 \n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001,B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JZ\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001b2\u0006\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u00032\u0006\u0010 \u001a\u00020\u00032\u0006\u0010!\u001a\u00020\u00032\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u001b2\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00030$J\u0016\u0010%\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u001b2\u0006\u0010\'\u001a\u00020\u0003J\u000e\u0010(\u001a\u00020\u00032\u0006\u0010)\u001a\u00020\u0003J\b\u0010*\u001a\u00020\u0019H\u0002J\u0006\u0010+\u001a\u00020\u0019R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00070\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000eR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000e\u00a8\u0006-"}, d2 = {"Lcom/habithive/app/ui/habits/AddHabitViewModel;", "Landroidx/lifecycle/ViewModel;", "calories", "", "(I)V", "_addHabitState", "Landroidx/lifecycle/MutableLiveData;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState;", "_isLoading", "", "_userWeight", "addHabitState", "Landroidx/lifecycle/LiveData;", "getAddHabitState", "()Landroidx/lifecycle/LiveData;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getCalories", "()I", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoading", "userWeight", "getUserWeight", "addHabit", "", "title", "", "description", "type", "frequency", "points", "caloriesBurnedPerCompletion", "goal", "reminderTime", "reminderDays", "", "calculateCaloriesBurned", "activityType", "durationMinutes", "calculatePoints", "caloriesBurned", "loadUserData", "resetState", "AddHabitState", "app_debug"})
public final class AddHabitViewModel extends androidx.lifecycle.ViewModel {
    private final int calories = 0;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState> _addHabitState = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState> addHabitState = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> _userWeight = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Integer> userWeight = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    
    public AddHabitViewModel(int calories) {
        super();
    }
    
    public final int getCalories() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState> getAddHabitState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Integer> getUserWeight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    private final void loadUserData() {
    }
    
    public final int calculateCaloriesBurned(@org.jetbrains.annotations.NotNull
    java.lang.String activityType, int durationMinutes) {
        return 0;
    }
    
    public final int calculatePoints(int caloriesBurned) {
        return 0;
    }
    
    public final void addHabit(@org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String type, int frequency, int points, int caloriesBurnedPerCompletion, int goal, @org.jetbrains.annotations.Nullable
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> reminderDays) {
    }
    
    public final void resetState() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState;", "", "()V", "Error", "Initial", "Success", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Error;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Initial;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Success;", "app_debug"})
    public static abstract class AddHabitState {
        
        private AddHabitState() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Error;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Error extends com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState {
            @org.jetbrains.annotations.NotNull
            private final java.lang.String message = null;
            
            public Error(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState.Error copy(@org.jetbrains.annotations.NotNull
            java.lang.String message) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @java.lang.Override
            @org.jetbrains.annotations.NotNull
            public java.lang.String toString() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Initial;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState;", "()V", "app_debug"})
        public static final class Initial extends com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState.Initial INSTANCE = null;
            
            private Initial() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState$Success;", "Lcom/habithive/app/ui/habits/AddHabitViewModel$AddHabitState;", "()V", "app_debug"})
        public static final class Success extends com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.habits.AddHabitViewModel.AddHabitState.Success INSTANCE = null;
            
            private Success() {
            }
        }
    }
}