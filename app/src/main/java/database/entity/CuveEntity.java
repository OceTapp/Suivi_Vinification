package database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.Objects;

/**
 * @author oceane
 * Création d'une table nommée cuve et de ses colonnes
 */
@Entity(tableName = "cuves")
public class CuveEntity {

        private int id;

        @ColumnInfo(name = "numero")
        private int number;

        @ColumnInfo(name = "volume")
        private int volume;

        @ColumnInfo(name = "mois")
        private String period;

        @ColumnInfo(name = "couleur")
        private String color;

        @ColumnInfo(name = "cepage")
        private String variety;


        @Ignore
        public CuveEntity() {
        }

        public CuveEntity(int number, int volume, String period, String color, String variety) {
            this.number = number;
            this.volume = volume;
            this.period = period;
            this.color = color;
            this.variety = variety;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (!(obj instanceof CuveEntity)) return false;
            CuveEntity o = (CuveEntity) obj;
            return o.getNumber() == this.getNumber();
        }


        @Override
        public String toString() {
            return number + " " + variety;
        }
    }