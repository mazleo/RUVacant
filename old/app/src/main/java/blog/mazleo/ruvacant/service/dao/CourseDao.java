package blog.mazleo.ruvacant.service.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.model.Subject;
import io.reactivex.Completable;

@Dao
public interface CourseDao {
    @Insert
    Completable insertSubjects(Subject... subjects);

    @Insert
    Completable insertCourses(Course... courses);

    @Insert
    Completable insertClasses(Class... classes);

    @Insert
    Completable insertInstructors(Instructor... instructors);

    @Insert
    Completable insertClassesInstructors(ClassInstructor... classesInstructors);

    @Insert
    Completable insertMeetings(Meeting... meetings);
}
