package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import blog.mazleo.ruvacant.service.model.RuClassroom;

/** Dao for RuClassroom. */
@Dao
public interface RuClassroomDao {

  @Insert
  void insertAll(RuClassroom... classroom);
}
