package database.repository;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;
import database.firebase.CuveListLiveData;
import database.firebase.CuveLiveData;

/**
 * Gestion de toutes les données de l'app dans des instances
 */
public class CuveRepository {

    private static CuveRepository instance;

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

    /**
     * Stucture Firebase d'une cuve spécifique
     */
    public LiveData<CuveEntity> getCuve(final String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference("cuves")
                .child(id);
        return new CuveLiveData(reference);
    }


    /**
     * Structure Firebase de toutes les cuves
     */
    public LiveData<List<CuveEntity>> getAllCuves() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("cuves");
        return new CuveListLiveData(reference);
    }

    public void insert(final CuveEntity cuve, final OnAsyncEventListener callback) {
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

    public void update(final CuveEntity cuve, final OnAsyncEventListener callback) {
        FirebaseDatabase.getInstance()
                .getReference("cuves")
                .child(cuve.getId())
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
                .child(cuve.getId())
                .removeValue((databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        callback.onFailure(databaseError.toException());
                    } else {
                        callback.onSuccess();
    }
});
    }
}