package com.habithive.app.ui.exercise;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.R;
import com.habithive.app.databinding.ItemExerciseHistoryBinding;
import com.habithive.app.data.model.Exercise;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Adapter for displaying exercise history items in a RecyclerView
 * Supports long-press deletion
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u0002\u0012\b\u0012\u00060\u0003R\u00020\u00000\u0001:\u0002\u0012\u0013B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\n\u001a\u00020\u00062\n\u0010\u000b\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\f\u001a\u00020\rH\u0016J\u001c\u0010\u000e\u001a\u00060\u0003R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\rH\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter;", "Landroidx/recyclerview/widget/ListAdapter;", "Lcom/habithive/app/data/model/Exercise;", "Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter$ExerciseViewHolder;", "onDeleteExercise", "Lkotlin/Function1;", "", "(Lkotlin/jvm/functions/Function1;)V", "dateFormat", "Ljava/text/SimpleDateFormat;", "onBindViewHolder", "holder", "position", "", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ExerciseDiffCallback", "ExerciseViewHolder", "app_debug"})
public final class ExerciseHistoryAdapter extends androidx.recyclerview.widget.ListAdapter<com.habithive.app.data.model.Exercise, com.habithive.app.ui.exercise.ExerciseHistoryAdapter.ExerciseViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.habithive.app.data.model.Exercise, kotlin.Unit> onDeleteExercise = null;
    @org.jetbrains.annotations.NotNull
    private final java.text.SimpleDateFormat dateFormat = null;
    
    public ExerciseHistoryAdapter(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.habithive.app.data.model.Exercise, kotlin.Unit> onDeleteExercise) {
        super(null);
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.habithive.app.ui.exercise.ExerciseHistoryAdapter.ExerciseViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.habithive.app.ui.exercise.ExerciseHistoryAdapter.ExerciseViewHolder holder, int position) {
    }
    
    /**
     * DiffCallback to efficiently update RecyclerView items
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0002H\u0016\u00a8\u0006\t"}, d2 = {"Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter$ExerciseDiffCallback;", "Landroidx/recyclerview/widget/DiffUtil$ItemCallback;", "Lcom/habithive/app/data/model/Exercise;", "()V", "areContentsTheSame", "", "oldItem", "newItem", "areItemsTheSame", "app_debug"})
    static final class ExerciseDiffCallback extends androidx.recyclerview.widget.DiffUtil.ItemCallback<com.habithive.app.data.model.Exercise> {
        
        public ExerciseDiffCallback() {
            super();
        }
        
        @java.lang.Override
        public boolean areItemsTheSame(@org.jetbrains.annotations.NotNull
        com.habithive.app.data.model.Exercise oldItem, @org.jetbrains.annotations.NotNull
        com.habithive.app.data.model.Exercise newItem) {
            return false;
        }
        
        @java.lang.Override
        public boolean areContentsTheSame(@org.jetbrains.annotations.NotNull
        com.habithive.app.data.model.Exercise oldItem, @org.jetbrains.annotations.NotNull
        com.habithive.app.data.model.Exercise newItem) {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter$ExerciseViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/habithive/app/databinding/ItemExerciseHistoryBinding;", "(Lcom/habithive/app/ui/exercise/ExerciseHistoryAdapter;Lcom/habithive/app/databinding/ItemExerciseHistoryBinding;)V", "bind", "", "exercise", "Lcom/habithive/app/data/model/Exercise;", "app_debug"})
    public final class ExerciseViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.habithive.app.databinding.ItemExerciseHistoryBinding binding = null;
        
        public ExerciseViewHolder(@org.jetbrains.annotations.NotNull
        com.habithive.app.databinding.ItemExerciseHistoryBinding binding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.habithive.app.data.model.Exercise exercise) {
        }
    }
}