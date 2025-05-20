package com.habithive.app.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010#\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0002J\b\u0010$\u001a\u00020%H\u0002J\u0006\u0010&\u001a\u00020%J\u0006\u0010\'\u001a\u00020%J\u000e\u0010(\u001a\u00020%2\u0006\u0010)\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\tR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\tR\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\tR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\tR\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001cR\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\tR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\tR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\t\u00a8\u0006*"}, d2 = {"Lcom/habithive/app/ui/profile/ProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_isLoading", "Landroidx/lifecycle/MutableLiveData;", "", "age", "", "getAge", "()Landroidx/lifecycle/MutableLiveData;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "bmi", "getBmi", "dateOfBirth", "getDateOfBirth", "email", "getEmail", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "gender", "getGender", "health", "getHealth", "height", "getHeight", "isLoading", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "shareGoal", "getShareGoal", "userName", "getUserName", "weight", "getWeight", "calculateAge", "calculateAndSetBmi", "", "loadUserData", "logout", "updateShareGoalPreference", "share", "app_debug"})
public final class ProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> userName = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> email = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> gender = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> health = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> height = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> weight = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> dateOfBirth = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> age = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> bmi = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> shareGoal = null;
    
    public ProfileViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getUserName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getGender() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getHealth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getHeight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getWeight() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getDateOfBirth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getAge() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getBmi() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> getShareGoal() {
        return null;
    }
    
    public final void loadUserData() {
    }
    
    public final void updateShareGoalPreference(boolean share) {
    }
    
    private final java.lang.String calculateAge(java.lang.String dateOfBirth) {
        return null;
    }
    
    private final void calculateAndSetBmi() {
    }
    
    public final void logout() {
    }
}