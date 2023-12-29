package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for RuClassRoomTest. */
public final class RuClassroomTest {
  @Test
  public void construct_expectedValues() {
    String expectedRoomCode = "105";
    String expectedBuildingCode = "ACH";
    String expectedCampusName = "cac";
    String expectedUniCampusCode = "NWK";
    String expectedCode = String.format("%s.%s", expectedBuildingCode, expectedRoomCode);
    RuClassroom classRoom =
        new RuClassroom(
            expectedRoomCode, expectedBuildingCode, expectedCampusName, expectedUniCampusCode);
    assertEquals(expectedCode, classRoom.key);
    assertEquals(expectedRoomCode, classRoom.code);
    assertEquals(expectedBuildingCode, classRoom.buildingCode);
    assertEquals(expectedCampusName, classRoom.campusName);
    assertEquals(expectedUniCampusCode, classRoom.uniCampusCode);
  }

  @Test
  public void construct_nullValues() {
    String expectedRoomCode = null;
    String expectedBuildingCode = null;
    String expectedCampusName = null;
    String expectedUniCampusCode = null;
    String expectedCode = String.format("%s.%s", expectedBuildingCode, expectedRoomCode);
    RuClassroom classRoom =
        new RuClassroom(
            expectedRoomCode, expectedBuildingCode, expectedCampusName, expectedUniCampusCode);
    assertEquals(expectedCode, classRoom.key);
    assertEquals(expectedRoomCode, classRoom.code);
    assertEquals(expectedBuildingCode, classRoom.buildingCode);
    assertEquals(expectedCampusName, classRoom.campusName);
    assertEquals(expectedUniCampusCode, classRoom.uniCampusCode);
  }
}
