package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

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
    private Button mModifie;
    private Button mDelete;
    private Button mFollow;

    private CuveEntity cuve;
    private CuveViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuve_details);

        mTitle = findViewById(R.id.CuveActivity_details_textView_Title);
        mModifie = findViewById(R.id.CuveActivity_details_button_Change);
        mDelete= findViewById(R.id.CuveActivity_details_button_Delete);
        mFollow = findViewById(R.id.CuveActivity_details_button_Follow);

        //TODO à revoir
        int number = getIntent().getIntExtra("CuveNumber",0);

        initiateView();

        //TODO comprendre le code
        CuveViewModel.Factory factory = new CuveViewModel.Factory(getApplication(), number);
        mViewModel = ViewModelProviders.of(this,factory).get(CuveViewModel.class);
        mViewModel.getCuve().observe(this, cuveEntity -> {
            if(cuveEntity != null){
                cuve = cuveEntity;
                updateContent();
            }
        });
        //TODO à revoir
        if(number != -1){
            setTitle("No identification");
        }else{
            setTitle("Details cuve");
            //switchEditableMode(); --> Methode qui permettra d'éditer la vu
        }
    }

    private void CreateCuve(int number, int volume, String period, String color, String variety) {

        cuve = new CuveEntity();
        cuve.setNumber(number);
        cuve.setVolume(volume);
        cuve.setPeriod(period);
        cuve.setColor(color);
        cuve.setVariety(variety);

        mViewModel.createCuve(cuve, new OnAsyncEventListener() {
            @Override
            public void onSuccess() {
                Log.d("CuveDetails", "createClient: success");
                onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("CuveDetails", "createClient: failure", e);
            }
        });
    }

    /**
     * Initialise la base de donnée et rends les champs non éditable
     */
    private void initiateView(){

        Editable = false;
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

    }



    /**
     * Met à jour les champs en fonction des données de la cuve
     */
    private void updateContent(){
        if (cuve != null){
            mNumber.setText(cuve.getNumber());
            mVolume.setText(cuve.getVolume());
            mPeriod.setText(cuve.getPeriod());
            mColor.setText(cuve.getColor());
            mVariety.setText(cuve.getVariety());
        }
    }

    /*
    public void Back(View v) {
        //pour retourner a l’activite principale il suffit seulement d’appler la methode finish() qui vas tuer cette activite

        finish() ;

    }

 */




}