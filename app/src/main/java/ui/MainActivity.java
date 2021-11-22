package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.suivi_vinification.R;


/**
 * HOME
 */
public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mTitle;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolBar();
        mTitle = findViewById(R.id.MainActivity_textView_Title);
        mImage = findViewById(R.id.MainActivity_imageView_Image);

        savePreferences();
    }

    private void savePreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    private void changeTextColor(String pref_color_value) {
        Log.d("MainActivity", "changeTextColor");
        switch (pref_color_value) {
            case "blue":
                mTitle.setTextColor(Color.BLUE);
                break;
            case "green":
                mTitle.setTextColor(Color.GREEN);
                break;
            case "red":
                mTitle.setTextColor(Color.RED);
                break;
            case "pink":
                mTitle.setTextColor(Color.CYAN);
                break;
            default:
                mTitle.setTextColor(Color.BLACK);
        }
    }
    private void loadColorFromPreference(SharedPreferences sharedPreferences) {
        Log.d("MainActivity",sharedPreferences.getString(getString(R.string.color_key),"R.string.color_green_value"));
        changeTextColor(sharedPreferences.getString(getString(R.string.color_key),getString(R.string.color_green_value)));
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        loadColorFromPreference(sharedPreferences);

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //On sérialise le menu afin de l'ajouter à la bar
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    //Configurer la toolbar
    public void configureToolBar(){
        //récupère la vu et l'ajoute à l'activité via setSupportActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    //qu'est-ce qui se passe quand on choisi l'une des options de la toolbar

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            //3 - Handle actions on menu items
            switch (item.getItemId()) {
                //Amener sur l'activité paramètre
                case R.id.main_params:
                    Toast.makeText(this, "Affiche la page paramètre", Toast.LENGTH_SHORT).show();
                    Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(settingsActivityIntent);
                    return true;
                    //Amener sur l'activité cuve
                case R.id.main_cuves:
                    Toast.makeText(this, "Affiche l'activité des cuves", Toast.LENGTH_SHORT).show();
                    Intent cuveActivityIntent = new Intent(MainActivity.this, CuveActivity.class);
                    startActivity(cuveActivityIntent);

                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }


         }