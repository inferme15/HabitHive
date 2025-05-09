package com.habithive.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.R;
import com.habithive.app.model.Goal;
import com.habithive.app.utils.QuoteDisplayHelper;
import java.text.SimpleDateFormat;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0014B-\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\t2\n\u0010\u000e\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000f\u001a\u00020\fH\u0016J\u001c\u0010\u0010\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/habithive/app/adapters/GoalAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/habithive/app/adapters/GoalAdapter$GoalViewHolder;", "goals", "", "Lcom/habithive/app/model/Goal;", "onCompletionChange", "Lkotlin/Function2;", "", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function2;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "GoalViewHolder", "app_debug"})
public final class GoalAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.habithive.app.adapters.GoalAdapter.GoalViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.habithive.app.model.Goal> goals = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function2<com.habithive.app.model.Goal, java.lang.Boolean, kotlin.Unit> onCompletionChange = null;
    
    public GoalAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.habithive.app.model.Goal> goals, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super com.habithive.app.model.Goal, ? super java.lang.Boolean, kotlin.Unit> onCompletionChange) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.habithive.app.adapters.GoalAdapter.GoalViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.habithive.app.adapters.GoalAdapter.GoalViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/habithive/app/adapters/GoalAdapter$GoalViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/habithive/app/adapters/GoalAdapter;Landroid/view/View;)V", "completedCheckbox", "Landroid/widget/CheckBox;", "descriptionTextView", "Landroid/widget/TextView;", "durationTextView", "progressBar", "Landroid/widget/ProgressBar;", "progressTextView", "quoteTextView", "sharedLabel", "targetCaloriesTextView", "targetPointsTextView", "titleTextView", "bind", "", "goal", "Lcom/habithive/app/model/Goal;", "app_debug"})
    public final class GoalViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView titleTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView descriptionTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView durationTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ProgressBar progressBar = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView progressTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView targetPointsTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView targetCaloriesTextView = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.CheckBox completedCheckbox = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView sharedLabel = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView quoteTextView = null;
        
        public GoalViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View itemView) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        com.habithive.app.model.Goal goal) {
        }
    }
}