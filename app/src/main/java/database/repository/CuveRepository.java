package database.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import database.AppDatabase;
import database.async.CreateCuve;
import database.async.DeleteCuve;
import database.async.UpdateCuve;
import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;

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

    public LiveData<CuveEntity> getCuve(Context context,final int number) {
        return AppDatabase.getInstance(context).cuveDao().getCuveByNumber(number);
    }


    //Méthode qui liste l'ensemble des cuves en utilisant la requête indiqué dans DAO
    public LiveData<List<CuveEntity>> getAllCuves(Context context) {
        return AppDatabase.getInstance(context).cuveDao().getAllCuves();
    }

    /**
     * @param cuve : entité cuve
     * @param callback : indique si l'évènement est réussi ou non
     * @param context : regarde si le contexte est juste
     */
    public void insert(final CuveEntity cuve, OnAsyncEventListener callback, Context context) {
        new CreateCuve(context, callback).execute(cuve);
    }

    public void update(final CuveEntity cuve, OnAsyncEventListener callback, Context context) {
        new UpdateCuve(context, callback).execute(cuve);
    }

    public void delete(final CuveEntity cuve, OnAsyncEventListener callback, Context context) {
        new DeleteCuve(context, callback).execute(cuve);
    }
}
