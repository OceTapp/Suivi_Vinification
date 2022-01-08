package firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import database.entity.CuveEntity;

public class CuveListLiveData extends LiveData<List<CuveEntity>> {

    private static final String TAG = "CuveAccountsLiveData";

    private final DatabaseReference reference;
    private final MyValueEventListener listener = new MyValueEventListener();

    public CuveListLiveData(DatabaseReference ref) {
        reference = ref;
    }

    @Override
    protected void onActive() {
        Log.d(TAG, "onActive");
        reference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(TAG, "onInactive");
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(toCuveList(dataSnapshot));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "Can't listen to query " + reference, databaseError.toException());
        }
    }

    private List<CuveEntity> toCuveList(DataSnapshot snapshot) {
        List<CuveEntity> cuves = new ArrayList<>();
        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
            CuveEntity entity = childSnapshot.getValue(CuveEntity.class);
            //voir si ça marche
            entity.setId(Long.valueOf(childSnapshot.getKey()));
            cuves.add(entity);
        }
        return cuves;
    }
}

