package blog.mazleo.ruvacant.viewmodel;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.processor.DataDownloadProcessor;

@RunWith(AndroidJUnit4.class)
public class CoursesViewModelTest {
    @Test
    public void downloadCourseDataTest() {
        CoursesViewModel coursesViewModel = new CoursesViewModel(new DataDownloadProcessor());

        coursesViewModel.initializeDownloadCourseData(new Option(
                9,
                2019,
                "NB",
                "U"
        ));

        final int NUM_SECONDS_WAIT = 60;
        for (int s = 0; s < NUM_SECONDS_WAIT; s++) {
            try {
                Thread.sleep(1000);
                Log.i("APPDEBUG", "SEC: " + (s + 1));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
        }

        List<Subject> subjects = coursesViewModel.getSubjects();
        List<Course> courses = coursesViewModel.getCourses();
        List<Class> classes = coursesViewModel.getClasses();
        List<Instructor> instructors = coursesViewModel.getInstructors();
        List<ClassInstructor> classesInstructors = coursesViewModel.getClassesInstructors();
        List<Meeting> meetings = coursesViewModel.getMeetings();

        Assert.assertNotNull(subjects);
        Assert.assertNotNull(courses);
        Assert.assertNotNull(classes);
        Assert.assertNotNull(instructors);
        Assert.assertNotNull(classesInstructors);
        Assert.assertNotNull(meetings);

//        for (Subject subject : subjects) {
//            Log.i("APPDEBUG", subject.toString());
//        }
//        for (Course course : courses) {
//            Log.i("APPDEBUG", course.toString());
//        }
//        for (Class clazz : classes) {
//            Log.i("APPDEBUG", clazz.toString());
//        }
//        for (Instructor instructor : instructors) {
//            Log.i("APPDEBUG", instructor.toString());
//        }
//        for (ClassInstructor classInstructor : classesInstructors) {
//            Log.i("APPDEBUG", classInstructor.toString());
//        }
//        for (Meeting meeting : meetings) {
//            Log.i("APPDEBUG", meeting.toString());
//        }

        Assert.assertThat(subjects.size(), Matchers.greaterThan(0));
        Assert.assertThat(courses.size(), Matchers.greaterThan(0));
        Assert.assertThat(classes.size(), Matchers.greaterThan(0));
        Assert.assertThat(instructors.size(), Matchers.greaterThan(0));
        Assert.assertThat(classesInstructors.size(), Matchers.greaterThan(0));
        Assert.assertThat(meetings.size(), Matchers.greaterThan(0));

        assertAllSubjectDataValid(subjects);
        assertAllCourseDataValid(courses);
        assertAllClassDataValid(classes);
        assertAllInstructorDataValid(instructors);
        assertAllClassInstructorDataValid(classesInstructors);
        assertAllMeetingDataValid(meetings);
        assertAllMeetingTimesValid(meetings);
    }

    private void assertAllMeetingDataValid(List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            Assert.assertNotNull(meeting.getClassIndex());
            Assert.assertNotNull(meeting.getBuildingCode());
            Assert.assertNotNull(meeting.getRoomNumber());
            Assert.assertNotNull(meeting.getMeetingDay());

            Assert.assertThat(meeting.getClassIndex().length(), Matchers.greaterThan(0));
            Assert.assertThat(meeting.getBuildingCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(meeting.getRoomNumber().length(), Matchers.greaterThan(0));
            Assert.assertThat(meeting.getMeetingDay().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllClassInstructorDataValid(List<ClassInstructor> classesInstructors) {
        for (ClassInstructor classInstructor : classesInstructors) {
            Assert.assertNotNull(classInstructor.getClassIndex());
            Assert.assertNotNull(classInstructor.getInstructorLastName());

            Assert.assertThat(classInstructor.getClassIndex().length(), Matchers.greaterThan(0));
            Assert.assertThat(classInstructor.getInstructorLastName().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllInstructorDataValid(List<Instructor> instructors) {
        for (Instructor instructor : instructors) {
            Assert.assertNotNull(instructor.getLastName());
            Assert.assertNotNull(instructor.getFirstName());

            Assert.assertThat(instructor.getLastName().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllClassDataValid(List<Class> classes) {
        for (Class clazz : classes) {
            Assert.assertNotNull(clazz.getIndex());
            Assert.assertNotNull(clazz.getCode());
            Assert.assertNotNull(clazz.getSubjectCode());
            Assert.assertNotNull(clazz.getCourseCode());

            Assert.assertThat(clazz.getIndex().length(), Matchers.greaterThan(0));
            Assert.assertThat(clazz.getCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(clazz.getSubjectCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(clazz.getCourseCode().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllCourseDataValid(List<Course> courses) {
        for (Course course : courses) {
            Assert.assertNotNull(course.getSubjectCode());
            Assert.assertNotNull(course.getCode());
            Assert.assertNotNull(course.getTitle());

            Assert.assertThat(course.getSubjectCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(course.getCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(course.getTitle().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllSubjectDataValid(List<Subject> subjects) {
        for (Subject subject : subjects) {
            Assert.assertNotNull(subject.getCode());
            Assert.assertNotNull(subject.getTitle());
            Assert.assertThat(subject.getCode().length(), Matchers.greaterThan(0));
            Assert.assertThat(subject.getTitle().length(), Matchers.greaterThan(0));
        }
    }

    private void assertAllMeetingTimesValid(List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            Assert.assertThat(meeting.getStartTime(), Matchers.greaterThan(0));
            Assert.assertThat(meeting.getEndTime(), Matchers.greaterThan(0));
            Assert.assertThat(meeting.getEndTime(), Matchers.greaterThan(meeting.getStartTime()));
        }
    }
}
