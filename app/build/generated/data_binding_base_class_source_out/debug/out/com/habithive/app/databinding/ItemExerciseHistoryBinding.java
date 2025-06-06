// Generated by view binder compiler. Do not edit!
package com.habithive.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.habithive.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemExerciseHistoryBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final View divider;

  @NonNull
  public final LinearLayout statsLayout;

  @NonNull
  public final TextView textCalories;

  @NonNull
  public final TextView textDate;

  @NonNull
  public final TextView textDuration;

  @NonNull
  public final TextView textExerciseType;

  @NonNull
  public final TextView textNotes;

  @NonNull
  public final TextView textNotesLabel;

  @NonNull
  public final TextView textPoints;

  private ItemExerciseHistoryBinding(@NonNull CardView rootView, @NonNull View divider,
      @NonNull LinearLayout statsLayout, @NonNull TextView textCalories, @NonNull TextView textDate,
      @NonNull TextView textDuration, @NonNull TextView textExerciseType,
      @NonNull TextView textNotes, @NonNull TextView textNotesLabel, @NonNull TextView textPoints) {
    this.rootView = rootView;
    this.divider = divider;
    this.statsLayout = statsLayout;
    this.textCalories = textCalories;
    this.textDate = textDate;
    this.textDuration = textDuration;
    this.textExerciseType = textExerciseType;
    this.textNotes = textNotes;
    this.textNotesLabel = textNotesLabel;
    this.textPoints = textPoints;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemExerciseHistoryBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemExerciseHistoryBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_exercise_history, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemExerciseHistoryBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.divider;
      View divider = ViewBindings.findChildViewById(rootView, id);
      if (divider == null) {
        break missingId;
      }

      id = R.id.statsLayout;
      LinearLayout statsLayout = ViewBindings.findChildViewById(rootView, id);
      if (statsLayout == null) {
        break missingId;
      }

      id = R.id.textCalories;
      TextView textCalories = ViewBindings.findChildViewById(rootView, id);
      if (textCalories == null) {
        break missingId;
      }

      id = R.id.textDate;
      TextView textDate = ViewBindings.findChildViewById(rootView, id);
      if (textDate == null) {
        break missingId;
      }

      id = R.id.textDuration;
      TextView textDuration = ViewBindings.findChildViewById(rootView, id);
      if (textDuration == null) {
        break missingId;
      }

      id = R.id.textExerciseType;
      TextView textExerciseType = ViewBindings.findChildViewById(rootView, id);
      if (textExerciseType == null) {
        break missingId;
      }

      id = R.id.textNotes;
      TextView textNotes = ViewBindings.findChildViewById(rootView, id);
      if (textNotes == null) {
        break missingId;
      }

      id = R.id.textNotesLabel;
      TextView textNotesLabel = ViewBindings.findChildViewById(rootView, id);
      if (textNotesLabel == null) {
        break missingId;
      }

      id = R.id.textPoints;
      TextView textPoints = ViewBindings.findChildViewById(rootView, id);
      if (textPoints == null) {
        break missingId;
      }

      return new ItemExerciseHistoryBinding((CardView) rootView, divider, statsLayout, textCalories,
          textDate, textDuration, textExerciseType, textNotes, textNotesLabel, textPoints);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
