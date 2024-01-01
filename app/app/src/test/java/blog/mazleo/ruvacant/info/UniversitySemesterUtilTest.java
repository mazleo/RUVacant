package blog.mazleo.ruvacant.info;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import org.junit.Test;

/** Test cases for the UniversitySemesterUtil. */
public final class UniversitySemesterUtilTest {

  @Test
  public void getCurrentSemester() {
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getCurrentSemester(1));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getCurrentSemester(2));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getCurrentSemester(3));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getCurrentSemester(4));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getCurrentSemester(5));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getCurrentSemester(6));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getCurrentSemester(7));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getCurrentSemester(8));
    assertEquals(
        UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getCurrentSemester(9));
    assertEquals(
        UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getCurrentSemester(10));
    assertEquals(
        UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getCurrentSemester(11));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getCurrentSemester(12));
  }

  @Test
  public void getNextSemester() {
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getNextSemester(1));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getNextSemester(2));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getNextSemester(3));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getNextSemester(4));
    assertEquals(UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getNextSemester(5));
    assertEquals(UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getNextSemester(6));
    assertEquals(UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getNextSemester(7));
    assertEquals(UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getNextSemester(8));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getNextSemester(9));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getNextSemester(10));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getNextSemester(11));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getNextSemester(12));
  }

  @Test
  public void getNextSemesterYear() {
    String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    String nextYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(1));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(2));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(3));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(4));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(5));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(6));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(7));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(8));
    assertEquals(nextYear, UniversitySemesterUtil.getNextSemesterYear(9));
    assertEquals(nextYear, UniversitySemesterUtil.getNextSemesterYear(10));
    assertEquals(nextYear, UniversitySemesterUtil.getNextSemesterYear(11));
    assertEquals(nextYear, UniversitySemesterUtil.getNextSemesterYear(12));
  }

  @Test
  public void getPreviousSemester() {
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getPreviousSemester(1));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getPreviousSemester(2));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getPreviousSemester(3));
    assertEquals(
        UniversitySemester.WINTER.getSemester(), UniversitySemesterUtil.getPreviousSemester(4));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getPreviousSemester(5));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getPreviousSemester(6));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getPreviousSemester(7));
    assertEquals(
        UniversitySemester.SPRING.getSemester(), UniversitySemesterUtil.getPreviousSemester(8));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getPreviousSemester(9));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getPreviousSemester(10));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(), UniversitySemesterUtil.getPreviousSemester(11));
    assertEquals(
        UniversitySemester.FALL.getSemester(), UniversitySemesterUtil.getPreviousSemester(12));
  }

  @Test
  public void getPreviousSemesterYear() {
    String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear());
  }

  @Test
  public void getSemesterCodeFromString() {
    assertEquals("1", UniversitySemesterUtil.getSemesterCodeFromString("Spring 2023"));
    assertEquals("5", UniversitySemesterUtil.getSemesterCodeFromString("Summer 2023"));
    assertEquals("9", UniversitySemesterUtil.getSemesterCodeFromString("Fall 2023"));
    assertEquals("0", UniversitySemesterUtil.getSemesterCodeFromString("Winter 2023"));
  }

  @Test
  public void getSemesterYearFromString() {
    assertEquals("2023", UniversitySemesterUtil.getSemesterYearFromString("Spring 2023"));
    assertEquals("2023", UniversitySemesterUtil.getSemesterYearFromString("Summer 2023"));
    assertEquals("2023", UniversitySemesterUtil.getSemesterYearFromString("Fall 2023"));
    assertEquals("2023", UniversitySemesterUtil.getSemesterYearFromString("Winter 2023"));
    assertEquals("2024", UniversitySemesterUtil.getSemesterYearFromString("Spring 2024"));
    assertEquals("2024", UniversitySemesterUtil.getSemesterYearFromString("Summer 2024"));
    assertEquals("2024", UniversitySemesterUtil.getSemesterYearFromString("Fall 2024"));
    assertEquals("2024", UniversitySemesterUtil.getSemesterYearFromString("Winter 2024"));
    assertEquals("2025", UniversitySemesterUtil.getSemesterYearFromString("Spring 2025"));
    assertEquals("2025", UniversitySemesterUtil.getSemesterYearFromString("Summer 2025"));
    assertEquals("2025", UniversitySemesterUtil.getSemesterYearFromString("Fall 2025"));
    assertEquals("2025", UniversitySemesterUtil.getSemesterYearFromString("Winter 2025"));
  }
}
