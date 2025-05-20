package com.habithive.app.adapters;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.R;
import com.habithive.app.data.model.Exercise;
import com.habithive.app.databinding.ItemHabitBinding;
import java.text.SimpleDateFormat;
import java.util.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001bB\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u001c\u0010\u0010\u001a\u00020\b2\n\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u001c\u0010\u0013\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0018\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/habithive/app/adapters/HabitAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/habithive/app/adapters/HabitAdapter$ExerciseViewHolder;", "exercises", "", "Lcom/habithive/app/data/model/Exercise;", "onExerciseDelete", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "formatDate", "", "date", "Ljava/util/Date;", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "showPopupMenu", "view", "Landroid/view/View;", "exercise", "ExerciseViewHolder", "app_debug"})
public final class HabitAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.habithive.app.adapters.HabitAdapter.ExerciseViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.habithive.app.data.model.Exercise> exercises = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function1<com.habithive.app.data.model.Exercise, kotlin.Unit> onExerciseDelete = null;
    
    public HabitAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.habithive.app.data.model.Exercise> exercises, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.habithive.app.data.model.Exercise, kotlin.Unit> onExerciseDelete) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.habithive.app.adapters.HabitAdapter.ExerciseViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.habithive.app.adapters.HabitAdapter.ExerciseViewHolder holder, int position) {
    }
    
    private final java.lang.String formatDate(java.util.Date date) {
        return null;
    }
    
    private final void showPopupMenu(android.view.View view, com.habithive.app.data.model.Exercise exercise) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/habithive/app/adapters/HabitAdapter$ExerciseViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/habithive/app/databinding/ItemHabitBinding;", "(Lcom/habithive/app/adapters/HabitAdapter;Lcom/habithive/app/databinding/ItemHabitBinding;)V", "getBinding", "()Lcom/habithive/app/databinding/ItemHabitBinding;", "app_debug"})
    public final class ExerciseViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.habithive.app.databinding.ItemHabitBinding binding = null;
        
        public ExerciseViewHolder(@org.jetbrains.annotations.NotNull
        com.habithive.app.databinding.ItemHabitBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.habithive.app.databinding.ItemHabitBinding getBinding() {
            return null;
        }
    }
}