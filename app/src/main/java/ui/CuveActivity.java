package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suivi_vinification.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerAdapter;
import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import util.RecyclerViewItemClickListener;
import viewModel.CuveListViewModel;
import viewModel.CuveViewModel;

/**
 * AFFICHE LA LISTE DES CUVES
 */
//TODO relire l'ensemble du code
public class CuveActivity extends AppCompatActivity {

    private TextView mTitle;
    private Button mButton_Ajouter;
    private ImageView mImage;
    private TextView mCuve;
    private TextView mCepage;
    private Button mButtonDetails;
    // MainActivity in;

    private List<CuveEntity> cuves;
    private RecyclerAdapter recyclerAdapter;
    private CuveListViewModel mViewListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve);
        //   in.configureToolBar();


        mTitle = findViewById(R.id.CuveActivity_textView_Title);
        mButton_Ajouter = findViewById(R.id.CuveActivity_button_Ajouter);
        //mImage = findViewById(R.id.CuveActivity_ImageView_Card);
        //mCuve = findViewById(R.id.CuveActivity_TextView_TitleCard);
        //mCepage = findViewById(R.id.CuveActivity_TextView_CepageCard);
        //mButtonDetails = findViewById(R.id.CuveActivity_Button_Details);

        RecyclerView recyclerView = findViewById(R.id.cuvesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

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
                intent.putExtra("cuveNumber", cuves.get(position).getNumber());
                startActivity(intent);
            }

            int number = getIntent().getIntExtra("CuveNumber", 0);

            @Override
            public void onItemLongClick(View v, int position) {
                Log.d("CuveActvity", "longClicked position:" + position);
                Log.d("CuveActivity", "longClicked on: " + cuves.get(position).toString());
            }
        });


        //TODO comprendre le code
        CuveListViewModel.Factory factory = new CuveListViewModel.Factory(getApplication());
        mViewListModel = ViewModelProviders.of(this, factory).get(CuveListViewModel.class);
        mViewListModel.getCuves().observe(this, cuveEntities -> {
            if (cuveEntities != null) {
                cuves = cuveEntities;
                //recyclerAdapter.setData(clients);

            }
        });

        recyclerView.setAdapter(recyclerAdapter);

        //amène à l'activité cuveDetails
        mButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsActivityIntent = new Intent(CuveActivity.this, CuveDetails.class);
                startActivity(detailsActivityIntent);

            }
        });
    }
    }