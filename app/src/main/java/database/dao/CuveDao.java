package database.dao;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import database.entity.CuveEntity;

/**
 * @author oceane
 * Data Access Object
 * Requête SQL pour accéder aux données
 */
@Dao
public interface CuveDao {


    @Query("SELECT * FROM cuves WHERE numero = :number")
    LiveData<CuveEntity> getCuveByNumber(int number);

    @Query("SELECT * FROM cuves")
    LiveData<List<CuveEntity>> getAllCuves();

    @Insert
    void insert(CuveEntity cuve) throws SQLiteConstraintException;

    @Update
    void update(CuveEntity cuve);

    @Delete
    void delete(CuveEntity cuve);

    @Query("DELETE FROM cuves")
    void deleteAll();

}