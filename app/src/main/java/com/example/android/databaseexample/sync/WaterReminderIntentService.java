package com.example.android.databaseexample.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Water reminder Intent Service
 */

public class WaterReminderIntentService extends IntentService {
    private static String TAG = WaterReminderIntentService.class.getSimpleName();

    public WaterReminderIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this, action);
    }
}
