package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import blog.mazleo.ruvacant.service.model.RuBuilding;
import java.util.List;

/** Dao for RuBuilding. */
@Dao
public interface RuBuildingDao {

  @Insert
  void insertAll(RuBuilding... building);

  @Query("SELECT COUNT(code) FROM RuBuilding")
  int getCount();

  @Query("SELECT * FROM RuBuilding WHERE uniCampusCode = :uniCampusCode")
  List<RuBuilding> getAllBuildingsInUniCampus(String uniCampusCode);
}
