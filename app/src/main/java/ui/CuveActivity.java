package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivi_vinification.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import adapter.RecyclerAdapter;
import database.entity.CuveEntity;
import util.RecyclerViewItemClickListener;
import viewModel.CuveListViewModel;
import viewModel.CuveViewModel;

/**
 * AFFICHE LA LISTE DES CUVES
 */
public class CuveActivity extends AppCompatActivity {

    private TextView mTitle;
    private Button mButton_Ajouter;
    private RecyclerView mView;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayout mLayout;

    private List<CuveEntity> cuves;
    private CuveListViewModel mViewListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTitle = findViewById(R.id.CuveActivity_textView_Title);
        mButton_Ajouter = findViewById(R.id.CuveActivity_button_Ajouter);
        mView = findViewById(R.id.cuvesRecyclerView);
        mLayout = findViewById(R.id.CuveActivity_Layout);

        Load_setting();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mView.getContext(),
                LinearLayoutManager.VERTICAL);
        mView.addItemDecoration(dividerItemDecoration);

        cuves = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("CuveActivity", "clicked position:" + position);
                Log.d("CuveActivity", "clicked on: " + cuves.get(position).toString());

                //ammène vers la vue CuveDetails
                Intent intent = new Intent(CuveActivity.this, CuveDetails.class);

                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );

                //transmettre les données à une nouvelle activité
                intent.putExtra("cuveNumber", cuves.get(position).getNumber());
                //intent.putExtra("cuveVariety", cuves.get(position).getVariety());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d("cuveActivity", "longClicked position:" + position);
                Log.d("cuveActivity", "longClicked on: " + cuves.get(position).toString());
            }
        });

        CuveListViewModel.Factory factory = new CuveListViewModel.Factory(getApplication());
        mViewListModel = ViewModelProviders.of(this, factory).get(CuveListViewModel.class);
        mViewListModel.getCuves().observe(this, cuveEntities -> {
            if (cuveEntities != null) {
                cuves = cuveEntities;
                recyclerAdapter.setData(cuves);
            }
        });

        mView.setAdapter(recyclerAdapter);

        mButton_Ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cuveActivityIntent = new Intent(CuveActivity.this,CuveDetails.class);
                startActivity(cuveActivityIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //On sérialise le menu afin de l'ajouter à la bar
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.getItem(1).setVisible(false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.main_params:
                Intent settingsActivityIntent = new Intent(CuveActivity.this, SettingsActivity.class);
                startActivity(settingsActivityIntent);
                return true;
            case R.id.main_home:
                Intent homeActivityIntent = new Intent(CuveActivity.this, MainActivity.class);
                startActivity(homeActivityIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void Load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean theme = sp.getBoolean("theme_preference", false);
        if (theme) {
            mLayout.setBackgroundColor(Color.parseColor("#000000"));
            mTitle.setTextColor(Color.parseColor("#ffffff"));

        } else {
            mLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            mTitle.setTextColor(Color.parseColor("#000000"));

        }
    }
    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
}

