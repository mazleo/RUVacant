package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import org.junit.Test;

/** Test cases for RuMeeting. */
public final class RuMeetingTest {
  @Test
  public void construct_expectedValues() {
    int expectedStart = 540;
    int expectedEnd = 640;
    int expectedDayOfWeek = Calendar.WEDNESDAY;
    String expectedSectionCode = "17";
    String expectedCourseKey = "206.386";
    String expectedRoomCode = "27";
    String expectedBuildingCode = "SEC";
    String expectedUniCampusCode = "NWK";
    RuMeeting meeting =
        new RuMeeting(
            /* ruStart= */ "0900",
            /* ruEnd= */ "1040",
            /* pmCode= */ "A",
            /* ruMeetingDay= */ "W",
            expectedSectionCode,
            expectedCourseKey,
            expectedRoomCode,
            expectedBuildingCode,
            /* semesterCode= */ null,
            expectedUniCampusCode,
            /* levelCode= */ null);
    assertEquals(expectedStart, meeting.start);
    assertEquals(expectedEnd, meeting.end);
    assertEquals(expectedDayOfWeek, meeting.dayOfWeek);
    assertEquals(expectedSectionCode, meeting.sectionCode);
    assertEquals(expectedCourseKey, meeting.courseKey);
    assertEquals(expectedRoomCode, meeting.roomCode);
    assertEquals(expectedBuildingCode, meeting.buildingCode);
    assertEquals(expectedUniCampusCode, meeting.uniCampusCode);
  }

  @Test
  public void construct_pmTime() {
    int expectedStart = 780;
    int expectedEnd = 840;
    int expectedDayOfWeek = Calendar.MONDAY;
    String expectedSectionCode = null;
    String expectedCourseKey = null;
    String expectedRoomCode = null;
    String expectedBuildingCode = null;
    String expectedUniCampusCode = null;
    RuMeeting meeting =
        new RuMeeting(
            /* ruStart= */ "0100",
            /* ruEnd= */ "0200",
            /* pmCode= */ "P",
            /* ruMeetingDay= */ "M",
            expectedSectionCode,
            expectedCourseKey,
            expectedRoomCode,
            expectedBuildingCode,
            /* semesterCode= */ null,
            expectedUniCampusCode,
            /* levelCode= */ null);
    assertEquals(expectedStart, meeting.start);
    assertEquals(expectedEnd, meeting.end);
    assertEquals(expectedDayOfWeek, meeting.dayOfWeek);
    assertEquals(expectedSectionCode, meeting.sectionCode);
    assertEquals(expectedCourseKey, meeting.courseKey);
    assertEquals(expectedRoomCode, meeting.roomCode);
    assertEquals(expectedBuildingCode, meeting.buildingCode);
    assertEquals(expectedUniCampusCode, meeting.uniCampusCode);
  }

  @Test
  public void construct_AmPmTime() {
    int expectedStart = 690;
    int expectedEnd = 810;
    int expectedDayOfWeek = Calendar.FRIDAY;
    String expectedSectionCode = null;
    String expectedCourseKey = null;
    String expectedRoomCode = null;
    String expectedBuildingCode = null;
    String expectedUniCampusCode = null;
    RuMeeting meeting =
        new RuMeeting(
            /* ruStart= */ "1130",
            /* ruEnd= */ "0130",
            /* pmCode= */ "A",
            /* ruMeetingDay= */ "F",
            expectedSectionCode,
            expectedCourseKey,
            expectedRoomCode,
            expectedBuildingCode,
            /* semesterCode= */ null,
            expectedUniCampusCode,
            /* levelCode= */ null);
    assertEquals(expectedStart, meeting.start);
    assertEquals(expectedEnd, meeting.end);
    assertEquals(expectedDayOfWeek, meeting.dayOfWeek);
    assertEquals(expectedSectionCode, meeting.sectionCode);
    assertEquals(expectedCourseKey, meeting.courseKey);
    assertEquals(expectedRoomCode, meeting.roomCode);
    assertEquals(expectedBuildingCode, meeting.buildingCode);
    assertEquals(expectedUniCampusCode, meeting.uniCampusCode);
  }
}
