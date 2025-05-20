package com.habithive.app.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.habithive.app.R;
import com.habithive.app.databinding.FragmentHomeBinding;
import com.habithive.app.model.Achievement;
import com.habithive.app.data.model.Exercise;
import com.habithive.app.model.Goal;
import com.habithive.app.utils.QuoteDisplayHelper;
import com.habithive.app.utils.QuotesUtil;
import kotlinx.coroutines.Dispatchers;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\b\u0010\u0016\u001a\u00020\u0010H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0002J\u0018\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0002J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u0012H\u0082@\u00a2\u0006\u0002\u0010\u001dJ\u0012\u0010\u001e\u001a\u00020\u00102\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0018\u0010!\u001a\u00020\u00102\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0016J$\u0010&\u001a\u00020\'2\u0006\u0010$\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010+\u001a\u00020\u0010H\u0016J\u0010\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H\u0016J\u001a\u00100\u001a\u00020\u00102\u0006\u00101\u001a\u00020\'2\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\u0010\u00102\u001a\u00020\u00102\u0006\u00103\u001a\u00020\u0012H\u0002J\b\u00104\u001a\u00020\u0010H\u0002J\b\u00105\u001a\u00020\u0010H\u0002J\b\u00106\u001a\u00020\u0010H\u0002J\b\u00107\u001a\u00020\u0010H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0010\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u00068"}, d2 = {"Lcom/habithive/app/ui/home/HomeFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/habithive/app/databinding/FragmentHomeBinding;", "achievementListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "getBinding", "()Lcom/habithive/app/databinding/FragmentHomeBinding;", "exerciseListener", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "displayFormattedQuote", "", "fullQuote", "", "quoteText", "Landroid/widget/TextView;", "quoteAuthor", "listenForAchievements", "listenForTodayExercises", "loadAndDisplayQuote", "loadUserGoals", "", "Lcom/habithive/app/model/Goal;", "userId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "inflater", "Landroid/view/MenuInflater;", "onCreateView", "Landroid/view/View;", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onDestroyView", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onViewCreated", "view", "resetFirestoreScore", "scoreField", "resetScoresIfNeeded", "setupQuoteWidget", "setupWeeklyLineChart", "shareAppLink", "app_debug"})
public final class HomeFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.habithive.app.databinding.FragmentHomeBinding _binding;
    private com.google.firebase.auth.FirebaseAuth auth;
    private com.google.firebase.firestore.FirebaseFirestore firestore;
    @org.jetbrains.annotations.Nullable
    private com.google.firebase.firestore.ListenerRegistration achievementListener;
    @org.jetbrains.annotations.Nullable
    private com.google.firebase.firestore.ListenerRegistration exerciseListener;
    
    public HomeFragment() {
        super();
    }
    
    private final com.habithive.app.databinding.FragmentHomeBinding getBinding() {
        return null;
    }
    
    @java.lang.Override
    public void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
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
    
    @java.lang.Override
    public void onCreateOptionsMenu(@org.jetbrains.annotations.NotNull
    android.view.Menu menu, @org.jetbrains.annotations.NotNull
    android.view.MenuInflater inflater) {
    }
    
    @java.lang.Override
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull
    android.view.MenuItem item) {
        return false;
    }
    
    private final void shareAppLink() {
    }
    
    private final void setupQuoteWidget() {
    }
    
    private final void loadAndDisplayQuote(android.widget.TextView quoteText, android.widget.TextView quoteAuthor) {
    }
    
    private final java.lang.Object loadUserGoals(java.lang.String userId, kotlin.coroutines.Continuation<? super java.util.List<com.habithive.app.model.Goal>> $completion) {
        return null;
    }
    
    private final void displayFormattedQuote(java.lang.String fullQuote, android.widget.TextView quoteText, android.widget.TextView quoteAuthor) {
    }
    
    private final void resetScoresIfNeeded() {
    }
    
    private final void resetFirestoreScore(java.lang.String scoreField) {
    }
    
    private final void listenForAchievements() {
    }
    
    private final void listenForTodayExercises() {
    }
    
    private final void setupWeeklyLineChart() {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}