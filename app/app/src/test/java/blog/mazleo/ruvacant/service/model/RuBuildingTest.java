package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for the RuBuilding model. */
public final class RuBuildingTest {

  @Test
  public void construct_expectedValues() {
    String expectedCode = "HLL";
    String expectedName = "hill center";
    String expectedCampusName = "busch";
    String expectedUniCampusCode = "NB";
    RuBuilding building =
        new RuBuilding(expectedCode, expectedName, expectedCampusName, expectedUniCampusCode);
    assertEquals(expectedCode, building.code);
    assertEquals(expectedName, building.name);
    assertEquals(expectedCampusName, building.campusName);
    assertEquals(expectedUniCampusCode, building.uniCampusCode);
  }

  @Test
  public void construct_nullValues() {
    String expectedCode = null;
    String expectedName = null;
    String expectedCampusName = null;
    String expectedUniCampusCode = null;
    RuBuilding building =
        new RuBuilding(expectedCode, expectedName, expectedCampusName, expectedUniCampusCode);
    assertEquals(expectedCode, building.code);
    assertEquals(expectedName, building.name);
    assertEquals(expectedCampusName, building.campusName);
    assertEquals(expectedUniCampusCode, building.uniCampusCode);
  }
}
