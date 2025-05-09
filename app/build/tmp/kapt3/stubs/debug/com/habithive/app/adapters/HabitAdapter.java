package com.habithive.app.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.databinding.ItemHabitBinding;
import com.habithive.app.model.Habit;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0014B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\t2\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/habithive/app/adapters/HabitAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/habithive/app/adapters/HabitAdapter$HabitViewHolder;", "habits", "", "Lcom/habithive/app/model/Habit;", "onHabitCheckedListener", "Lkotlin/Function2;", "", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "HabitViewHolder", "app_debug"})
public final class HabitAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.habithive.app.adapters.HabitAdapter.HabitViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.habithive.app.model.Habit> habits = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function2<com.habithive.app.model.Habit, java.lang.Boolean, kotlin.Unit> onHabitCheckedListener = null;
    
    public HabitAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.habithive.app.model.Habit> habits, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.habithive.app.model.Habit, ? super java.lang.Boolean, kotlin.Unit> onHabitCheckedListener) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.habithive.app.adapters.HabitAdapter.HabitViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.habithive.app.adapters.HabitAdapter.HabitViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/habithive/app/adapters/HabitAdapter$HabitViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/habithive/app/databinding/ItemHabitBinding;", "(Lcom/habithive/app/adapters/HabitAdapter;Lcom/habithive/app/databinding/ItemHabitBinding;)V", "getBinding", "()Lcom/habithive/app/databinding/ItemHabitBinding;", "app_debug"})
    public final class HabitViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.habithive.app.databinding.ItemHabitBinding binding = null;
        
        public HabitViewHolder(@org.jetbrains.annotations.NotNull
        com.habithive.app.databinding.ItemHabitBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.habithive.app.databinding.ItemHabitBinding getBinding() {
            return null;
        }
    }
}