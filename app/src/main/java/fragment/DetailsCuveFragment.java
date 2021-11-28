package fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suivi_vinification.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;

import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import ui.CuveActivity;
import ui.CuveDetails;
import ui.MainActivity;
import ui.SettingsActivity;
import viewModel.CuveViewModel;


/**
 * @author oceane
 * Fragment qui affiche les informations d'une cuve
 * Permet de consulter, modifier, supprimer
 */
public class DetailsCuveFragment extends Fragment {

    private boolean Editable;

    private TextInputEditText mNumber;
    private TextInputEditText mVolume;
    private TextInputEditText mPeriod;
    private TextInputEditText mColor;
    private TextInputEditText mVariety;
    private int number;
    private Button mModifie;
    private Button mDelete;

    Boolean status;

    private CuveEntity cuve;
    private CuveViewModel mViewModel;

    View mView;

    /**
     * Créer la view de la classe layout fragment_details_cuve
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_details_cuve, container, false);
        mNumber = (TextInputEditText) mView.findViewById(R.id.CuveActivity_Details_TextInputEdit_Number);
        mVolume = (TextInputEditText) mView.findViewById(R.id.CuveActivity_Details_TextInputEdit_Volume);
        mPeriod = (TextInputEditText) mView.findViewById(R.id.CuveActivity_Details_TextInputEdit_Period);
        mVariety = (TextInputEditText) mView.findViewById(R.id.CuveActivity_Details_TextInputEdit_Variety);
        mColor =(TextInputEditText) mView.findViewById(R.id.CuveActivity_Details_TextInputEdit_Color);
        mModifie = (Button) mView.findViewById(R.id.CuveActivity_details_button_Change);
        mDelete = (Button) mView.findViewById(R.id.CuveActivity_details_button_Delete);

        //récupère la view de la bonne cuve en fonction du number
        number = getActivity().getIntent().getIntExtra("cuveNumber", 0);

        initiateView();
        CuveViewModel.Factory factory = new CuveViewModel.Factory(getActivity().getApplication(), number);
        mViewModel = ViewModelProviders.of(this, factory).get(CuveViewModel.class);
        mViewModel.getCuve().observe(getViewLifecycleOwner(), cuveEntity -> {
            if (cuveEntity != null) {
                cuve = cuveEntity;
                updateContent();
            }

        });
        //if utilisé lorsque l'utilisateur ajoute une nouvelle cuve, on appelle la fonction createCuve
        if(number == 0){
            mDelete.setVisibility(View.INVISIBLE);
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
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("CuveDetails", "delete:fail");

                    }
                });
            }
        });
        return mView;
    }


    /**
     * Initialise la base de donnée et rends les champs non éditable
     */
    private void initiateView() {

        if (Editable == false) {

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

    /**
     * Sauvegarde les changements générer par l'utilisateurs
     */
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
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("CuveDetails", "updateClient: success");
                status = false;
                status();

            }
        });
    }

    /**
     * Informe sur le status des informations
     */
    private void status() {
        if (status == true) {
            Toast.makeText(getActivity(), "informations mises à jour", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Les informations n'ont pas été mises à jour", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Permet de créer une cuve
     */
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
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d("CuveDetails", "createClient: failure", e);
            }
        });
    }
}