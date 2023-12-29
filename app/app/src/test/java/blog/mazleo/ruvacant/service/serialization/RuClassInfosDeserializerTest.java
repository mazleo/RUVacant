package blog.mazleo.ruvacant.service.serialization;

import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertIsJsonArray;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertIsJsonObject;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertIsMemberString;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;

/** Test cases for the RuClassInfosDeserializer. */
public final class RuClassInfosDeserializerTest {
  @Test
  public void deserialize_expectedStructure() {
    String jsonString =
        "[{\"subjectNotes\":null,\"courseNumber\":\"107\",\"subject\":\"198\",\"campusCode\":\"NB\",\"openSections\":2,\"synopsisUrl\":\"http://www.cs.rutgers.edu/undergraduate/courses/\",\"subjectGroupNotes\":null,\"offeringUnitCode\":\"01\",\"offeringUnitTitle\":null,\"title\":\"COMPUT"
            + " MATH & SCIENC\",\"courseDescription\":null,\"preReqNotes\":\"(01:640:135 ) OR"
            + " (01:640:151 ) OR (01:640:153 ) OR (01:640:191"
            + " )\",\"sections\":[{\"sectionEligibility\":null,\"sessionDatePrintIndicator\":\"N\",\"examCode\":\"A\",\"specialPermissionAddCode\":null,\"crossListedSections\":[],\"sectionNotes\":null,\"specialPermissionDropCode\":null,\"instructors\":[{\"name\":\"MOTTO,"
            + " DOUGLAS\"}],\"number\":\"01\",\"majors\":[],\"sessionDates\":null,\"specialPermissionDropCodeDescription\":null,\"subtopic\":null,\"openStatus\":true,\"comments\":[{\"code\":\"35\",\"description\":\"See"
            + " online schedule for"
            + " prereqs\"}],\"minors\":[],\"campusCode\":\"NB\",\"index\":\"06561\",\"unitMajors\":[],\"printed\":\"Y\",\"specialPermissionAddCodeDescription\":null,\"subtitle\":null,\"meetingTimes\":[{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"117\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"T\",\"buildingCode\":\"SEC\",\"startTime\":\"0200\",\"endTime\":\"0320\",\"meetingModeDesc\":\"LEC\",\"meetingModeCode\":\"02\"},{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"209\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"TH\",\"buildingCode\":\"SEC\",\"startTime\":\"0745\",\"endTime\":\"0840\",\"meetingModeDesc\":\"RECIT\",\"meetingModeCode\":\"03\"},{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"117\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"TH\",\"buildingCode\":\"SEC\",\"startTime\":\"0200\",\"endTime\":\"0320\",\"meetingModeDesc\":\"LEC\",\"meetingModeCode\":\"02\"}],\"legendKey\":null,\"honorPrograms\":[]},{\"sectionEligibility\":null,\"sessionDatePrintIndicator\":\"N\",\"examCode\":\"A\",\"specialPermissionAddCode\":null,\"crossListedSections\":[],\"sectionNotes\":null,\"specialPermissionDropCode\":null,\"instructors\":[{\"name\":\"MOTTO,"
            + " DOUGLAS\"}],\"number\":\"02\",\"majors\":[],\"sessionDates\":null,\"specialPermissionDropCodeDescription\":null,\"subtopic\":null,\"openStatus\":true,\"comments\":[],\"minors\":[],\"campusCode\":\"NB\",\"index\":\"06562\",\"unitMajors\":[],\"printed\":\"Y\",\"specialPermissionAddCodeDescription\":null,\"subtitle\":null,\"meetingTimes\":[{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"117\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"T\",\"buildingCode\":\"SEC\",\"startTime\":\"0200\",\"endTime\":\"0320\",\"meetingModeDesc\":\"LEC\",\"meetingModeCode\":\"02\"},{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"117\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"TH\",\"buildingCode\":\"SEC\",\"startTime\":\"0200\",\"endTime\":\"0320\",\"meetingModeDesc\":\"LEC\",\"meetingModeCode\":\"02\"},{\"campusLocation\":\"2\",\"baClassHours\":null,\"roomNumber\":\"209\",\"pmCode\":\"P\",\"campusAbbrev\":\"BUS\",\"campusName\":\"BUSCH\",\"meetingDay\":\"TH\",\"buildingCode\":\"SEC\",\"startTime\":\"0555\",\"endTime\":\"0650\",\"meetingModeDesc\":\"RECIT\",\"meetingModeCode\":\"03\"}],\"legendKey\":null,\"honorPrograms\":[]}],\"supplementCode\":\""
            + " \",\"credits\":4,\"unitNotes\":null,\"coreCodes\":[{\"id\":\"2024101198107"
            + " 21\",\"year\":\"2024\",\"description\":\"Information Technology and"
            + " Research\",\"subject\":\"198\",\"code\":\"ITR\",\"unit\":\"01\",\"offeringUnitCode\":\"01\",\"coreCode\":\"ITR\",\"coreCodeDescription\":\"Information"
            + " Technology and"
            + " Research\",\"lastUpdated\":1468423768000,\"course\":\"107\",\"offeringUnitCampus\":\"NB\",\"supplement\":\""
            + " \",\"effective\":\"20241\",\"coreCodeReferenceId\":\"21\",\"term\":\"1\"},{\"id\":\"2024101198107"
            + " 24\",\"year\":\"2024\",\"description\":\"Mathematical or Formal"
            + " Reasoning\",\"subject\":\"198\",\"code\":\"QR\",\"unit\":\"01\",\"offeringUnitCode\":\"01\",\"coreCode\":\"QR\",\"coreCodeDescription\":\"Mathematical"
            + " or Formal"
            + " Reasoning\",\"lastUpdated\":1468423795000,\"course\":\"107\",\"offeringUnitCampus\":\"NB\",\"supplement\":\""
            + " \",\"effective\":\"20241\",\"coreCodeReferenceId\":\"24\",\"term\":\"1\"}],\"courseNotes\":null,\"expandedTitle\":\"COMPUTING"
            + " FOR MATH AND THE SCIENCES \"}]";
    Gson gson = new Gson();
    JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
    JsonObject courseObject = assertIsJsonObject(jsonArray.get(0));
    assertIsMemberString(courseObject, "campusCode");
    assertIsMemberString(courseObject, "courseNumber");
    assertIsMemberString(courseObject, "title");
    assertIsMemberString(courseObject, "expandedTitle");
    assertIsMemberString(courseObject, "subject");
    JsonArray sectionArray = assertIsJsonArray(courseObject.get("sections"));
    JsonObject sectionObject = assertIsJsonObject(sectionArray.get(0));
    assertIsMemberString(sectionObject, "number");
    JsonArray meetingTimesArray = assertIsJsonArray(sectionObject.get("meetingTimes"));
    JsonObject meetingTimeObject = assertIsJsonObject(meetingTimesArray.get(0));
    assertIsMemberString(meetingTimeObject, "roomNumber");
    assertIsMemberString(meetingTimeObject, "pmCode");
    assertIsMemberString(meetingTimeObject, "campusName");
    assertIsMemberString(meetingTimeObject, "meetingDay");
    assertIsMemberString(meetingTimeObject, "buildingCode");
    assertIsMemberString(meetingTimeObject, "startTime");
    assertIsMemberString(meetingTimeObject, "endTime");
    RuClassInfosDeserializer deserializer = new RuClassInfosDeserializer();
    RuClassInfos classInfos =
        deserializer.deserialize(jsonArray, /* typeOfT= */ null, /* context= */ null);
    List<RuCourse> courses = assertNotNull(classInfos.courses);
    assertNotNull(courses.get(0).key);
    assertNotNull(courses.get(0).code);
    assertNotNull(courses.get(0).title);
    assertNotNull(courses.get(0).subjectCode);
    assertNotNull(courses.get(0).uniCampusCode);
    List<RuMeeting> meetings = assertNotNull(classInfos.meetings);
    assertNotNull(meetings.get(0).key);
    assertEquals(840, meetings.get(0).start);
    assertEquals(920, meetings.get(0).end);
    assertEquals(Calendar.TUESDAY, meetings.get(0).dayOfWeek);
    assertNotNull(meetings.get(0).sectionCode);
    assertNotNull(meetings.get(0).courseKey);
    assertNotNull(meetings.get(0).roomCode);
    assertNotNull(meetings.get(0).buildingCode);
    assertNotNull(meetings.get(0).uniCampusCode);
    List<RuBuilding> buildings = assertNotNull(classInfos.buildings);
    assertNotNull(buildings.get(0).code);
    assertNotNull(buildings.get(0).campusName);
    assertNotNull(buildings.get(0).uniCampusCode);
    List<RuClassroom> classRooms = assertNotNull(classInfos.classrooms);
    assertNotNull(classRooms.get(0).key);
    assertNotNull(classRooms.get(0).code);
    assertNotNull(classRooms.get(0).buildingCode);
    assertNotNull(classRooms.get(0).campusName);
    assertNotNull(classRooms.get(0).uniCampusCode);
  }

  @Test
  public void deserialize_null() {
    JsonNull jsonNull = JsonNull.INSTANCE;
    RuClassInfosDeserializer deserializer = new RuClassInfosDeserializer();
    Object object = deserializer.deserialize(jsonNull, /* typeOfT= */ null, /* context= */ null);
    assertNull(object);
  }
}
