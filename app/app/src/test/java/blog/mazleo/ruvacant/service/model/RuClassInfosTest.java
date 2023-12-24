package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public final class RuClassInfosTest {
  private List<RuCourse> expectedCourses;
  private List<RuMeeting> expectedMeetings;
  private List<RuBuilding> expectedBuildings;
  private List<RuClassRoom> expectedClassRooms;

  @Before
  public void setup() {
    expectedCourses = new ArrayList<>();
    expectedMeetings = new ArrayList<>();
    expectedBuildings = new ArrayList<>();
    expectedClassRooms = new ArrayList<>();
  }

  @Test
  public void construct_expectedValues() {
    expectedCourses.add(new RuCourse("101", "comp arch", "computer architecture", "198", "NB"));
    expectedMeetings.add(
        new RuMeeting("0215", "0415", "P", "T", "02", "283.294", "109", "SC", "NB"));
    expectedBuildings.add(new RuBuilding("TLL", "tillete hall", "busch", "NB"));
    expectedClassRooms.add(new RuClassRoom("287", "LC", "cac", "NB"));
    RuClassInfos classInfos =
        new RuClassInfos(expectedCourses, expectedMeetings, expectedBuildings, expectedClassRooms);
    assertSame(expectedCourses, classInfos.courses);
    assertSame(expectedMeetings, classInfos.meetings);
    assertSame(expectedBuildings, classInfos.buildings);
    assertSame(expectedClassRooms, classInfos.classRooms);
  }

  @Test
  public void construct_empty() {
    RuClassInfos classInfos =
        new RuClassInfos(expectedCourses, expectedMeetings, expectedBuildings, expectedClassRooms);
    assertSame(expectedCourses, classInfos.courses);
    assertSame(expectedMeetings, classInfos.meetings);
    assertSame(expectedBuildings, classInfos.buildings);
    assertSame(expectedClassRooms, classInfos.classRooms);
    assertEquals(/* actual= */ 0, classInfos.courses.size());
    assertEquals(/* actual= */ 0, classInfos.meetings.size());
    assertEquals(/* actual= */ 0, classInfos.buildings.size());
    assertEquals(/* actual= */ 0, classInfos.classRooms.size());
  }
}
