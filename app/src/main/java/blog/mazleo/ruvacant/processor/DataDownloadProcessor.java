package blog.mazleo.ruvacant.processor;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.viewmodel.CoursesViewModel;
import blog.mazleo.ruvacant.viewmodel.LocationsViewModel;

public class DataDownloadProcessor {
    // ----- FIELDS -----
    private Activity currentActivity;
    private Option selectedOptions;

    // ----- VIEWMODELS -----
    private LocationsViewModel locationsViewModel;
    private CoursesViewModel coursesViewModel;
    // TODO Create
    // private DatabaseViewModel databaseViewModel;

    // ----- UI Components -----
    private MutableLiveData<Boolean> dataRetrievalProgress;
    private MutableLiveData<Boolean> dataDownloadProgress;
    private MutableLiveData<Boolean> dataSaveProgress;

    public DataDownloadProcessor(Activity currentActivity, Option selectedOptions) {
        this.currentActivity = currentActivity;
        this.selectedOptions = selectedOptions;

        this.locationsViewModel = new LocationsViewModel(this);
        this.coursesViewModel = new CoursesViewModel(this);
        // TODO DBVM
        // this.databaseViewModel = new ...

        this.dataRetrievalProgress = new MutableLiveData<>();
        this.dataDownloadProgress = new MutableLiveData<>();
        this.dataSaveProgress = new MutableLiveData<>();

        this.dataRetrievalProgress.setValue(false);
        this.dataDownloadProgress.setValue(false);
        this.dataSaveProgress.setValue(false);
    }

    public Option getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(Option selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public LocationsViewModel getLocationsViewModel() {
        return locationsViewModel;
    }

    public CoursesViewModel getCoursesViewModel() {
        return coursesViewModel;
    }

    public boolean isDataDownloadInProgress() {
        return this.dataDownloadProgress.getValue();
    }

    public boolean isDataSaveInProgress() {
        return this.dataSaveProgress.getValue();
    }

    public boolean isDataRetrievalInProgress() {
        return this.dataRetrievalProgress.getValue();
    }
}
