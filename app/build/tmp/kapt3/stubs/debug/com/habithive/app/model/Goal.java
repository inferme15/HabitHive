package com.habithive.app.model;

import com.google.firebase.Timestamp;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b1\b\u0086\b\u0018\u00002\u00020\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0002B\u009d\u0001\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0004\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\b\b\u0002\u0010\u0015\u001a\u00020\r\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0017J\u0006\u0010,\u001a\u00020\u0011J\t\u0010-\u001a\u00020\u0004H\u00c6\u0003J\t\u0010.\u001a\u00020\tH\u00c6\u0003J\t\u0010/\u001a\u00020\u0011H\u00c6\u0003J\t\u00100\u001a\u00020\u0013H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\t\u00102\u001a\u00020\rH\u00c6\u0003J\t\u00103\u001a\u00020\u0004H\u00c6\u0003J\t\u00104\u001a\u00020\u0004H\u00c6\u0003J\t\u00105\u001a\u00020\u0004H\u00c6\u0003J\t\u00106\u001a\u00020\u0004H\u00c6\u0003J\t\u00107\u001a\u00020\tH\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\tH\u00c6\u0003J\t\u0010:\u001a\u00020\rH\u00c6\u0003J\t\u0010;\u001a\u00020\tH\u00c6\u0003J\u00a1\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u00042\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\t2\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00132\b\b\u0002\u0010\u0015\u001a\u00020\r2\b\b\u0002\u0010\u0016\u001a\u00020\u0004H\u00c6\u0001J\u0013\u0010=\u001a\u00020\r2\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010?\u001a\u00020\tJ\u0006\u0010@\u001a\u00020\tJ\t\u0010A\u001a\u00020\tH\u00d6\u0001J\u0006\u0010B\u001a\u00020\rJ\t\u0010C\u001a\u00020\u0004H\u00d6\u0001R\u0011\u0010\u0015\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001eR\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010!R\u0011\u0010\u0016\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0011\u0010\u0006\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010!\u00a8\u0006D"}, d2 = {"Lcom/habithive/app/model/Goal;", "", "()V", "id", "", "userId", "title", "description", "targetPoints", "", "targetCalories", "duration", "shared", "", "currentPoints", "currentCalories", "progress", "", "createdAt", "Lcom/google/firebase/Timestamp;", "completedAt", "completed", "inspirationalQuote", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIIZIIDLcom/google/firebase/Timestamp;Lcom/google/firebase/Timestamp;ZLjava/lang/String;)V", "getCompleted", "()Z", "getCompletedAt", "()Lcom/google/firebase/Timestamp;", "getCreatedAt", "getCurrentCalories", "()I", "getCurrentPoints", "getDescription", "()Ljava/lang/String;", "getDuration", "getId", "getInspirationalQuote", "getProgress", "()D", "getShared", "getTargetCalories", "getTargetPoints", "getTitle", "getUserId", "calculateProgress", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "getProgressPercentage", "getRemainingDays", "hashCode", "isExpired", "toString", "app_debug"})
public final class Goal {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String userId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    private final int targetPoints = 0;
    private final int targetCalories = 0;
    private final int duration = 0;
    private final boolean shared = false;
    private final int currentPoints = 0;
    private final int currentCalories = 0;
    private final double progress = 0.0;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.Timestamp createdAt = null;
    @org.jetbrains.annotations.Nullable
    private final com.google.firebase.Timestamp completedAt = null;
    private final boolean completed = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String inspirationalQuote = null;
    
    public Goal(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, int targetPoints, int targetCalories, int duration, boolean shared, int currentPoints, int currentCalories, double progress, @org.jetbrains.annotations.NotNull
    com.google.firebase.Timestamp createdAt, @org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp completedAt, boolean completed, @org.jetbrains.annotations.NotNull
    java.lang.String inspirationalQuote) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getUserId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final int getTargetPoints() {
        return 0;
    }
    
    public final int getTargetCalories() {
        return 0;
    }
    
    public final int getDuration() {
        return 0;
    }
    
    public final boolean getShared() {
        return false;
    }
    
    public final int getCurrentPoints() {
        return 0;
    }
    
    public final int getCurrentCalories() {
        return 0;
    }
    
    public final double getProgress() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.google.firebase.Timestamp getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.Timestamp getCompletedAt() {
        return null;
    }
    
    public final boolean getCompleted() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getInspirationalQuote() {
        return null;
    }
    
    public Goal() {
        super();
    }
    
    public final double calculateProgress() {
        return 0.0;
    }
    
    public final int getRemainingDays() {
        return 0;
    }
    
    public final boolean isExpired() {
        return false;
    }
    
    public final int getProgressPercentage() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final double component11() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.google.firebase.Timestamp component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.google.firebase.Timestamp component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.habithive.app.model.Goal copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, int targetPoints, int targetCalories, int duration, boolean shared, int currentPoints, int currentCalories, double progress, @org.jetbrains.annotations.NotNull
    com.google.firebase.Timestamp createdAt, @org.jetbrains.annotations.Nullable
    com.google.firebase.Timestamp completedAt, boolean completed, @org.jetbrains.annotations.NotNull
    java.lang.String inspirationalQuote) {
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