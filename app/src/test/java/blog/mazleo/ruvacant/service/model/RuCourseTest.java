package blog.mazleo.ruvacant.service.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Test cases for RuCourse. */
public final class RuCourseTest {
  @Test
  public void construct_expectedValues() {
    String expectedCourseCode = "101";
    String expectedTitle = "comp arch";
    String expectedExpandedTitle = "computer architecture";
    String expectedSubjectCode = "198";
    String expectedUniCampusCode = "CAM";
    String expectedCode = String.format("%s.%s", expectedSubjectCode, expectedCourseCode);
    RuCourse course =
        new RuCourse(
            expectedCourseCode,
            expectedTitle,
            expectedExpandedTitle,
            expectedSubjectCode,
            /* semesterCode= */ null,
            expectedUniCampusCode,
            /* levelCode= */ null);
    assertEquals(expectedCode, course.key);
    assertEquals(expectedCourseCode, course.code);
    assertEquals(expectedTitle, course.title);
    assertEquals(expectedExpandedTitle, course.expandedTitle);
    assertEquals(expectedSubjectCode, course.subjectCode);
    assertEquals(expectedUniCampusCode, course.uniCampusCode);
  }

  @Test
  public void construct_nullValues() {
    String expectedCourseCode = null;
    String expectedTitle = null;
    String expectedExpandedTitle = null;
    String expectedSubjectCode = null;
    String expectedUniCampusCode = null;
    String expectedCode = String.format("%s.%s", expectedSubjectCode, expectedCourseCode);
    RuCourse course =
        new RuCourse(
            expectedCourseCode,
            expectedTitle,
            expectedExpandedTitle,
            expectedSubjectCode,
            /* semesterCode= */ null,
            expectedUniCampusCode,
            /* levelCode= */ null);
    assertEquals(expectedCode, course.key);
    assertEquals(expectedCourseCode, course.code);
    assertEquals(expectedTitle, course.title);
    assertEquals(expectedExpandedTitle, course.expandedTitle);
    assertEquals(expectedSubjectCode, course.subjectCode);
    assertEquals(expectedUniCampusCode, course.uniCampusCode);
  }
}
