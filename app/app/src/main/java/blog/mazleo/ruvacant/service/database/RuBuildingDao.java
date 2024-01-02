package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import blog.mazleo.ruvacant.service.model.RuBuilding;

/** Dao for RuBuilding. */
@Dao
public interface RuBuildingDao {

  @Insert
  void insertAll(RuBuilding... building);
}
