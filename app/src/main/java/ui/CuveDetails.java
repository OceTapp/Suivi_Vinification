package ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivi_vinification.R;
import com.google.android.material.textfield.TextInputEditText;

import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import viewModel.CuveViewModel;

/**
 * Affiche les détails d'une cuve
 */
public class CuveDetails extends AppCompatActivity {

    private boolean Editable;

    private TextView mTitle;
    private TextInputEditText mNumber;
    private TextInputEditText mVolume;
    private TextInputEditText mPeriod;
    private TextInputEditText mColor;
    private TextInputEditText mVariety;
    private int number;
    private Button mModifie;
    private Button mDelete;
    private Button mFollow;

    Boolean status;

    private CuveEntity cuve;
    private CuveViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve_details);

        mTitle = findViewById(R.id.CuveActivity_details_textView_Title);
        mModifie = findViewById(R.id.CuveActivity_details_button_Change);
        mDelete = findViewById(R.id.CuveActivity_details_button_Delete);
        mFollow = findViewById(R.id.CuveActivity_details_button_Follow);

        //  int number = getIntent().getIntExtra("CuveNumber",cuve.getNumber());
        //récupérer donnée de cuveActivité
        number = getIntent().getIntExtra("cuveNumber", 0);

        initiateView();

        CuveViewModel.Factory factory = new CuveViewModel.Factory(getApplication(), number);
        mViewModel = ViewModelProviders.of(this, factory).get(CuveViewModel.class);
        mViewModel.getCuve().observe(this, cuveEntity -> {
            if (cuveEntity != null) {
                cuve = cuveEntity;
                updateContent();
            }

        });
        if(number == 0){
            Editable = true;
            initiateView();
            mModifie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createCuve(Integer.parseInt(String.valueOf(mNumber.getText())),
                            Integer.parseInt(String.valueOf(mVolume.getText())),
                            mPeriod.getText().toString(),
                            mColor.getText().toString(),
                            mVariety.getText().toString());
                }
            });
        }
        if(number != 0) {
            mModifie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mModifie.getText().equals("Modifier")) {
                        Editable = true;
                        initiateView();
                        mModifie.setText("Valider");
                    } else {
                        saveChanges(Integer.parseInt(String.valueOf(mNumber.getText())),
                                Integer.parseInt(String.valueOf(mVolume.getText())),
                                mPeriod.getText().toString(),
                                mColor.getText().toString(),
                                mVariety.getText().toString());
                        Editable = false;
                        mModifie.setText("Modifier");
                    }
                }
            });
        }



        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mViewModel.deleteCuve(cuve, new OnAsyncEventListener() {
                  @Override
                  public void onSuccess() {
                      Log.d("CuveDetails", "delete:success");
                      onBackPressed();
                  }

                  @Override
                  public void onFailure(Exception e) {
                      Log.d("CuveDetails", "delete:fail");

                  }
              });



            }
        });
    }


    /**
     * Initialise la base de donnée et rends les champs non éditable
     */
    private void initiateView() {

        if (Editable == false) {

            mNumber = findViewById(R.id.CuveActivity_Details_TextInputEdit_Number);
            mVolume = findViewById(R.id.CuveActivity_Details_TextInputEdit_Volume);
            mPeriod = findViewById(R.id.CuveActivity_Details_TextInputEdit_Period);
            mColor = findViewById(R.id.CuveActivity_Details_TextInputEdit_Color);
            mVariety = findViewById(R.id.CuveActivity_Details_TextInputEdit_Variety);

            mNumber.setFocusable(false);
            mNumber.setEnabled(false);
            mVolume.setFocusable(false);
            mVolume.setEnabled(false);
            mPeriod.setFocusable(false);
            mPeriod.setEnabled(false);
            mColor.setFocusable(false);
            mColor.setEnabled(false);
            mVariety.setFocusable(false);
            mVariety.setEnabled(false);

            mModifie.setText("Modifier");
        } else {
            mNumber.setFocusable(true);
            mNumber.setEnabled(true);
            mVolume.setFocusable(true);
            mVolume.setEnabled(true);
            mPeriod.setFocusable(true);
            mPeriod.setEnabled(true);
            mColor.setFocusable(true);
            mColor.setEnabled(true);
            mVariety.setFocusable(true);
            mVariety.setEnabled(true);

            mModifie.setText("Valider");
        }

    }

    /**
     * Met à jour les champs en fonction des données de la cuve
     */
    private void updateContent() {
        if (cuve != null) {
            mNumber.setText(String.valueOf(cuve.getNumber()));
            mVolume.setText(String.valueOf(cuve.getVolume()));
            mPeriod.setText(cuve.getPeriod());
            mColor.setText(cuve.getColor());
            mVariety.setText(cuve.getVariety());
        }
    }

    private void saveChanges(int number, int volume, String period, String color, String variety) {
        cuve.setNumber(number);
        cuve.setVolume(volume);
        cuve.setPeriod(period);
        cuve.setColor(color);
        cuve.setVariety(variety);

        mViewModel.updateCuve(cuve, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d("CuveDetails", "updateClient: success");
                status = true;
                status();
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("CuveDetails", "updateClient: success");
                status = false;
                status();

            }
        });
    }

    private void status() {
        if (status == true) {
            Toast.makeText(this, "informations mises à jour", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Les informations n'ont pas été mises à jour", Toast.LENGTH_LONG).show();
        }
    }

    private void createCuve(int number, int volume, String period, String color, String variety){
        cuve = new CuveEntity();
        cuve.setNumber(number);
        cuve.setVolume(volume);
        cuve.setPeriod(period);
        cuve.setColor(color);
        cuve.setVariety(variety);

        mViewModel.createCuve(cuve, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d("CuveActvity", "createClient: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("CuveDetails", "createClient: failure", e);
            }
        });
    }
}
