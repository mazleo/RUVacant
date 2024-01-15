package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import blog.mazleo.ruvacant.service.model.RuCourse;

/** Dao for RuCourse. */
@Dao
public interface RuCourseDao {

  @Insert
  void insertAll(RuCourse... course);
}
