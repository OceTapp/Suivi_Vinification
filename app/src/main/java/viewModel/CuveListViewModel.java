package viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import database.entity.CuveEntity;
import database.repository.CuveRepository;

public class CuveListViewModel extends AndroidViewModel {

    private CuveRepository repository;

    private Context applicationContext;

    private final MediatorLiveData<List<CuveEntity>> observableCuves;

    public CuveListViewModel(@NonNull Application application, CuveRepository cuveRepository) {
        super(application);

        repository = cuveRepository;

        applicationContext = application.getApplicationContext();

        observableCuves = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCuves.setValue(null);

        LiveData<List<CuveEntity>> cuves = repository.getAllCuves(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableCuves.addSource(cuves, observableCuves::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final CuveRepository cuveRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            cuveRepository = CuveRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new CuveListViewModel(application, cuveRepository);
        }
    }

    public LiveData<List<CuveEntity>> getCuves() {
        return observableCuves;
    }
}
