package com.habithive.app.model;

import com.google.firebase.Timestamp;
import java.util.Calendar;
import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b2\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\b\u0018\u0000 J2\u00020\u0001:\u0001JB\u00a9\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\t\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\t\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u0012\b\b\u0002\u0010\u0015\u001a\u00020\t\u00a2\u0006\u0002\u0010\u0016J\u0006\u0010+\u001a\u00020\tJ\u0006\u0010,\u001a\u00020\tJ\u0006\u0010-\u001a\u00020\tJ\u0006\u0010.\u001a\u00020\tJ\u0006\u0010/\u001a\u00020\tJ\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u00c6\u0003J\t\u00102\u001a\u00020\tH\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u00104\u001a\b\u0012\u0004\u0012\u00020\t0\u000fH\u00c6\u0003J\t\u00105\u001a\u00020\u0014H\u00c6\u0003J\t\u00106\u001a\u00020\tH\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\tH\u00c6\u0003J\t\u0010<\u001a\u00020\tH\u00c6\u0003J\t\u0010=\u001a\u00020\tH\u00c6\u0003J\t\u0010>\u001a\u00020\rH\u00c6\u0003J\u00ad\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\r2\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\t2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\tH\u00c6\u0001J\u0013\u0010@\u001a\u00020\u00142\b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010B\u001a\u00020\u0003J\u0006\u0010C\u001a\u00020\tJ\t\u0010D\u001a\u00020\tH\u00d6\u0001J\u000e\u0010E\u001a\u00020\u00142\u0006\u0010F\u001a\u00020GJ\u0006\u0010H\u001a\u00020\u0014J\t\u0010I\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0015\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0018R\u0011\u0010\u0010\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010!R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010!R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010!R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010!R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!\u00a8\u0006K"}, d2 = {"Lcom/habithive/app/model/Habit;", "", "id", "", "userId", "title", "description", "type", "frequency", "", "points", "caloriesBurnedPerCompletion", "createdAt", "Lcom/google/firebase/Timestamp;", "completions", "", "goal", "reminderTime", "reminderDays", "completed", "", "caloriesBurned", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;IIILcom/google/firebase/Timestamp;Ljava/util/List;ILjava/lang/String;Ljava/util/List;ZI)V", "getCaloriesBurned", "()I", "getCaloriesBurnedPerCompletion", "getCompleted", "()Z", "getCompletions", "()Ljava/util/List;", "getCreatedAt", "()Lcom/google/firebase/Timestamp;", "getDescription", "()Ljava/lang/String;", "getFrequency", "getGoal", "getId", "getPoints", "getReminderDays", "getReminderTime", "getTitle", "getType", "getUserId", "calculateCaloriesBurned", "calculateCurrentStreak", "calculateTotalPoints", "completionsThisMonth", "completionsThisWeek", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "formatNextReminder", "getCompletionPercentage", "hashCode", "isCompletedOnDate", "date", "Ljava/util/Date;", "isCompletedToday", "toString", "Companion", "app_debug"})
public final class Habit {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String userId = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String description = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String type = null;
    private final int frequency = 0;
    private final int points = 0;
    private final int caloriesBurnedPerCompletion = 0;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.Timestamp createdAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.google.firebase.Timestamp> completions = null;
    private final int goal = 0;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String reminderTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.Integer> reminderDays = null;
    private final boolean completed = false;
    private final int caloriesBurned = 0;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_EXERCISE = "exercise";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_NUTRITION = "nutrition";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_MINDFULNESS = "mindfulness";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_SLEEP = "sleep";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_WATER = "water";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String TYPE_CUSTOM = "custom";
    public static final int FREQUENCY_DAILY = 1;
    public static final int FREQUENCY_WEEKLY = 2;
    public static final int FREQUENCY_MONTHLY = 3;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXERCISE_RUNNING = "running";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXERCISE_CYCLING = "cycling";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXERCISE_WEIGHTLIFTING = "weightlifting";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EXERCISE_OTHER = "other";
    @org.jetbrains.annotations.NotNull
    public static final com.habithive.app.model.Habit.Companion Companion = null;
    
    public Habit(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String type, int frequency, int points, int caloriesBurnedPerCompletion, @org.jetbrains.annotations.NotNull
    com.google.firebase.Timestamp createdAt, @org.jetbrains.annotations.NotNull
    java.util.List<com.google.firebase.Timestamp> completions, int goal, @org.jetbrains.annotations.Nullable
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> reminderDays, boolean completed, int caloriesBurned) {
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getType() {
        return null;
    }
    
    public final int getFrequency() {
        return 0;
    }
    
    public final int getPoints() {
        return 0;
    }
    
    public final int getCaloriesBurnedPerCompletion() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.google.firebase.Timestamp getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.google.firebase.Timestamp> getCompletions() {
        return null;
    }
    
    public final int getGoal() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getReminderTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Integer> getReminderDays() {
        return null;
    }
    
    public final boolean getCompleted() {
        return false;
    }
    
    public final int getCaloriesBurned() {
        return 0;
    }
    
    public final boolean isCompletedToday() {
        return false;
    }
    
    public final boolean isCompletedOnDate(@org.jetbrains.annotations.NotNull
    java.util.Date date) {
        return false;
    }
    
    public final int completionsThisWeek() {
        return 0;
    }
    
    public final int completionsThisMonth() {
        return 0;
    }
    
    public final int calculateCurrentStreak() {
        return 0;
    }
    
    public final int getCompletionPercentage() {
        return 0;
    }
    
    public final int calculateCaloriesBurned() {
        return 0;
    }
    
    public final int calculateTotalPoints() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String formatNextReminder() {
        return null;
    }
    
    public Habit() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.google.firebase.Timestamp> component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Integer> component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    public final int component15() {
        return 0;
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final int component8() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.google.firebase.Timestamp component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.habithive.app.model.Habit copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String userId, @org.jetbrains.annotations.NotNull
    java.lang.String title, @org.jetbrains.annotations.NotNull
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.lang.String type, int frequency, int points, int caloriesBurnedPerCompletion, @org.jetbrains.annotations.NotNull
    com.google.firebase.Timestamp createdAt, @org.jetbrains.annotations.NotNull
    java.util.List<com.google.firebase.Timestamp> completions, int goal, @org.jetbrains.annotations.Nullable
    java.lang.String reminderTime, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.Integer> reminderDays, boolean completed, int caloriesBurned) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/habithive/app/model/Habit$Companion;", "", "()V", "EXERCISE_CYCLING", "", "EXERCISE_OTHER", "EXERCISE_RUNNING", "EXERCISE_WEIGHTLIFTING", "FREQUENCY_DAILY", "", "FREQUENCY_MONTHLY", "FREQUENCY_WEEKLY", "TYPE_CUSTOM", "TYPE_EXERCISE", "TYPE_MINDFULNESS", "TYPE_NUTRITION", "TYPE_SLEEP", "TYPE_WATER", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}