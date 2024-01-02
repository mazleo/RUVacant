package blog.mazleo.ruvacant.service.database;

import androidx.room.Dao;
import androidx.room.Insert;
import blog.mazleo.ruvacant.service.model.RuMeeting;

/** Dao for RuMeeting. */
@Dao
public interface RuMeetingDao {

  @Insert
  void insertAll(RuMeeting... meeting);
}
