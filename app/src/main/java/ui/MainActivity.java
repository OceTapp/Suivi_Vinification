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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import com.example.suivi_vinification.R;


/**
 * HOME
 */
public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutPref;

    private TextView mTitle;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutPref = findViewById(R.id.Layout);
        mTitle = findViewById(R.id.MainActivity_textView_Title);
        mImage = findViewById(R.id.MainActivity_imageView_Image);

        Load_setting();

    }

    private void Load_setting(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean theme = sp.getBoolean("theme_preference", false);
        if (theme) {
            layoutPref.setBackgroundColor(Color.parseColor("#000000"));
            mTitle.setTextColor(Color.parseColor("#ffffff"));
            mImage.setImageResource(R.drawable.picture_background_dark);

        } else {
            layoutPref.setBackgroundColor(Color.parseColor("#ffffff"));
            mTitle.setTextColor(Color.parseColor("#000000"));
            mImage.setImageResource(R.drawable.picture_background);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //On sérialise le menu afin de l'ajouter à la bar
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.getItem(0).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.main_params:
                    Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(settingsActivityIntent);
                    return true;
                case R.id.main_cuves:
                    Intent cuveActivityIntent = new Intent(MainActivity.this, CuveActivity.class);
                    startActivity(cuveActivityIntent);

                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
}