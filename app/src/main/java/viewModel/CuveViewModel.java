package viewModel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import database.entity.CuveEntity;
import database.repository.CuveRepository;
import database.util.OnAsyncEventListener;

/**
 * Utiliser pour montrer les détails d'une cuve
 * 1. observe les objets
 * 2. pattern factory
 * 3. Appel les méthodes de repository
 */
public class CuveViewModel extends AndroidViewModel {

    private CuveRepository repository;

    private Context applicationContext;

    // MediatorLiveData observe le comportement des données et réagit en conséquence
    private final MediatorLiveData<CuveEntity> observableCuve;

    public CuveViewModel(@NonNull Application application,final int number,
                           CuveRepository cuveRepository) {
        super(application);

        repository = cuveRepository;

        applicationContext = application.getApplicationContext();

        observableCuve = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableCuve.setValue(null);

        LiveData<CuveEntity> cuve = repository.getCuve(number,applicationContext);

        // observe the changes of the cuve entity from the database and forward them
        observableCuve.addSource(cuve, observableCuve::setValue);
    }

    /**
     * A COMPRENDRE (Pattern factory)
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int number;

        private final CuveRepository repository;

        public Factory(@NonNull Application application, int cuveNumber) {
            this.application = application;
            this.number = cuveNumber;
            repository = CuveRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CuveViewModel(application, number, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
     */
    public LiveData<CuveEntity> getCuve() {
        return observableCuve;
    }

    public void createCuve(CuveEntity cuve, OnAsyncEventListener callback) {
        repository.insert(cuve, callback, applicationContext);
    }

    public void updateClient(CuveEntity cuve, OnAsyncEventListener callback) {
        repository.update(cuve, callback, applicationContext);
    }

    public void deleteClient(CuveEntity cuve, OnAsyncEventListener callback) {
        repository.delete(cuve, callback, applicationContext);
    }
}
