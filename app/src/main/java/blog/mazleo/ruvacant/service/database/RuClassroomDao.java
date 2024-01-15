package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import java.util.List;

/** Dao for RuClassroom. */
@Dao
public interface RuClassroomDao {

  @Insert
  void insertAll(RuClassroom... classroom);

  @Query("SELECT * FROM RuClassroom WHERE buildingCode = :buildingCode")
  List<RuClassroom> getAllClassroomsInBuilding(String buildingCode);
}
