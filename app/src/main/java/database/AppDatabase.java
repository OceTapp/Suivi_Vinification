package database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

import database.dao.CuveDao;
import database.entity.CuveEntity;


/**
 * Création d'un thread qui exécute une nouvelle instance de base de donnée
 */
@Database(entities = {CuveEntity.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "Database_initialized";

    private static AppDatabase INSTANCE;

    private static final String DATABASE_NAME = "cuve-database";

    public abstract CuveDao cuveDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    /**
     * Création d'une base de donnée si aucune instance existe
     */
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,DATABASE_NAME).build();
                 //   INSTANCE = buildDatabase(context.getApplicationContext());
                 //   INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Création d'un thread qui exécute une nouvelle instance de base de donnée
     * Build the database. only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext) {
        Log.i(TAG, "Database will be initialized.");
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadExecutor().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext);
                            DatabaseInitializer.populateDatabase(database);
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }
        /**
         * initialise la base de donnée si elle existe
         */
        private void updateDatabaseCreated (final Context context){
            if (context.getDatabasePath(DATABASE_NAME).exists()) {
                Log.i(TAG, "Database initialized.");
                setDatabaseCreated();
            }
        }

        private void setDatabaseCreated() {
            isDatabaseCreated.postValue(true);
        }

        public LiveData<Boolean> getDatabaseCreated() {
            return isDatabaseCreated;
        }
    }



