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
    private MutableLiveData<Boolean> dataLocationsDownloadProgress;
    private MutableLiveData<Boolean> dataCoursesDownloadProgress;
    private MutableLiveData<Boolean> dataSaveProgress;
    private MutableLiveData<Boolean> dataLocationsSaveProgress;
    private MutableLiveData<Boolean> dataCoursesSaveProgress;
    private MutableLiveData<Boolean> onDownloadError;
    private MutableLiveData<Boolean> onSaveError;

    public DataDownloadProcessor() {}

    public DataDownloadProcessor(Activity currentActivity, Option selectedOptions) {
        this.currentActivity = currentActivity;
        this.selectedOptions = selectedOptions;

        initializeAllComponents(currentActivity);
        constructLiveDatas();
        initializeLiveDatas();
    }

    private void initializeLiveDatas() {
        this.dataRetrievalProgress.setValue(false);
        this.dataDownloadProgress.setValue(false);
        this.dataLocationsDownloadProgress.setValue(false);
        this.dataCoursesDownloadProgress.setValue(false);
        this.dataSaveProgress.setValue(false);
        this.dataLocationsSaveProgress.setValue(false);
        this.dataCoursesSaveProgress.setValue(false);
        this.onDownloadError.setValue(false);
        this.onSaveError.setValue(false);
    }

    private void constructLiveDatas() {
        this.dataRetrievalProgress = new MutableLiveData<>();
        this.dataDownloadProgress = new MutableLiveData<>();
        this.dataLocationsDownloadProgress = new MutableLiveData<>();
        this.dataCoursesDownloadProgress = new MutableLiveData<>();
        this.dataSaveProgress = new MutableLiveData<>();
        this.dataLocationsSaveProgress = new MutableLiveData<>();
        this.dataCoursesSaveProgress = new MutableLiveData<>();
        this.onDownloadError = new MutableLiveData<>();
        this.onSaveError = new MutableLiveData<>();
    }

    private void initializeAllComponents(Activity currentActivity) {
        this.locationsViewModel = new LocationsViewModel(this);
        this.coursesViewModel = new CoursesViewModel(this);
        this.databaseViewModel = new DatabaseViewModel(currentActivity);
    }

    public void initializeDataDownload() {
        onDownloadStart();
        downloadLocations();
    }

    private void onDownloadStart() {
        dataRetrievalProgress.setValue(true);
        dataDownloadProgress.setValue(true);
    }

    public void downloadLocations() {
        dataLocationsDownloadProgress.setValue(true);
        if (locationsViewModel == null) {
            locationsViewModel = new LocationsViewModel(this);
        }
        locationsViewModel.downloadLocations();
    }

    public void onDownloadLocationsComplete() {
        dataLocationsDownloadProgress.setValue(false);
        downloadCourses();
        errorMessage = null;
    }

    public void downloadCourses() {
        dataCoursesDownloadProgress.setValue(true);
        if (coursesViewModel == null) {
            coursesViewModel = new CoursesViewModel(this);
        }
        coursesViewModel.initializeDownloadCourseData(selectedOptions);
    }

    public void onDownloadCoursesComplete() {
        dataCoursesDownloadProgress.setValue(false);
        onDownloadComplete();
        errorMessage = null;
    }

    public void onDownloadComplete() {
        dataDownloadProgress.setValue(false);
        errorMessage = null;
        onSaveStart();
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

    public void saveLocations() {
        dataLocationsSaveProgress.setValue(true);
        databaseViewModel.saveLocations(locationsViewModel.getBuildings(), locationsViewModel.getRooms());
    }

    public void onSaveLocationsComplete() {
        dataLocationsSaveProgress.setValue(false);
        saveCourses();
        errorMessage = null;
    }

    public void saveCourses() {
        dataCoursesSaveProgress.setValue(true);
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
        dataCoursesSaveProgress.setValue(false);
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

    public MutableLiveData<Boolean> getDataRetrievalProgress() {
        return dataRetrievalProgress;
    }

    public MutableLiveData<Boolean> getDataDownloadProgress() {
        return dataDownloadProgress;
    }

    public MutableLiveData<Boolean> getDataLocationsDownloadProgress() {
        return dataLocationsDownloadProgress;
    }

    public MutableLiveData<Boolean> getDataCoursesDownloadProgress() {
        return dataCoursesDownloadProgress;
    }

    public MutableLiveData<Boolean> getDataSaveProgress() {
        return dataSaveProgress;
    }

    public MutableLiveData<Boolean> getDataLocationsSaveProgress() {
        return dataLocationsSaveProgress;
    }

    public MutableLiveData<Boolean> getDataCoursesSaveProgress() {
        return dataCoursesSaveProgress;
    }

    public MutableLiveData<Boolean> getOnDownloadError() {
        return onDownloadError;
    }

    public MutableLiveData<Boolean> getOnSaveError() {
        return onSaveError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
