package blog.mazleo.ruvacant.repository;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Campus;
import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Level;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Room;
import blog.mazleo.ruvacant.model.SchoolCampus;
import blog.mazleo.ruvacant.model.Semester;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.service.dbservice.DatabaseService;
import blog.mazleo.ruvacant.utils.OptionsUtil;
import blog.mazleo.ruvacant.viewmodel.DatabaseViewModel;

public class DatabaseRepository {
    private DatabaseViewModel databaseViewModel;
    private DatabaseService databaseService;

    public DatabaseRepository(DatabaseViewModel databaseViewModel, Activity activity) {
        this.databaseViewModel = databaseViewModel;
        this.databaseService = new DatabaseService(this, activity);
    }

    public void setupInitialDatabase(Option option) {
        databaseService.setupInitialDatabase(option, getPopulatedSchoolCampuses(), getPopulatedLevels(), getPopulatedSemesters(), getPopulatedCampuses());
    }

    public void onInitialDatabaseSetupComplete() {
        // TODO Do something on finish
    }

    public void saveLocations(List<Building> buildings, List<Room> rooms) {
        databaseService.saveLocations(buildings, rooms);
    }

    public void onSaveLocationsComplete() {
        // TODO Do something on finish
    }

    public void saveCourses(List<Subject> subjects, List<Course> courses, List<Class> classes, List<Instructor> instructors, List<ClassInstructor> classesInstructors, List<Meeting> meetings) {
        databaseService.saveCourses(subjects, courses, classes, instructors, classesInstructors, meetings);
    }

    public void onSaveCoursesComplete() {
        // TODO Do something on finish
    }

    private List<SchoolCampus> getPopulatedSchoolCampuses() {
        List<SchoolCampus> schoolCampuses = new ArrayList<>();

        schoolCampuses.add(new SchoolCampus("NB", "New Brunswick"));
        schoolCampuses.add(new SchoolCampus("N", "Newark"));
        schoolCampuses.add(new SchoolCampus("CM", "Camden"));

        return schoolCampuses;
    }

    private List<Campus> getPopulatedCampuses() {
        List<Campus> campuses = new ArrayList<>();

        campuses.add(new Campus("BUS", "Busch", "NB"));
        campuses.add(new Campus("CAC", "College Avenue", "NB"));
        campuses.add(new Campus("N", "Newark", "N"));
        campuses.add(new Campus("LIV", "Livingston", "NB"));
        campuses.add(new Campus("D/C", "Douglass/Cook", "NB"));
        campuses.add(new Campus("CM", "Camden", "CM"));

        return campuses;
    }

    private List<Level> getPopulatedLevels() {
        List<Level> levels = new ArrayList<>();

        levels.add(new Level("U", "Undergraduate"));
        levels.add(new Level("G", "Graduate"));

        return levels;
    }

    private List<Semester> getPopulatedSemesters() {
        List<String> semesterOptionsStrings = OptionsUtil.getSemesterOptionsStrings();
        List<Semester> semesters = new ArrayList<>();

        for (String semesterOptionString : semesterOptionsStrings) {
            String[] splitSemester = semesterOptionString.split(" ");
            int semesterMonth = -1;
            int semesterYear = -1;

            semesterYear = Integer.valueOf(splitSemester[1]);

            switch (splitSemester[0]) {
                case "Fall":
                    semesterMonth = 9;
                    break;
                case "Spring":
                    semesterMonth = 1;
                    break;
                case "Winter":
                    semesterMonth = 0;
                    break;
                case "Summer":
                    semesterMonth = 7;
                    break;
            }

            semesters.add(new Semester(semesterMonth, semesterYear));
        }

        return semesters;
    }

    public void onError(Throwable e) {
        databaseViewModel.onError(e);
        cleanUp();
    }

    public void cleanUpKeepData() {
        if (databaseService != null) {
            databaseService.cleanUpKeepData();
            databaseService = null;
        }
        databaseViewModel = null;
    }

    public void cleanUp() {
        if (databaseService != null) {
            databaseService.cleanUp();
            databaseService = null;
        }
        databaseViewModel = null;
    }
}
