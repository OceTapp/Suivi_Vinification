package database.async;

import android.content.Context;
import android.os.AsyncTask;

import database.AppDatabase;
import database.entity.CuveEntity;
import database.util.OnAsyncEventListener;

/**
 * @author oceane
 * Suppression d'une cuve
 */
public class DeleteCuve extends AsyncTask<CuveEntity, Void, Void> {
    private AppDatabase database;
    private OnAsyncEventListener callback;
    private Exception exception;

    public DeleteCuve(Context context, OnAsyncEventListener callback) {
        database = AppDatabase.getInstance(context);
        this.callback = callback;
    }

    @Override
    protected Void doInBackground(CuveEntity... params) {
        try {
            for (CuveEntity cuve : params)
                database.cuveDao().delete(cuve);
        } catch (Exception e) {
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            if (exception == null) {
                callback.onSuccess();
            } else {
                callback.onFailure(exception);
            }
        }
    }
}