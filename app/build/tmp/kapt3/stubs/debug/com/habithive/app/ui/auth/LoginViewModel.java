package com.habithive.app.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0016B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000eR\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0017"}, d2 = {"Lcom/habithive/app/ui/auth/LoginViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_isLoading", "Landroidx/lifecycle/MutableLiveData;", "", "_loginStatus", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoading", "Landroidx/lifecycle/LiveData;", "()Landroidx/lifecycle/LiveData;", "loginStatus", "getLoginStatus", "login", "", "email", "", "password", "LoginStatus", "app_debug"})
public final class LoginViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<com.habithive.app.ui.auth.LoginViewModel.LoginStatus> _loginStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<com.habithive.app.ui.auth.LoginViewModel.LoginStatus> loginStatus = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.MutableLiveData<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull
    private final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading = null;
    
    public LoginViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<com.habithive.app.ui.auth.LoginViewModel.LoginStatus> getLoginStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<java.lang.Boolean> isLoading() {
        return null;
    }
    
    public final void login(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus;", "", "()V", "ERROR_EMPTY_FIELDS", "ERROR_FAILED_LOGIN", "SUCCESS", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$ERROR_EMPTY_FIELDS;", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$ERROR_FAILED_LOGIN;", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$SUCCESS;", "app_debug"})
    public static abstract class LoginStatus {
        
        private LoginStatus() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$ERROR_EMPTY_FIELDS;", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus;", "()V", "app_debug"})
        public static final class ERROR_EMPTY_FIELDS extends com.habithive.app.ui.auth.LoginViewModel.LoginStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.auth.LoginViewModel.LoginStatus.ERROR_EMPTY_FIELDS INSTANCE = null;
            
            private ERROR_EMPTY_FIELDS() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$ERROR_FAILED_LOGIN;", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus;", "()V", "app_debug"})
        public static final class ERROR_FAILED_LOGIN extends com.habithive.app.ui.auth.LoginViewModel.LoginStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.auth.LoginViewModel.LoginStatus.ERROR_FAILED_LOGIN INSTANCE = null;
            
            private ERROR_FAILED_LOGIN() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus$SUCCESS;", "Lcom/habithive/app/ui/auth/LoginViewModel$LoginStatus;", "()V", "app_debug"})
        public static final class SUCCESS extends com.habithive.app.ui.auth.LoginViewModel.LoginStatus {
            @org.jetbrains.annotations.NotNull
            public static final com.habithive.app.ui.auth.LoginViewModel.LoginStatus.SUCCESS INSTANCE = null;
            
            private SUCCESS() {
            }
        }
    }
}