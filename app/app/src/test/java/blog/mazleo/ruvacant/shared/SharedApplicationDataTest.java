package blog.mazleo.ruvacant.shared;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/** Test cases for SharedApplicationDataTest. */
public final class SharedApplicationDataTest {

  private SharedApplicationData sharedApplicationData;

  @Before
  public void setup() {
    sharedApplicationData = new SharedApplicationData();
  }

  @Test
  public void addData() {
    String existingData = "existing-data";
    Integer data = 5;
    String nonexistentData = "non-existent=data";
    sharedApplicationData.addData(existingData, data);
    assertTrue(sharedApplicationData.containsData(existingData));
    assertFalse(sharedApplicationData.containsData(nonexistentData));
    assertEquals(sharedApplicationData.getData(existingData), data);
  }

  @Test
  public void removeData() {
    String tag = "tag";
    Integer data = 5;
    sharedApplicationData.addData(tag, data);
    assertTrue(sharedApplicationData.containsData(tag));
    assertEquals(sharedApplicationData.getData(tag), data);
    sharedApplicationData.removeData(tag);
    assertFalse(sharedApplicationData.containsData(tag));
  }
}
