// Generated by view binder compiler. Do not edit!
package com.habithive.app.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.habithive.app.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityNotificationSettingsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button buttonSaveNotificationSettings;

  @NonNull
  public final CardView cardEnableNotifications;

  @NonNull
  public final CardView cardReminderSettings;

  @NonNull
  public final CheckBox checkboxFriday;

  @NonNull
  public final CheckBox checkboxMonday;

  @NonNull
  public final CheckBox checkboxSaturday;

  @NonNull
  public final CheckBox checkboxSunday;

  @NonNull
  public final CheckBox checkboxThursday;

  @NonNull
  public final CheckBox checkboxTuesday;

  @NonNull
  public final CheckBox checkboxWednesday;

  @NonNull
  public final ConstraintLayout layoutNotificationOptions;

  @NonNull
  public final LinearLayout layoutReminderTime;

  @NonNull
  public final SwitchCompat switchEnableNotifications;

  @NonNull
  public final TextView textDescription;

  @NonNull
  public final TextView textEnableNotifications;

  @NonNull
  public final TextView textEnableNotificationsDescription;

  @NonNull
  public final TextView textReminderDaysLabel;

  @NonNull
  public final TextView textReminderSettings;

  @NonNull
  public final TextView textReminderSettingsDescription;

  @NonNull
  public final TextView textReminderTime;

  @NonNull
  public final TextView textReminderTimeLabel;

  @NonNull
  public final TextView textTitle;

  private ActivityNotificationSettingsBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button buttonSaveNotificationSettings, @NonNull CardView cardEnableNotifications,
      @NonNull CardView cardReminderSettings, @NonNull CheckBox checkboxFriday,
      @NonNull CheckBox checkboxMonday, @NonNull CheckBox checkboxSaturday,
      @NonNull CheckBox checkboxSunday, @NonNull CheckBox checkboxThursday,
      @NonNull CheckBox checkboxTuesday, @NonNull CheckBox checkboxWednesday,
      @NonNull ConstraintLayout layoutNotificationOptions, @NonNull LinearLayout layoutReminderTime,
      @NonNull SwitchCompat switchEnableNotifications, @NonNull TextView textDescription,
      @NonNull TextView textEnableNotifications,
      @NonNull TextView textEnableNotificationsDescription, @NonNull TextView textReminderDaysLabel,
      @NonNull TextView textReminderSettings, @NonNull TextView textReminderSettingsDescription,
      @NonNull TextView textReminderTime, @NonNull TextView textReminderTimeLabel,
      @NonNull TextView textTitle) {
    this.rootView = rootView;
    this.buttonSaveNotificationSettings = buttonSaveNotificationSettings;
    this.cardEnableNotifications = cardEnableNotifications;
    this.cardReminderSettings = cardReminderSettings;
    this.checkboxFriday = checkboxFriday;
    this.checkboxMonday = checkboxMonday;
    this.checkboxSaturday = checkboxSaturday;
    this.checkboxSunday = checkboxSunday;
    this.checkboxThursday = checkboxThursday;
    this.checkboxTuesday = checkboxTuesday;
    this.checkboxWednesday = checkboxWednesday;
    this.layoutNotificationOptions = layoutNotificationOptions;
    this.layoutReminderTime = layoutReminderTime;
    this.switchEnableNotifications = switchEnableNotifications;
    this.textDescription = textDescription;
    this.textEnableNotifications = textEnableNotifications;
    this.textEnableNotificationsDescription = textEnableNotificationsDescription;
    this.textReminderDaysLabel = textReminderDaysLabel;
    this.textReminderSettings = textReminderSettings;
    this.textReminderSettingsDescription = textReminderSettingsDescription;
    this.textReminderTime = textReminderTime;
    this.textReminderTimeLabel = textReminderTimeLabel;
    this.textTitle = textTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityNotificationSettingsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_notification_settings, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityNotificationSettingsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonSaveNotificationSettings;
      Button buttonSaveNotificationSettings = ViewBindings.findChildViewById(rootView, id);
      if (buttonSaveNotificationSettings == null) {
        break missingId;
      }

      id = R.id.cardEnableNotifications;
      CardView cardEnableNotifications = ViewBindings.findChildViewById(rootView, id);
      if (cardEnableNotifications == null) {
        break missingId;
      }

      id = R.id.cardReminderSettings;
      CardView cardReminderSettings = ViewBindings.findChildViewById(rootView, id);
      if (cardReminderSettings == null) {
        break missingId;
      }

      id = R.id.checkboxFriday;
      CheckBox checkboxFriday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxFriday == null) {
        break missingId;
      }

      id = R.id.checkboxMonday;
      CheckBox checkboxMonday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxMonday == null) {
        break missingId;
      }

      id = R.id.checkboxSaturday;
      CheckBox checkboxSaturday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxSaturday == null) {
        break missingId;
      }

      id = R.id.checkboxSunday;
      CheckBox checkboxSunday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxSunday == null) {
        break missingId;
      }

      id = R.id.checkboxThursday;
      CheckBox checkboxThursday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxThursday == null) {
        break missingId;
      }

      id = R.id.checkboxTuesday;
      CheckBox checkboxTuesday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxTuesday == null) {
        break missingId;
      }

      id = R.id.checkboxWednesday;
      CheckBox checkboxWednesday = ViewBindings.findChildViewById(rootView, id);
      if (checkboxWednesday == null) {
        break missingId;
      }

      id = R.id.layoutNotificationOptions;
      ConstraintLayout layoutNotificationOptions = ViewBindings.findChildViewById(rootView, id);
      if (layoutNotificationOptions == null) {
        break missingId;
      }

      id = R.id.layoutReminderTime;
      LinearLayout layoutReminderTime = ViewBindings.findChildViewById(rootView, id);
      if (layoutReminderTime == null) {
        break missingId;
      }

      id = R.id.switchEnableNotifications;
      SwitchCompat switchEnableNotifications = ViewBindings.findChildViewById(rootView, id);
      if (switchEnableNotifications == null) {
        break missingId;
      }

      id = R.id.textDescription;
      TextView textDescription = ViewBindings.findChildViewById(rootView, id);
      if (textDescription == null) {
        break missingId;
      }

      id = R.id.textEnableNotifications;
      TextView textEnableNotifications = ViewBindings.findChildViewById(rootView, id);
      if (textEnableNotifications == null) {
        break missingId;
      }

      id = R.id.textEnableNotificationsDescription;
      TextView textEnableNotificationsDescription = ViewBindings.findChildViewById(rootView, id);
      if (textEnableNotificationsDescription == null) {
        break missingId;
      }

      id = R.id.textReminderDaysLabel;
      TextView textReminderDaysLabel = ViewBindings.findChildViewById(rootView, id);
      if (textReminderDaysLabel == null) {
        break missingId;
      }

      id = R.id.textReminderSettings;
      TextView textReminderSettings = ViewBindings.findChildViewById(rootView, id);
      if (textReminderSettings == null) {
        break missingId;
      }

      id = R.id.textReminderSettingsDescription;
      TextView textReminderSettingsDescription = ViewBindings.findChildViewById(rootView, id);
      if (textReminderSettingsDescription == null) {
        break missingId;
      }

      id = R.id.textReminderTime;
      TextView textReminderTime = ViewBindings.findChildViewById(rootView, id);
      if (textReminderTime == null) {
        break missingId;
      }

      id = R.id.textReminderTimeLabel;
      TextView textReminderTimeLabel = ViewBindings.findChildViewById(rootView, id);
      if (textReminderTimeLabel == null) {
        break missingId;
      }

      id = R.id.textTitle;
      TextView textTitle = ViewBindings.findChildViewById(rootView, id);
      if (textTitle == null) {
        break missingId;
      }

      return new ActivityNotificationSettingsBinding((ConstraintLayout) rootView,
          buttonSaveNotificationSettings, cardEnableNotifications, cardReminderSettings,
          checkboxFriday, checkboxMonday, checkboxSaturday, checkboxSunday, checkboxThursday,
          checkboxTuesday, checkboxWednesday, layoutNotificationOptions, layoutReminderTime,
          switchEnableNotifications, textDescription, textEnableNotifications,
          textEnableNotificationsDescription, textReminderDaysLabel, textReminderSettings,
          textReminderSettingsDescription, textReminderTime, textReminderTimeLabel, textTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
