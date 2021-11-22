package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.suivi_vinification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import database.AppDatabase;
import database.async.CreateCuve;
import database.dao.CuveDao;
import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import util.RecyclerViewItemClickListener;
import viewModel.Cuve;
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

    private List<CuveEntity> cuves;
    private CuveListViewModel mViewListModel;
    private CuveEntity cuve;
    private CuveViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve);


        mTitle = findViewById(R.id.CuveActivity_textView_Title);
        mButton_Ajouter = findViewById(R.id.CuveActivity_button_Ajouter);
        mView = findViewById(R.id.cuvesRecyclerView);

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
}

