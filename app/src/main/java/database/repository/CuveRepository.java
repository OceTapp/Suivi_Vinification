package database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import database.AppDatabase;
import database.async.CreateCuve;
import database.async.DeleteCuve;
import database.async.UpdateCuve;
import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import firebase.CuveListLiveData;
import firebase.CuveLiveData;

/**
 * Gestion de toutes les données de l'app dans des instances
 */
public class CuveRepository {

    private static CuveRepository instance;

    private CuveRepository() {}

    /**
     * Création d'une instance
     */
    public static CuveRepository getInstance() {
        if (instance == null) {
            synchronized (CuveRepository.class) {
                if (instance == null) {
                    instance = new CuveRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<CuveEntity> getCuve(final int number) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cuves")
                //A REVOIR
                .child(String.valueOf(number));
        return new CuveLiveData(reference);
    }


    /**
     * Liste l'ensemble des cuves en utilisant la requête getAllCuves de la DAO
     */
    public LiveData<List<CuveEntity>> getAllCuves() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cuves");
        return new CuveListLiveData(reference);
    }

    public void insert(final CuveEntity cuve, OnAsyncEventListener callback) {
        String id = FirebaseDatabase.getInstance().getReference("cuves").push().getKey();
        FirebaseDatabase.getInstance()
                .getReference("cuves")
                .child(id)
                .setValue(cuve, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void update(final CuveEntity cuve, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("clients")
                //A REVOIR valueOf
                .child(String.valueOf(cuve.getId()))
                .updateChildren(cuve.toMap(), (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
                    }
                });
    }

    public void delete(final CuveEntity cuve, OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("cuves")
                //VOIR si marche valueOf
                .child(String.valueOf(cuve.getId()))
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
    }
});
    }
}

