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

        try {
            Thread.sleep(60000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return;
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
    }
}
