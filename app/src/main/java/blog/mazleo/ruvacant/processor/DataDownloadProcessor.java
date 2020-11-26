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
    }

    public void downloadCourses() {
        if (coursesViewModel == null) {
            coursesViewModel = new CoursesViewModel(this);
        }
        coursesViewModel.initializeDownloadCourseData(selectedOptions);
    }

    public void onDownloadCoursesComplete() {
        onDownloadComplete();
    }

    public void onDownloadComplete() {
        dataDownloadProgress.setValue(false);
        onSaveStart();
    }

    public void saveLocations() {
        databaseViewModel.saveLocations(locationsViewModel.getBuildings(), locationsViewModel.getRooms());
    }

    public void onSaveLocationsComplete() {
        saveCourses();
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
    }

    public void onSaveComplete() {
        dataSaveProgress.setValue(false);
        onDataRetrievalComplete();
    }

    public void onDataRetrievalComplete() {
        dataRetrievalProgress.setValue(false);
        // TODO move on

        Log.i("APPDEBUG", "DONE");
    }

    private void onSaveStart() {
        Log.i("APPDEBUG", "SAVE STARTING");
        dataSaveProgress.setValue(true);
        if (databaseViewModel == null) {
            databaseViewModel = new DatabaseViewModel(currentActivity);
        }
        databaseViewModel.setDataDownloadProcessor(this);
        databaseViewModel.setupInitialDatabase(selectedOptions);
    }

    public void onInitialDatabaseSetupComplete() {
        saveLocations();
    }

    private void onDownloadStart() {
        Log.i("APPDEBUG", "DOWNLOAD STARTING");
        dataRetrievalProgress.setValue(true);
        dataDownloadProgress.setValue(true);
    }

    public Option getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(Option selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public void onSaveError(Throwable e) {
        this.onSaveError.setValue(true);
        this.onSaveError.setValue(false);
        this.dataSaveProgress.setValue(false);
        this.dataRetrievalProgress.setValue(false);
        e.printStackTrace();
        cleanUpAllComponents();
    }

    public void onDownloadError(Throwable e) {
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
