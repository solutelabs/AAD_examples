package com.example.android.databaseexample.sync;

import android.content.Context;
import android.util.TimeUtils;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

/**
 * Reminder Utilities
 */

public class ReminderUtilities {
    private static final int REMINDER_INTERVAL_MINUTE = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTE));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;
    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";
    private static boolean sInitialized;

    synchronized public static void scheduleChargingReminder(Context context) {
        if (sInitialized) return;
        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(driver);
        Job constraintReminderJob = jobDispatcher.newJobBuilder()
                .setService(WaterReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                /**
                 * you can set constraint also like if you want to execute
                 * jobscheduler when device having internet connection or device in idle state,
                 * device in charge mode, etc
                 .setConstraints(Constraint.ON_ANY_NETWORK)
                 **/
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                ))
                .setReplaceCurrent(true)
                .build();
        jobDispatcher.schedule(constraintReminderJob);
        sInitialized = true;
    }
}
