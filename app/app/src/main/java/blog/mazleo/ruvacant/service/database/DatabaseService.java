package blog.mazleo.ruvacant.service.database;

import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Interfaces the database. */
@Singleton
public final class DatabaseService {

  private final RuVacantDatabase ruVacantDatabase;
  private final ApplicationStateManager stateManager;
  private final SharedApplicationData sharedApplicationData;

  @Inject
  DatabaseService(
      RuVacantDatabase ruVacantDatabase,
      ApplicationStateManager stateManager,
      SharedApplicationData sharedApplicationData) {
    this.ruVacantDatabase = ruVacantDatabase;
    this.stateManager = stateManager;
    this.sharedApplicationData = sharedApplicationData;
  }

  public boolean hasData() {
    return ruVacantDatabase.getDatabase().buildingDao().getCount() > 0;
  }

  public List<RuBuilding> getAllBuildingsInUniCampus(String uniCampusCode) {
    return ruVacantDatabase.getDatabase().buildingDao().getAllBuildingsInUniCampus(uniCampusCode);
  }

  public List<RuClassroom> getAllClassroomsInBuilding(String buildingCode) {
    return ruVacantDatabase.getDatabase().classroomDao().getAllClassroomsInBuilding(buildingCode);
  }

  public List<RuMeeting> getMeetings(
      String semesterCode, String buildingCode, String roomCode, int dayOfWeek) {
    return ruVacantDatabase
        .getDatabase()
        .meetingDao()
        .getMeetings(semesterCode, buildingCode, roomCode, dayOfWeek);
  }

  public void saveAllData() {
    // TODO: Remove resetting of tables.
    ruVacantDatabase.getDatabase().clearAllTables();

    RuClassInfos classInfos =
        (RuClassInfos) sharedApplicationData.getData(ApplicationData.CLASS_INFOS_CACHE.getTag());
    saveCourses(classInfos);
    saveMeetings(classInfos);
    saveBuildings(classInfos);
    saveClassrooms(classInfos);

    stateManager.exitState(ApplicationState.DATA_SAVING.getState());
    stateManager.enterState(ApplicationState.DATA_SAVED.getState());
  }

  private void saveCourses(RuClassInfos classInfos) {
    RuCourse[] courses = new RuCourse[classInfos.courses.size()];
    classInfos.courses.toArray(courses);
    ruVacantDatabase.getDatabase().courseDao().insertAll(courses);
  }

  private void saveMeetings(RuClassInfos classInfos) {
    RuMeeting[] meetings = new RuMeeting[classInfos.meetings.size()];
    classInfos.meetings.toArray(meetings);
    ruVacantDatabase.getDatabase().meetingDao().insertAll(meetings);
  }

  private void saveBuildings(RuClassInfos classInfos) {
    RuBuilding[] buildings = new RuBuilding[classInfos.buildings.size()];
    classInfos.buildings.toArray(buildings);
    ruVacantDatabase.getDatabase().buildingDao().insertAll(buildings);
  }

  private void saveClassrooms(RuClassInfos classInfos) {
    RuClassroom[] classrooms = new RuClassroom[classInfos.classrooms.size()];
    classInfos.classrooms.toArray(classrooms);
    ruVacantDatabase.getDatabase().classroomDao().insertAll(classrooms);
  }
}
