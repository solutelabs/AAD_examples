package com.example.android.databaseexample.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.example.android.databaseexample.R;
import com.example.android.databaseexample.drinking_water.DrinkingWaterActivity;
import com.example.android.databaseexample.sync.ReminderTask;
import com.example.android.databaseexample.sync.WaterReminderIntentService;

import static android.support.v4.app.NotificationManagerCompat.IMPORTANCE_HIGH;

/**
 * Notification Utils
 */

public class NotificationUtils {
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3001;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 10;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1000;
    private static final String CHANNEL_ID = "channel_id";

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserForDrinking(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drinking_water)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.drinking_reminder_notification_title))
                .setContentText(context.getString(R.string.drinking_reminder_notification_text))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.drinking_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setPriority(IMPORTANCE_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        /**
         * WATER_REMINDER_NOTIFICATION_ID
         */
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, builder.build());

    }

    private static NotificationCompat.Action ignoreReminderAction(Context context) {
        Intent ignoreDrinkingWater = new Intent(context, WaterReminderIntentService.class);
        ignoreDrinkingWater.setAction(ReminderTask.ACTION_DISMISS_NOTIFICATION);
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreDrinkingWater,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action ignoreReminderAction = new NotificationCompat.Action(
                R.drawable.ic_drinking_water,
                "No, thanks.",
                ignoreReminderPendingIntent);
        return ignoreReminderAction;
    }

    private static NotificationCompat.Action drinkWaterAction(Context context) {
        Intent incrementWaterCountIntent = new Intent(context, WaterReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTask.ACTION_INCREMENT_WATER_COUNT);
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );
        NotificationCompat.Action drinkWaterAction = new NotificationCompat.Action(
                R.drawable.ic_drinking_water,
                "I did it!",
                incrementWaterPendingIntent);
        return drinkWaterAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, DrinkingWaterActivity.class);
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        return BitmapFactory.decodeResource(
                context.getResources(), R.drawable.ic_drinking_water_large);
    }
}
