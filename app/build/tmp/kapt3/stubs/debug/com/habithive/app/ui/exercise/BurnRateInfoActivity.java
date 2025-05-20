package com.habithive.app.ui.exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.R;
import com.habithive.app.databinding.ActivityBurnRateInfoBinding;

/**
 * Activity to display information about exercise burn rates
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0002\u000f\u0010B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0002J\u0012\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0014J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/habithive/app/ui/exercise/BurnRateInfoActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/habithive/app/databinding/ActivityBurnRateInfoBinding;", "createBurnRatesList", "", "Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateInfo;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "setupRecyclerView", "BurnRateAdapter", "BurnRateInfo", "app_debug"})
public final class BurnRateInfoActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.habithive.app.databinding.ActivityBurnRateInfoBinding binding;
    
    public BurnRateInfoActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupRecyclerView() {
    }
    
    private final java.util.List<com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateInfo> createBurnRatesList() {
        return null;
    }
    
    @java.lang.Override
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    /**
     * Adapter for the burn rate RecyclerView
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u0010\u0012\f\u0012\n0\u0002R\u00060\u0000R\u00020\u00030\u0001:\u0001\u0012B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J \u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\r\u001a\u00020\tH\u0016J \u0010\u000e\u001a\n0\u0002R\u00060\u0000R\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\tH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateAdapter$BurnRateViewHolder;", "Lcom/habithive/app/ui/exercise/BurnRateInfoActivity;", "burnRates", "", "Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateInfo;", "(Lcom/habithive/app/ui/exercise/BurnRateInfoActivity;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "BurnRateViewHolder", "app_debug"})
    public final class BurnRateAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateAdapter.BurnRateViewHolder> {
        @org.jetbrains.annotations.NotNull
        private final java.util.List<com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateInfo> burnRates = null;
        
        public BurnRateAdapter(@org.jetbrains.annotations.NotNull
        java.util.List<com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateInfo> burnRates) {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateAdapter.BurnRateViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
        android.view.ViewGroup parent, int viewType) {
            return null;
        }
        
        @java.lang.Override
        public void onBindViewHolder(@org.jetbrains.annotations.NotNull
        com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateAdapter.BurnRateViewHolder holder, int position) {
        }
        
        @java.lang.Override
        public int getItemCount() {
            return 0;
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateAdapter$BurnRateViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateAdapter;Landroid/view/View;)V", "textBurnRate", "Landroid/widget/TextView;", "textExerciseType", "textIntensity", "bind", "", "burnRateInfo", "Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateInfo;", "app_debug"})
        public final class BurnRateViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView textExerciseType = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView textBurnRate = null;
            @org.jetbrains.annotations.NotNull
            private final android.widget.TextView textIntensity = null;
            
            public BurnRateViewHolder(@org.jetbrains.annotations.NotNull
            android.view.View itemView) {
                super(null);
            }
            
            public final void bind(@org.jetbrains.annotations.NotNull
            com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateInfo burnRateInfo) {
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/habithive/app/ui/exercise/BurnRateInfoActivity$BurnRateInfo;", "", "exerciseType", "", "burnRate", "", "intensityLevel", "(Ljava/lang/String;ILjava/lang/String;)V", "getBurnRate", "()I", "getExerciseType", "()Ljava/lang/String;", "getIntensityLevel", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
    public static final class BurnRateInfo {
        @org.jetbrains.annotations.NotNull
        private final java.lang.String exerciseType = null;
        private final int burnRate = 0;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String intensityLevel = null;
        
        public BurnRateInfo(@org.jetbrains.annotations.NotNull
        java.lang.String exerciseType, int burnRate, @org.jetbrains.annotations.NotNull
        java.lang.String intensityLevel) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getExerciseType() {
            return null;
        }
        
        public final int getBurnRate() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getIntensityLevel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.habithive.app.ui.exercise.BurnRateInfoActivity.BurnRateInfo copy(@org.jetbrains.annotations.NotNull
        java.lang.String exerciseType, int burnRate, @org.jetbrains.annotations.NotNull
        java.lang.String intensityLevel) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public java.lang.String toString() {
            return null;
        }
    }
}