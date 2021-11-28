package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivi_vinification.R;

import fragment.DetailsCuveFragment;
import fragment.SuiviCuveFragment;

/**
 * @author oceane
 * Permet d'accéder aux classes DetailsCuveFragment et SuiviCuveFragment
 * Récupérer les données de la cuve sélectionnée en amont
 */
public class CuveDetails extends AppCompatActivity {

    private TextView mTitle;
    private int number;
    private Button mModifie;
    private Button mDelete;

    private LinearLayout mLayoutPref;

    private Button mCuveSuiviFragment;
    private Button mCuveDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve_details);

        mCuveSuiviFragment = findViewById(R.id.CuveeActivity_Button_SuiviCuve);
        mCuveDetailsFragment = findViewById(R.id.CuveeActivity_Button_DetailsCuve);

        mCuveSuiviFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SuiviCuveFragment());

            }
        });

        mCuveDetailsFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DetailsCuveFragment());
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.CuveActivity_details_textView_Title);
        mModifie = findViewById(R.id.CuveActivity_details_button_Change);
        mDelete = findViewById(R.id.CuveActivity_details_button_Delete);
        mLayoutPref = findViewById(R.id.CuveActivity_Details_Layout);

        number = getIntent().getIntExtra("cuveNumber", 0);
        if(number == 0){
            replaceFragment(new DetailsCuveFragment());
            mCuveSuiviFragment.setVisibility(View.INVISIBLE);
            mCuveDetailsFragment.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            //Amener sur l'activité paramètre
            case R.id.main_params:
                Toast.makeText(this, "Affiche la page paramètre", Toast.LENGTH_SHORT).show();
                Intent settingsActivityIntent = new Intent(CuveDetails.this, SettingsActivity.class);
                startActivity(settingsActivityIntent);
                return true;
            //Amener sur l'activité cuve
            case R.id.main_cuves:
                Intent cuveActivityIntent = new Intent(CuveDetails.this, CuveActivity.class);
                startActivity(cuveActivityIntent);

                return true;
            case R.id.main_home:
                Intent homeActivityIntent = new Intent(CuveDetails.this, MainActivity.class);
                startActivity(homeActivityIntent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * Permet d'afficher un autre fragment
     */
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

        private void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean theme = sp.getBoolean("theme_preference", false);
        if (theme) {
            mLayoutPref.setBackgroundColor(Color.parseColor("#000000"));
            mTitle.setTextColor(Color.parseColor("#ffffff"));

        } else {
            mLayoutPref.setBackgroundColor(Color.parseColor("#ffffff"));
            mTitle.setTextColor(Color.parseColor("#000000"));
        }
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