package blog.mazleo.ruvacant.service.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Room;
import io.reactivex.Completable;

@Dao
public interface LocationDao {
    @Insert
    Completable insertBuildings(Building... buildings);

    @Insert
    Completable insertRooms(Room... rooms);
}
