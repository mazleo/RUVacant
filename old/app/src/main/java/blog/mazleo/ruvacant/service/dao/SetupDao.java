package blog.mazleo.ruvacant.service.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import blog.mazleo.ruvacant.model.Campus;
import blog.mazleo.ruvacant.model.Level;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.SchoolCampus;
import blog.mazleo.ruvacant.model.Semester;
import io.reactivex.Completable;

@Dao
public interface SetupDao {
    @Insert
    Completable insertLevels(Level... levels);

    @Insert
    Completable insertSchoolCampuses(SchoolCampus... schoolCampuses);

    @Insert
    Completable insertSemesters(Semester... semesters);

    @Insert
    Completable insertOption(Option option);

    @Insert
    Completable insertCampuses(Campus... campuses);
}
