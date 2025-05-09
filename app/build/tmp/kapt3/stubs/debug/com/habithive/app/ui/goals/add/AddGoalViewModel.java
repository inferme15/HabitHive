package com.habithive.app.ui.goals.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habithive.app.utils.QuotesUtil;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001-B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\"\u001a\u00020\u00142\u0006\u0010#\u001a\u00020\u0014H\u0002JP\u0010$\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010&0%j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010&`\'2&\u0010(\u001a\"\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010&0%j\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010&`\'H\u0002J\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\u0005R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u000fR\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u000fR\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0012R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0012R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0012\u00a8\u0006."}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_currentQuote", "Landroidx/lifecycle/MutableLiveData;", "", "_isLoading", "", "_saveStatus", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "currentQuote", "Landroidx/lifecycle/LiveData;", "getCurrentQuote", "()Landroidx/lifecycle/LiveData;", "description", "getDescription", "()Landroidx/lifecycle/MutableLiveData;", "durationPosition", "", "getDurationPosition", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoading", "isShared", "saveStatus", "getSaveStatus", "targetCalories", "getTargetCalories", "targetPoints", "getTargetPoints", "title", "getTitle", "getDurationInDays", "position", "includeQuoteWithGoal", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "goalData", "saveGoal", "", "updateQuote", "quote", "SaveStatus", "app_debug"})
public final class AddGoalViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> title = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> description = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> targetPoints = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> targetCalories = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Integer> durationPosition = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isShared = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus> _saveStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus> saveStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.String> _currentQuote = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.String> currentQuote = null;
    
    public AddGoalViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getTargetPoints() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.String> getTargetCalories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.Integer> getDurationPosition() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<java.lang.Boolean> isShared() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus> getSaveStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.String> getCurrentQuote() {
        return null;
    }
    
    public final void saveGoal() {
    }
    
    private final int getDurationInDays(int position) {
        return 0;
    }
    
    public final void updateQuote(@org.jetbrains.annotations.NotNull
    java.lang.String quote) {
    }
    
    private final java.util.HashMap<java.lang.String, java.lang.Object> includeQuoteWithGoal(java.util.HashMap<java.lang.String, java.lang.Object> goalData) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "", "()V", "ERROR_EMPTY_TITLE", "ERROR_NOT_AUTHENTICATED", "ERROR_SAVE_FAILED", "SUCCESS", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_EMPTY_TITLE;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_NOT_AUTHENTICATED;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_SAVE_FAILED;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$SUCCESS;", "app_debug"})
    public static abstract class SaveStatus {
        
        private SaveStatus() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_EMPTY_TITLE;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "()V", "app_debug"})
        public static final class ERROR_EMPTY_TITLE extends com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus.ERROR_EMPTY_TITLE INSTANCE = null;
            
            private ERROR_EMPTY_TITLE() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_NOT_AUTHENTICATED;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "()V", "app_debug"})
        public static final class ERROR_NOT_AUTHENTICATED extends com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus.ERROR_NOT_AUTHENTICATED INSTANCE = null;
            
            private ERROR_NOT_AUTHENTICATED() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$ERROR_SAVE_FAILED;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "()V", "app_debug"})
        public static final class ERROR_SAVE_FAILED extends com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus.ERROR_SAVE_FAILED INSTANCE = null;
            
            private ERROR_SAVE_FAILED() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus$SUCCESS;", "Lcom/habithive/app/ui/goals/add/AddGoalViewModel$SaveStatus;", "()V", "app_debug"})
        public static final class SUCCESS extends com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.goals.add.AddGoalViewModel.SaveStatus.SUCCESS INSTANCE = null;
            
            private SUCCESS() {
            }
        }
    }
}