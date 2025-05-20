package com.habithive.app.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.habithive.app.R;
import com.habithive.app.data.model.Exercise;
import com.habithive.app.databinding.ActivityExerciseHistoryBinding;
import com.habithive.app.ui.auth.LoginActivity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u0012\u0010\u0015\u001a\u00020\u00102\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0010H\u0014J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0010H\u0002J\b\u0010\u001c\u001a\u00020\u0010H\u0002J \u0010\u001d\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/habithive/app/ui/exercise/ExerciseHistoryActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "binding", "Lcom/habithive/app/databinding/ActivityExerciseHistoryBinding;", "exerciseListener", "Lcom/google/firebase/firestore/ListenerRegistration;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "viewModel", "Lcom/habithive/app/ui/exercise/ExerciseHistoryViewModel;", "confirmDelete", "", "exercise", "Lcom/habithive/app/data/model/Exercise;", "deleteExercise", "listenForExerciseChanges", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onSupportNavigateUp", "", "setupObservers", "setupRecyclerView", "updateAchievementsAfterDelete", "userId", "", "caloriesToSubtract", "", "pointsToSubtract", "app_debug"})
public final class ExerciseHistoryActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.habithive.app.databinding.ActivityExerciseHistoryBinding binding;
    private com.habithive.app.ui.exercise.ExerciseHistoryViewModel viewModel;
    private com.habithive.app.ui.exercise.ExerciseHistoryAdapter adapter;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.Nullable
    private com.google.firebase.firestore.ListenerRegistration exerciseListener;
    
    public ExerciseHistoryActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final void setupObservers() {
    }
    
    private final void confirmDelete(com.habithive.app.data.model.Exercise exercise) {
    }
    
    private final void deleteExercise(com.habithive.app.data.model.Exercise exercise) {
    }
    
    private final void updateAchievementsAfterDelete(java.lang.String userId, int caloriesToSubtract, int pointsToSubtract) {
    }
    
    private final void listenForExerciseChanges() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}