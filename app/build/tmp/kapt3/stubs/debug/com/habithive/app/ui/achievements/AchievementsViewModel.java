package com.habithive.app.ui.achievements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habithive.app.model.Achievement;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u0006H\u0002J&\u0010\u001f\u001a\u00020\u001d2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020!2\u0006\u0010$\u001a\u00020!J\u0006\u0010%\u001a\u00020\u001dJ\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010\'\u001a\u00020\bH\u0002J\u0006\u0010(\u001a\u00020!J\u0006\u0010)\u001a\u00020\u001dJ\u0016\u0010*\u001a\u00020\u001d2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\b0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0019\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0010R\u001d\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010\u00a8\u0006+"}, d2 = {"Lcom/habithive/app/ui/achievements/AchievementsViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_achievements", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/habithive/app/model/Achievement;", "_errorMessage", "", "_isLoading", "", "_newlyUnlockedAchievement", "_unlockedAchievements", "achievements", "Landroidx/lifecycle/LiveData;", "getAchievements", "()Landroidx/lifecycle/LiveData;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "errorMessage", "getErrorMessage", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoading", "newlyUnlockedAchievement", "getNewlyUnlockedAchievement", "unlockedAchievements", "getUnlockedAchievements", "addAchievementPointsToUser", "", "achievement", "checkAchievements", "streakCount", "", "completionCount", "totalPoints", "totalCalories", "clearNewlyUnlockedAchievement", "createDefaultAchievements", "userId", "getTotalAchievementPoints", "loadAchievements", "updateAchievementsInFirestore", "app_debug"})
public final class AchievementsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Achievement>> _achievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> achievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.util.List<com.habithive.app.model.Achievement>> _unlockedAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> unlockedAchievements = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.habithive.app.model.Achievement> _newlyUnlockedAchievement = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<com.habithive.app.model.Achievement> newlyUnlockedAchievement = null;
    
    public AchievementsViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> getAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.util.List<com.habithive.app.model.Achievement>> getUnlockedAchievements() {
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
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.habithive.app.model.Achievement> getNewlyUnlockedAchievement() {
        return null;
    }
    
    public final void loadAchievements() {
    }
    
    private final void createDefaultAchievements(java.lang.String userId) {
    }
    
    public final void checkAchievements(int streakCount, int completionCount, int totalPoints, int totalCalories) {
    }
    
    private final void updateAchievementsInFirestore(java.util.List<com.habithive.app.model.Achievement> achievements) {
    }
    
    private final void addAchievementPointsToUser(com.habithive.app.model.Achievement achievement) {
    }
    
    public final void clearNewlyUnlockedAchievement() {
    }
    
    public final int getTotalAchievementPoints() {
        return 0;
    }
}