package blog.mazleo.ruvacant.processor;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.viewmodel.CoursesViewModel;
import blog.mazleo.ruvacant.viewmodel.DatabaseViewModel;
import blog.mazleo.ruvacant.viewmodel.LocationsViewModel;

public class DataDownloadProcessor {
    // ----- FIELDS -----
    private Activity currentActivity;
    private Option selectedOptions;
    private String errorMessage;

    // ----- VIEWMODELS -----
    private LocationsViewModel locationsViewModel;
    private CoursesViewModel coursesViewModel;
    private DatabaseViewModel databaseViewModel;

    // ----- UI Components -----
    private MutableLiveData<Boolean> dataRetrievalProgress;
    private MutableLiveData<Boolean> dataDownloadProgress;
    private MutableLiveData<Boolean> dataSaveProgress;
    private MutableLiveData<Boolean> onDownloadError;
    private MutableLiveData<Boolean> onSaveError;

    public DataDownloadProcessor() {}

    public DataDownloadProcessor(Activity currentActivity, Option selectedOptions) {
        this.currentActivity = currentActivity;
        this.selectedOptions = selectedOptions;

        this.locationsViewModel = new LocationsViewModel(this);
        this.coursesViewModel = new CoursesViewModel(this);
        this.databaseViewModel = new DatabaseViewModel(currentActivity);

        this.dataRetrievalProgress = new MutableLiveData<>();
        this.dataDownloadProgress = new MutableLiveData<>();
        this.dataSaveProgress = new MutableLiveData<>();
        this.onDownloadError = new MutableLiveData<>();
        this.onSaveError = new MutableLiveData<>();

        this.dataRetrievalProgress.setValue(false);
        this.dataDownloadProgress.setValue(false);
        this.dataSaveProgress.setValue(false);
        this.onDownloadError.setValue(false);
        this.onSaveError.setValue(false);
    }

    public void initializeDataDownload() {
        onDownloadStart();
        downloadLocations();
    }

    public void downloadLocations() {
        if (locationsViewModel == null) {
            locationsViewModel = new LocationsViewModel(this);
        }
        locationsViewModel.downloadLocations();
    }

    public void onDownloadLocationsComplete() {
        downloadCourses();
        errorMessage = null;
    }

    public void downloadCourses() {
        if (coursesViewModel == null) {
            coursesViewModel = new CoursesViewModel(this);
        }
        coursesViewModel.initializeDownloadCourseData(selectedOptions);
    }

    public void onDownloadCoursesComplete() {
        onDownloadComplete();
        errorMessage = null;
    }

    public void onDownloadComplete() {
        dataDownloadProgress.setValue(false);
        errorMessage = null;
        onSaveStart();
    }

    public void saveLocations() {
        databaseViewModel.saveLocations(locationsViewModel.getBuildings(), locationsViewModel.getRooms());
    }

    public void onSaveLocationsComplete() {
        saveCourses();
        errorMessage = null;
    }

    public void saveCourses() {
        databaseViewModel.saveCourses(
                coursesViewModel.getSubjects(),
                coursesViewModel.getCourses(),
                coursesViewModel.getClasses(),
                coursesViewModel.getInstructors(),
                coursesViewModel.getClassesInstructors(),
                coursesViewModel.getMeetings()
        );
    }

    public void onSaveCoursesComplete() {
        onSaveComplete();
        errorMessage = null;
    }

    public void onSaveComplete() {
        dataSaveProgress.setValue(false);
        onDataRetrievalComplete();
        errorMessage = null;
    }

    public void onDataRetrievalComplete() {
        dataRetrievalProgress.setValue(false);
        errorMessage = null;
        // TODO move on
    }

    private void onSaveStart() {
        dataSaveProgress.setValue(true);
        if (databaseViewModel == null) {
            databaseViewModel = new DatabaseViewModel(currentActivity);
        }
        databaseViewModel.setDataDownloadProcessor(this);
        databaseViewModel.setupInitialDatabase(selectedOptions);
    }

    public void onInitialDatabaseSetupComplete() {
        saveLocations();
        errorMessage = null;
    }

    private void onDownloadStart() {
        dataRetrievalProgress.setValue(true);
        dataDownloadProgress.setValue(true);
    }

    public Option getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(Option selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public void onSaveError(Throwable e, String message) {
        this.errorMessage = message;
        this.onSaveError.setValue(true);
        this.onSaveError.setValue(false);
        this.dataSaveProgress.setValue(false);
        this.dataRetrievalProgress.setValue(false);
        e.printStackTrace();
        cleanUpAllComponents();
    }

    public void onDownloadError(Throwable e, String message) {
        this.errorMessage = message;
        onDownloadError.setValue(true);
        onDownloadError.setValue(false);
        dataDownloadProgress.setValue(false);
        dataRetrievalProgress.setValue(false);
        e.printStackTrace();
        cleanUpAllComponents();
    }

    public void cleanUpAllComponents() {
        cleanUpLocations();
        cleanUpCourses();
        cleanUpDatabase();
    }

    public void cleanUpDatabase() {
        if (this.databaseViewModel != null) {
            this.databaseViewModel.cleanUp();
            this.databaseViewModel = null;
        }
    }

    public void cleanUpLocations() {
        if (locationsViewModel != null) {
            locationsViewModel.cleanUp();
            locationsViewModel = null;
        }
    }

    public void cleanUpCourses() {
        if (coursesViewModel != null) {
            coursesViewModel.cleanUp();
            coursesViewModel = null;
        }
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
