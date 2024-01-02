package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for RuSubject. */
public final class RuSubjectTest {
  @Test
  public void construct_expectedValues() {
    String expectedCode = "123";
    String expectedName = "history";
    RuSubject subject = new RuSubject(expectedCode, expectedName);
    assertEquals(expectedCode, subject.code);
    assertEquals(expectedName, subject.name);
  }

  @Test
  public void construct_nullValues() {
    String expectedCode = null;
    String expectedName = null;
    RuSubject subject = new RuSubject(expectedCode, expectedName);
    assertEquals(expectedCode, subject.code);
    assertEquals(expectedName, subject.name);
  }
}
