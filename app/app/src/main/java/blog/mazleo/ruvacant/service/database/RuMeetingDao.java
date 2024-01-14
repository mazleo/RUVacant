package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import java.util.List;

/** Dao for RuMeeting. */
@Dao
public interface RuMeetingDao {

  @Insert
  void insertAll(RuMeeting... meeting);

  @Query(
      "SELECT * FROM RuMeeting WHERE semesterCode = :semesterCode AND buildingCode = :buildingCode"
          + " AND roomCode = :roomCode AND dayOfWeek = :dayOfWeek")
  List<RuMeeting> getMeetings(
      String semesterCode, String buildingCode, String roomCode, int dayOfWeek);
}
