package blog.mazleo.ruvacant.service.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuMeeting;

/** Application Room database. */
@Database(
    entities = {RuCourse.class, RuMeeting.class, RuBuilding.class, RuClassroom.class},
    version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

  public abstract RuCourseDao courseDao();

  public abstract RuMeetingDao meetingDao();

  public abstract RuBuildingDao buildingDao();

  public abstract RuClassroomDao classroomDao();
}
