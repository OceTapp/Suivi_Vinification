package ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.suivi_vinification.R;


/**
 * HOME
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTitle;
    private ImageView mImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.configureToolBar();
        mTitle = findViewById(R.id.MainActivity_textView_Title);
        mImage = findViewById(R.id.MainActivity_imageView_Image);
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