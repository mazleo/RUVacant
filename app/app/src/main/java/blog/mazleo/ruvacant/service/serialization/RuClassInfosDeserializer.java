package blog.mazleo.ruvacant.service.serialization;

import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getLowerCaseStringOrNull;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getStringOrNull;

import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.model.RuClassRoom;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** JsonDeserializer for RuCourses. */
public final class RuClassInfosDeserializer implements JsonDeserializer<RuClassInfos> {

  @Override
  public RuClassInfos deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    if (json.isJsonNull()) {
      return null;
    }
    List<RuCourse> courses = new ArrayList<>();
    List<RuMeeting> meetings = new ArrayList<>();
    List<RuBuilding> buildings = new ArrayList<>();
    List<RuClassRoom> classRooms = new ArrayList<>();
    json.getAsJsonArray()
        .iterator()
        .forEachRemaining(
            courseJsonElement -> {
              JsonObject courseObject = courseJsonElement.getAsJsonObject();
              String courseCode = getStringOrNull(courseObject, "courseNumber");
              String title = getLowerCaseStringOrNull(courseObject, "title");
              String expandedTitle = getLowerCaseStringOrNull(courseObject, "expandedTitle");
              String subjectCode = getStringOrNull(courseObject, "subject");
              String uniCampusCode = getStringOrNull(courseObject, "campusCode");
              courses.add(
                  new RuCourse(courseCode, title, expandedTitle, subjectCode, uniCampusCode));
              collectFromSections(courseObject, uniCampusCode, meetings, buildings, classRooms);
            });
    return new RuClassInfos(courses, meetings, buildings, classRooms);
  }

  private void collectFromSections(
      JsonObject courseObject,
      String uniCampusCode,
      List<RuMeeting> meetings,
      List<RuBuilding> buildings,
      List<RuClassRoom> classRooms) {
    if (courseObject.get("sections").isJsonNull()) {
      return;
    }
    courseObject
        .get("sections")
        .getAsJsonArray()
        .iterator()
        .forEachRemaining(
            sectionJsonElement -> {
              JsonObject sectionObject = sectionJsonElement.getAsJsonObject();
              String sectionCode = getStringOrNull(sectionObject, "number");
              collectFromMeetingTimes(
                  sectionObject, sectionCode, uniCampusCode, meetings, buildings, classRooms);
            });
  }

  private void collectFromMeetingTimes(
      JsonObject sectionObject,
      String sectionCode,
      String uniCampusCode,
      List<RuMeeting> meetings,
      List<RuBuilding> buildings,
      List<RuClassRoom> classRooms) {
    if (sectionObject.get("meetingTimes").isJsonNull()) {
      return;
    }
    sectionObject
        .get("meetingTimes")
        .getAsJsonArray()
        .iterator()
        .forEachRemaining(
            meetingJsonElement -> {
              JsonObject meetingObject = meetingJsonElement.getAsJsonObject();
              String roomCode = getStringOrNull(meetingObject, "roomNumber");
              String pmCode = getStringOrNull(meetingObject, "pmCode");
              String campusName = getLowerCaseStringOrNull(meetingObject, "campusName");
              String meetingDay = getStringOrNull(meetingObject, "meetingDay");
              String buildingCode = getStringOrNull(meetingObject, "buildingCode");
              String start = getStringOrNull(meetingObject, "startTime");
              String end = getStringOrNull(meetingObject, "endTime");
              if (buildingCode != null) {
                buildings.add(
                    new RuBuilding(buildingCode, /* name= */ null, campusName, uniCampusCode));
                classRooms.add(new RuClassRoom(roomCode, buildingCode, campusName, uniCampusCode));
                if (start != null || end != null) {
                  meetings.add(
                      new RuMeeting(
                          start,
                          end,
                          pmCode,
                          meetingDay,
                          sectionCode,
                          roomCode,
                          buildingCode,
                          uniCampusCode));
                }
              }
            });
  }
}
