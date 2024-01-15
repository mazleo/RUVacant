package blog.mazleo.ruvacant.service.model;

import java.util.List;

/** A wrapper class containing data retrieved from a single subject. */
public final class RuClassInfos {

  public List<RuCourse> courses;
  public List<RuMeeting> meetings;
  public List<RuBuilding> buildings;
  public List<RuClassroom> classrooms;

  public RuClassInfos(
      List<RuCourse> courses,
      List<RuMeeting> meetings,
      List<RuBuilding> buildings,
      List<RuClassroom> classrooms) {
    this.courses = courses;
    this.meetings = meetings;
    this.buildings = buildings;
    this.classrooms = classrooms;
  }
}
