package database;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import database.entity.CuveEntity;

/**
 * Initialiser la base de donnée
 */
public class DatabaseInitializer {

    public static final String TAG = "Database_being";

    /**
     * Création d'une tâche asynchrone
     */
    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addCuve(final AppDatabase db, final int number, final int volume, final String period, final String color, final String variety) {
        CuveEntity cuve = new CuveEntity(number, volume, period, color, variety);
        db.cuveDao().insert(cuve);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.cuveDao().deleteAll();

        addCuve(db,2, 300,"septembre","rouge","pinot rouge");
        addCuve(db,4, 200,"septembre","rouge","humagne rouge");
        addCuve(db,6, 150,"novembre","rouge","syrah");
        addCuve(db,8, 300,"septembre","blanc","pinot blanc");
        addCuve(db,10, 300,"septembre","blanc","fendant");

    }

    /**
     * Création en arrière plan d'une base de données avec les données remplis
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

}
}
