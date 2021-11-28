package database.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.suivi_vinification.R;


/**
 * Fragment qui affiche le suivi d'une cuve
 */
public class SuiviCuveFragment extends Fragment {
    View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_suivi_cuve, container, false);

        return mView;
    }
}