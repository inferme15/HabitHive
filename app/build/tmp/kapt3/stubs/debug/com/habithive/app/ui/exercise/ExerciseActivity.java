package com.habithive.app.ui.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.habithive.app.R;
import com.habithive.app.data.model.Exercise;
import com.habithive.app.databinding.ActivityExerciseBinding;
import com.habithive.app.ui.auth.LoginActivity;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\u0012\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0006H\u0016J\b\u0010\u0010\u001a\u00020\nH\u0002J\b\u0010\u0011\u001a\u00020\nH\u0002J\b\u0010\u0012\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/habithive/app/ui/exercise/ExerciseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/habithive/app/databinding/ActivityExerciseBinding;", "isSaving", "", "viewModel", "Lcom/habithive/app/ui/exercise/ExerciseViewModel;", "calculateCaloriesAndPoints", "", "observeViewModel", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "saveExercise", "setupListeners", "setupSpinner", "app_debug"})
public final class ExerciseActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.habithive.app.databinding.ActivityExerciseBinding binding;
    private com.habithive.app.ui.exercise.ExerciseViewModel viewModel;
    private boolean isSaving = false;
    
    public ExerciseActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupSpinner() {
    }
    
    private final void setupListeners() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void calculateCaloriesAndPoints() {
    }
    
    private final void saveExercise() {
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}