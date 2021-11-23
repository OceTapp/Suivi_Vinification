package ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;


import com.example.suivi_vinification.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_pref);

        Load_setting();

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
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            //Amener sur l'activité paramètre
            case R.id.main_params:
                Toast.makeText(this, "Affiche la page paramètre", Toast.LENGTH_SHORT).show();
                Intent settingsActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(settingsActivityIntent);
                return true;
            //Amener sur l'activité cuve
            case R.id.main_cuves:
                Toast.makeText(this, "Affiche l'activité des cuves", Toast.LENGTH_SHORT).show();
                Intent cuveActivityIntent = new Intent(SettingsActivity.this, CuveActivity.class);
                startActivity(cuveActivityIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}