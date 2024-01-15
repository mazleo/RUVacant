package blog.mazleo.ruvacant.repository;

import java.util.List;

import blog.mazleo.ruvacant.model.Locations;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.service.webservice.LocationsWebService;
import blog.mazleo.ruvacant.service.webservice.SubjectsWebService;
import blog.mazleo.ruvacant.viewmodel.LocationsViewModel;

public class LocationsRepository implements RepositoryInstance {
    private LocationsViewModel locationsViewModel;
    private SubjectsWebService subjectsWebService;
    private LocationsWebService locationsWebService;

    public LocationsRepository(LocationsViewModel locationsViewModel) {
        this.locationsViewModel = locationsViewModel;
    }

    public void initiateLocationsFromRutgersPlacesDownload() {
        locationsWebService = new LocationsWebService(this);
        locationsWebService.downloadLocationsFromRutgersPlaces();
    }

    public void onDownloadLocationsFromRutgersPlacesComplete(Locations locations) {
        locationsViewModel.onLocationsFromRutgersPlacesRetrieved(locations);
        locationsWebService = null;
    }

    public void initiateSubjectsServiceDownload(Option selectedOption) {
        subjectsWebService = new SubjectsWebService(this);
        subjectsWebService.downloadSubjects(selectedOption);
    }

    public void onSubjectsDownloadComplete(List<Subject> subjects) {
        locationsViewModel.onSubjectsRetrieved(subjects);
        subjectsWebService = null;
    }

    public void initiateLocationsFromRutgersCoursesDownload(List<Subject> subjects, Option option) {
        locationsWebService = new LocationsWebService(this);
        locationsWebService.downloadLocationsFromRutgersCourses(subjects, option);
    }

    public void onDownloadLocationsFromRutgersCoursesComplete(Locations locations) {
        locationsViewModel.onLocationsFromRutgersCoursesRetrieved(locations);
        locationsWebService = null;
    }

    public void onError(Throwable e, String message) {
        locationsViewModel.onError(e, message);
        cleanUp();
    }

    public void cleanUp() {
        if (locationsWebService != null) {
            locationsWebService.cleanUp();
            locationsWebService = null;
        }
        if (subjectsWebService != null) {
            subjectsWebService.cleanUp();
            subjectsWebService = null;
        }
        locationsViewModel = null;
    }
}
