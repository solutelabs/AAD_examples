package com.example.android.databaseexample.sync;

import android.content.Context;

import com.example.android.databaseexample.Utils.NotificationUtils;
import com.example.android.databaseexample.Utils.PreferenceUtils;

/**
 * ReminderTask
 */

public class ReminderTask {
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment_water_count";
    public static final String ACTION_CHARGING_REMINDER = "charging_reminder";
    public static final String ACTION_DISMISS_NOTIFICATION = "dismiss-notification";

    public static void executeTask(Context context, String action) {
        if (action.equals(ACTION_INCREMENT_WATER_COUNT)) {
            incrementWaterCount(context);
        } else if (action.equals(ACTION_CHARGING_REMINDER)) {
            reminderForWater(context);
        } else if (action.equals(ACTION_DISMISS_NOTIFICATION)) {
            NotificationUtils.clearAllNotifications(context);
        }
    }

    private static void reminderForWater(Context context) {
        NotificationUtils.remindUserForDrinking(context);
    }

    private static void incrementWaterCount(Context context) {
        PreferenceUtils.incrementWaterCounter(context);
        NotificationUtils.clearAllNotifications(context);
    }
}
