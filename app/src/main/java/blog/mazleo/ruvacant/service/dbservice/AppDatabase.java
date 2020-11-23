package blog.mazleo.ruvacant.service.dbservice;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import blog.mazleo.ruvacant.model.Level;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.SchoolCampus;
import blog.mazleo.ruvacant.model.Semester;

@Database(
        entities = {
                SchoolCampus.class,
                Level.class,
                Semester.class,
                Option.class
        },
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
}
