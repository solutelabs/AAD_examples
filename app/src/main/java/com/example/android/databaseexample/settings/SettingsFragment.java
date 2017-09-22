package com.example.android.databaseexample.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import android.widget.Toast;

import com.example.android.databaseexample.R;

/**
 * Setting Fragment
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences
        .OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        Log.d("Setting Fragment", "OnCreatePreference method called");
        addPreferencesFromResource(R.xml.pref_settings);
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
            Preference p = preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference)){
                String value = sharedPreferences.getString(p.getKey(), "");
                setPrefSummary(p, value);
            }
        }

    }

    private void setPrefSummary(Preference preference, String value) {

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }else{
            preference.setSummary(value);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPrefSummary(preference, sharedPreferences.getString(key, ""));
            }
        }
    }
}
