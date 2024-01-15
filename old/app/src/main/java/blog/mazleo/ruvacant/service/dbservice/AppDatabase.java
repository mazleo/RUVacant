package blog.mazleo.ruvacant.service.dbservice;

import androidx.room.Database;
import androidx.room.RoomDatabase;

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
import blog.mazleo.ruvacant.service.dao.CourseDao;
import blog.mazleo.ruvacant.service.dao.LocationDao;
import blog.mazleo.ruvacant.service.dao.SetupDao;

@Database(
        entities = {
                SchoolCampus.class,
                Level.class,
                Semester.class,
                Option.class,
                Campus.class,
                Building.class,
                Room.class,
                Subject.class,
                Course.class,
                Class.class,
                Meeting.class,
                Instructor.class,
                ClassInstructor.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
        public abstract SetupDao setupDao();
        public abstract CourseDao courseDao();
        public abstract LocationDao locationDao();
}
