package blog.mazleo.ruvacant.viewmodel;

import java.util.ArrayList;
import java.util.List;

import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.processor.DataDownloadProcessor;
import blog.mazleo.ruvacant.repository.CoursesRepository;

public class CoursesViewModel {
    private DataDownloadProcessor dataDownloadProcessor;
    private CoursesRepository coursesRepository;

    private List<Subject> subjects;
    private List<Course> courses;
    private List<Class> classes;
    private List<Instructor> instructors;
    private List<ClassInstructor> classesInstructors;
    private List<Meeting> meetings;

    public CoursesViewModel(DataDownloadProcessor dataDownloadProcessor) {
        this.dataDownloadProcessor = dataDownloadProcessor;
        this.coursesRepository = null;

        this.subjects = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.classes = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.classesInstructors = new ArrayList<>();
        this.meetings = new ArrayList<>();
    }

    public void initializeDownloadCourseData(Option selectedOptions) {
        this.coursesRepository = new CoursesRepository(this, selectedOptions);
        this.coursesRepository.initiateSubjectsServiceDownload();
    }

    public void onSubjectsRetrieved(List<Subject> subjects) {
        appendSubjects(subjects);
    }

    public void onCourseInfosRetrieved(List<CourseInfoCollection> courseInfos) {
        appendCourseInfos(courseInfos);
        onCourseDataDownloadComplete();
    }

    private void onCourseDataDownloadComplete() {
        cleanUpDownloaders();
        // TODO Notify DataDownloadProcessor to start saving data
    }

    private void appendCourseInfos(List<CourseInfoCollection> courseInfos) {
        for (CourseInfoCollection courseInfo : courseInfos) {
            appendCourses(courseInfo);
            appendClasses(courseInfo);
            appendInstructors(courseInfo);
            appendClassesInstructors(courseInfo);
            appendMeetings(courseInfo);
        }
    }

    private void appendMeetings(CourseInfoCollection courseInfo) {
        for (Meeting meeting : courseInfo.getMeetings()) {
            if (!this.meetings.contains(meeting)) {
                this.meetings.add(meeting);
            }
        }
    }

    private void appendClassesInstructors(CourseInfoCollection courseInfo) {
        for (ClassInstructor classInstructor : courseInfo.getClassesInstructors()) {
            if (!this.classesInstructors.contains(classInstructor)) {
                this.classesInstructors.add(classInstructor);
            }
        }
    }

    private void appendInstructors(CourseInfoCollection courseInfo) {
        for (Instructor instructor : courseInfo.getInstructors()) {
            if (!this.instructors.contains(instructor)) {
                this.instructors.add(instructor);
            }
        }
    }

    private void appendClasses(CourseInfoCollection courseInfo) {
        for (Class clazz : courseInfo.getClasses()) {
            if (!this.classes.contains(clazz)) {
                this.classes.add(clazz);
            }
        }
    }

    private void appendCourses(CourseInfoCollection courseInfo) {
        for (Course course : courseInfo.getCourses()) {
            if (!this.courses.contains(course)) {
                this.courses.add(course);
            }
        }
    }

    private void appendSubjects(List<Subject> subjects) {
        for (Subject subject : subjects) {
            if (!this.subjects.contains(subject)) {
                this.subjects.add(subject);
            }
        }
    }

    public void cleanUpDownloaders() {
        if (this.coursesRepository != null) {
            this.coursesRepository.cleanUp();
            this.coursesRepository = null;
        }
    }

    public void cleanUp() {
        dataDownloadProcessor = null;

        cleanUpDownloaders();
        cleanUpCourseData();
    }

    private void cleanUpCourseData() {
        cleanUpSubjects();
        cleanUpCourses();
        cleanUpClasses();
        cleanUpInstructors();
        cleanUpClassesInstructors();
        cleanUpMeetings();
    }

    private void cleanUpMeetings() {
        if (meetings != null) {
            meetings.clear();
            meetings = null;
        }
    }

    private void cleanUpClassesInstructors() {
        if (classesInstructors != null) {
            classesInstructors.clear();
            classesInstructors = null;
        }
    }

    private void cleanUpInstructors() {
        if (instructors != null) {
            instructors.clear();
            instructors = null;
        }
    }

    private void cleanUpClasses() {
        if (classes != null) {
            classes.clear();
            classes = null;
        }
    }

    private void cleanUpCourses() {
        if (courses != null) {
            courses.clear();
            courses = null;
        }
    }

    private void cleanUpSubjects() {
        if (subjects != null) {
            subjects.clear();
            subjects = null;
        }
    }

    public void passError(Throwable e) {
        // TODO
        // dataDownloadProcessor.onDownloadError(e);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public List<ClassInstructor> getClassesInstructors() {
        return classesInstructors;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }
}
