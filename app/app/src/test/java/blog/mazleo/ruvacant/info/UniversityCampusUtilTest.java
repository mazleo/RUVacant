package blog.mazleo.ruvacant.info;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for UniversityCampusUtil. */
public final class UniversityCampusUtilTest {

  @Test
  public void getCampusCodeFromString() {
    assertEquals("NB", UniversityCampusUtil.getCampusCodeFromString("New Brunswick"));
    assertEquals("NK", UniversityCampusUtil.getCampusCodeFromString("Newark"));
    assertEquals("CM", UniversityCampusUtil.getCampusCodeFromString("Camden"));
  }
}
