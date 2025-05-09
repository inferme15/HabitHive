package com.habithive.app.utils;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.habithive.app.data.model.Exercise;
import java.util.Date;

/**
 * Constants for user document field names
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"Lcom/habithive/app/utils/UserFields;", "", "()V", "CREATED_AT", "", "EMAIL", "GENDER", "HEALTH", "HEIGHT", "NAME", "PHOTO_URL", "POINTS", "UPDATED_AT", "WEIGHT", "app_debug"})
public final class UserFields {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String POINTS = "points";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String UPDATED_AT = "updatedAt";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String NAME = "name";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String EMAIL = "email";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String PHOTO_URL = "photoUrl";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String GENDER = "gender";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String HEIGHT = "height";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String WEIGHT = "weight";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String HEALTH = "health";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String CREATED_AT = "createdAt";
    @org.jetbrains.annotations.NotNull
    public static final com.habithive.app.utils.UserFields INSTANCE = null;
    
    private UserFields() {
        super();
    }
}