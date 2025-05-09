package com.habithive.app.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.habithive.app.R;
import com.habithive.app.databinding.ItemLeaderboardBinding;
import com.habithive.app.model.LeaderboardUser;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0013B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/habithive/app/adapters/LeaderboardAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/habithive/app/adapters/LeaderboardAdapter$LeaderboardViewHolder;", "users", "", "Lcom/habithive/app/model/LeaderboardUser;", "currentUserId", "", "(Ljava/util/List;Ljava/lang/String;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "LeaderboardViewHolder", "app_debug"})
public final class LeaderboardAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.habithive.app.adapters.LeaderboardAdapter.LeaderboardViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.habithive.app.model.LeaderboardUser> users = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String currentUserId = null;
    
    public LeaderboardAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<com.habithive.app.model.LeaderboardUser> users, @org.jetbrains.annotations.NotNull
    java.lang.String currentUserId) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.habithive.app.adapters.LeaderboardAdapter.LeaderboardViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.habithive.app.adapters.LeaderboardAdapter.LeaderboardViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/habithive/app/adapters/LeaderboardAdapter$LeaderboardViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/habithive/app/databinding/ItemLeaderboardBinding;", "(Lcom/habithive/app/adapters/LeaderboardAdapter;Lcom/habithive/app/databinding/ItemLeaderboardBinding;)V", "getBinding", "()Lcom/habithive/app/databinding/ItemLeaderboardBinding;", "app_debug"})
    public final class LeaderboardViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull
        private final com.habithive.app.databinding.ItemLeaderboardBinding binding = null;
        
        public LeaderboardViewHolder(@org.jetbrains.annotations.NotNull
        com.habithive.app.databinding.ItemLeaderboardBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.habithive.app.databinding.ItemLeaderboardBinding getBinding() {
            return null;
        }
    }
}