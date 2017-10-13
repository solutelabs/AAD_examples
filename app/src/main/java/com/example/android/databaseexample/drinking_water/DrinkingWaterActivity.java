package com.example.android.databaseexample.drinking_water;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.databaseexample.R;
import com.example.android.databaseexample.Utils.PreferenceUtils;
import com.example.android.databaseexample.sync.ReminderTask;
import com.example.android.databaseexample.sync.ReminderUtilities;

import static com.example.android.databaseexample.sync.ReminderTask.ACTION_INCREMENT_WATER_COUNT;

/**
 * Drinking water activity
 */

public class DrinkingWaterActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    TextView tvCounter;
    ImageButton ibClickMe;
    ImageView ivBatteryStatus;
    TextView tvBatterStatus;

    BatteryStatusReceiver mBatteryStatusReceiver;
    IntentFilter mBatteryStatusIntentFilter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);
        initViews();
        updateWaterCount();
        setListener();
        ReminderUtilities.scheduleChargingReminder(this);
        SharedPreferences sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreference.registerOnSharedPreferenceChangeListener(this);
        initReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBatteryStatusReceiver, mBatteryStatusIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBatteryStatusReceiver);
    }

    private void initReceiver() {
        mBatteryStatusIntentFilter = new IntentFilter();
        mBatteryStatusReceiver = new BatteryStatusReceiver();
        mBatteryStatusIntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    }

    private void setListener() {
        ibClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderTask.executeTask(DrinkingWaterActivity.this, ACTION_INCREMENT_WATER_COUNT);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void updateWaterCount() {
        int waterCount = PreferenceUtils.getWaterCount(this);
        tvCounter.setText(waterCount + "");
    }

    private void initViews() {
        tvCounter = findViewById(R.id.tv_counter);
        ivBatteryStatus = findViewById(R.id.iv_battery_status);
        ibClickMe = findViewById(R.id.ib_drinking_counter);
        tvBatterStatus = findViewById(R.id.tv_battery_status);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtils.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount();
        }
    }

    private void showBatteryStatus(int level) {
        int resource = R.drawable.ic_below_30;
        if (level <= 30) {
            resource = R.drawable.ic_below_30;
        } else if (level > 30 && level <= 40) {
            resource = R.drawable.ic_inbetween_30_to_40;
        } else if (level > 40 && level <= 50) {
            resource = R.drawable.ic_inbetween_40_to_50;
        } else if (level > 50 && level <= 80) {
            resource = R.drawable.ic_inbetween_50_to_80;
        } else if (level > 80) {
            resource = R.drawable.ic_bettery_above_80;
        }
        ivBatteryStatus.setImageResource(resource);
        tvBatterStatus.setText(getString(R.string.battery_status) + " " + level + "%");
    }

    private class BatteryStatusReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            showBatteryStatus(level);
        }
    }
}
