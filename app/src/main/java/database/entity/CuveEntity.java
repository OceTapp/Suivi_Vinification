package database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author oceane
 * Création d'une table nommée cuve et de ses colonnes
 */
public class CuveEntity {

        private String id;
        private int number;
        private int volume;
        private String period;
        private String color;
        private String variety;

        public CuveEntity() {
        }

        @Exclude
        public String getId() {
        return id;
    }

    public void setId(String id) {
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

    /**
     * Utiliser pour mettre à jour
     */
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("number", number);
            result.put("volume", volume);
            result.put("period", period);
            result.put("color", color);
            result.put("variety", variety);
            return result;
        }

}
