package blog.mazleo.ruvacant.info;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for UniversityLevelUtil. */
public final class UniversityLevelUtilTest {

  @Test
  public void getLevelCodeFromString() {
    assertEquals("U", UniversityLevelUtil.getLevelCodeFromString("Undergraduate"));
    assertEquals("G", UniversityLevelUtil.getLevelCodeFromString("Graduate"));
  }
}
