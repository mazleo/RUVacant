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
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(9));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(10));
    assertEquals(currentYear, UniversitySemesterUtil.getNextSemesterYear(11));
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
    String previousYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1);
    assertEquals(previousYear, UniversitySemesterUtil.getPreviousSemesterYear(1));
    assertEquals(previousYear, UniversitySemesterUtil.getPreviousSemesterYear(2));
    assertEquals(previousYear, UniversitySemesterUtil.getPreviousSemesterYear(3));
    assertEquals(previousYear, UniversitySemesterUtil.getPreviousSemesterYear(4));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(5));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(6));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(7));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(8));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(9));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(10));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(11));
    assertEquals(currentYear, UniversitySemesterUtil.getPreviousSemesterYear(12));
  }
}
