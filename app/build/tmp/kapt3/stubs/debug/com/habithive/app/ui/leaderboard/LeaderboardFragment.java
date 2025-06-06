package com.habithive.app.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.*;
import com.habithive.app.adapters.LeaderboardAdapter;
import com.habithive.app.databinding.FragmentLeaderboardBinding;
import com.habithive.app.model.LeaderboardUser;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u0016H\u0002J$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u0018H\u0016J\u001a\u0010$\u001a\u00020\u00182\u0006\u0010%\u001a\u00020\u001c2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010&\u001a\u00020\u0018H\u0002J\b\u0010\'\u001a\u00020\u0018H\u0002J\u0012\u0010(\u001a\u00020\u00182\b\u0010)\u001a\u0004\u0018\u00010\u0016H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/habithive/app/ui/leaderboard/LeaderboardFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/habithive/app/databinding/FragmentLeaderboardBinding;", "achievementListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "getBinding", "()Lcom/habithive/app/databinding/FragmentLeaderboardBinding;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "isLoading", "", "leaderboardAdapter", "Lcom/habithive/app/adapters/LeaderboardAdapter;", "leaderboardUsers", "", "Lcom/habithive/app/model/LeaderboardUser;", "selectedType", "", "listenForLeaderboardUpdates", "", "loadLeaderboard", "type", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupRecyclerView", "setupTabs", "showError", "message", "app_debug"})
public final class LeaderboardFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.habithive.app.databinding.FragmentLeaderboardBinding _binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.firestore.FirebaseFirestore firestore;
    private com.habithive.app.adapters.LeaderboardAdapter leaderboardAdapter;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.habithive.app.model.LeaderboardUser> leaderboardUsers = null;
    @org.jetbrains.annotations.Nullable
    private com.google.firebase.firestore.ListenerRegistration achievementListener;
    @org.jetbrains.annotations.NotNull
    private java.lang.String selectedType = "daily";
    private boolean isLoading = false;
    
    public LeaderboardFragment() {
        super();
    }
    
    private final com.habithive.app.databinding.FragmentLeaderboardBinding getBinding() {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupTabs() {
    }
    
    private final void loadLeaderboard(java.lang.String type) {
    }
    
    private final void listenForLeaderboardUpdates() {
    }
    
    private final void showError(java.lang.String message) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}