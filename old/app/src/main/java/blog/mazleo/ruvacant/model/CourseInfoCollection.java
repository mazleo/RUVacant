package blog.mazleo.ruvacant.model;

import java.util.List;

public class CourseInfoCollection {
    private List<Course> courses;
    private List<Class> classes;
    private List<Instructor> instructors;
    private List<ClassInstructor> classesInstructors;
    private List<Meeting> meetings;

    public CourseInfoCollection(List<Course> courses, List<Class> classes, List<Instructor> instructors, List<ClassInstructor> classesInstructors, List<Meeting> meetings) {
        this.courses = courses;
        this.classes = classes;
        this.instructors = instructors;
        this.classesInstructors = classesInstructors;
        this.meetings = meetings;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public List<ClassInstructor> getClassesInstructors() {
        return classesInstructors;
    }

    public void setClassesInstructors(List<ClassInstructor> classesInstructors) {
        this.classesInstructors = classesInstructors;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
