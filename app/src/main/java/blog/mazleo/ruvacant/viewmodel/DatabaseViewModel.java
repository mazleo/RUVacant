package blog.mazleo.ruvacant.viewmodel;

import android.app.Activity;

import java.util.List;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Room;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.processor.DataDownloadProcessor;
import blog.mazleo.ruvacant.repository.DatabaseRepository;

public class DatabaseViewModel {
    private DataDownloadProcessor dataDownloadProcessor;
    private DatabaseRepository databaseRepository;

    public DatabaseViewModel(Activity activity) {
        databaseRepository = new DatabaseRepository(this, activity);
    }

    public void setupInitialDatabase(Option option) {
        databaseRepository.setupInitialDatabase(option);
    }

    public void onInitialDatabaseSetupComplete() {
        dataDownloadProcessor.onInitialDatabaseSetupComplete();
    }

    public void saveLocations(List<Building> buildings, List<Room> rooms) {
        databaseRepository.saveLocations(buildings, rooms);
    }

    public void onSaveLocationsComplete() {
        dataDownloadProcessor.onSaveLocationsComplete();
    }

    public void saveCourses(List<Subject> subjects, List<Course> courses, List<Class> classes, List<Instructor> instructors, List<ClassInstructor> classesInstructors, List<Meeting> meetings) {
        databaseRepository.saveCourses(subjects, courses, classes, instructors, classesInstructors, meetings);
    }

    public void onSaveCoursesComplete() {
        dataDownloadProcessor.onSaveCoursesComplete();
    }

    public void cleanUp() {
        if (databaseRepository != null) {
            databaseRepository.cleanUp();
            databaseRepository = null;
        }
        dataDownloadProcessor = null;
    }

    public void cleanUpKeepData() {
        if (databaseRepository != null) {
            databaseRepository.cleanUpKeepData();
            databaseRepository = null;
        }
        dataDownloadProcessor = null;
    }

    public void onError(Throwable e) {
        dataDownloadProcessor.onSaveError(e);
        cleanUp();
    }

    public void setDataDownloadProcessor(DataDownloadProcessor dataDownloadProcessor) {
        this.dataDownloadProcessor = dataDownloadProcessor;
    }
}
