package ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;


import com.example.suivi_vinification.R;

/**
 * @author oceane
 * Configuration de l'application
 * Permet d'indiquer si on veut utiliser le dark mode ou le light mode
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_pref);

        Load_setting();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    private void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean chk_night = sp.getBoolean("theme_preference", false);
        if (chk_night) {
            getListView().setBackgroundColor(Color.parseColor("#222222"));
        } else {
            getListView().setBackgroundColor(Color.parseColor("#ffffff"));
        }

        android.preference.SwitchPreference night = (SwitchPreference) findPreference("theme_preference");
        night.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object obj) {

                boolean yes = (boolean) obj;

                if (yes) {
                    getListView().setBackgroundColor(Color.parseColor("#222222"));

                } else {
                    getListView().setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return true;
            }
        });
    }
    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}