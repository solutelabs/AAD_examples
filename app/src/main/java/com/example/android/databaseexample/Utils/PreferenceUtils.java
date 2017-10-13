package com.example.android.databaseexample.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

/**
 * TODO: Add a class header comment!
 */

public class PreferenceUtils {
    public static final String KEY_WATER_COUNT = "water_count";
    public static final String KEY_CHARGING_REMINDER_COUNT = "charging_reminder_count";
    private static final int DEFAULT_COUNT = 0;

    private static SharedPreferences getPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static int getWaterCount(Context context) {
        return getPreference(context).getInt(KEY_WATER_COUNT, DEFAULT_COUNT);
    }

    public static synchronized void setWaterCounter(Context context, int newCounter) {
        SharedPreferences.Editor editor = getPreference(context).edit();
        editor.putInt(KEY_WATER_COUNT, newCounter);
        editor.apply();
    }

    public synchronized static void incrementWaterCounter(Context context) {
        int currentCounter = getWaterCount(context);
        setWaterCounter(context, ++currentCounter);
    }
}
