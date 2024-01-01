package blog.mazleo.ruvacant.info;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import org.junit.Test;

/** Test cases for the UniversitySemesterUtil. */
public final class UniversitySemesterUtilTest {

  @Test
  public void formatSemesterCode() {
    assertEquals("12023", UniversitySemesterUtil.formatSemesterCode("1", "2023"));
    assertEquals("52024", UniversitySemesterUtil.formatSemesterCode("5", "2024"));
    assertEquals("02021", UniversitySemesterUtil.formatSemesterCode("0", "2021"));
  }

  @Test
  public void getSemesterMonthCode() {
    assertEquals("1", UniversitySemesterUtil.getSemesterMonthCode("Spring"));
    assertEquals("5", UniversitySemesterUtil.getSemesterMonthCode("Summer"));
    assertEquals("9", UniversitySemesterUtil.getSemesterMonthCode("Fall"));
    assertEquals("0", UniversitySemesterUtil.getSemesterMonthCode("Winter"));
  }

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
  public void getCurrentSemesterYear() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int nextYear = year + 1;
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(1));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(2));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(3));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(4));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(5));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(6));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(7));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(8));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(9));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(10));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getCurrentSemesterYear(11));
    assertEquals(String.valueOf(nextYear), UniversitySemesterUtil.getCurrentSemesterYear(12));
  }

  @Test
  public void getCurrentSemesterCode() {
    String year = UniversitySemesterUtil.getCurrentSemesterYear();
    String nextYear = UniversitySemesterUtil.getCurrentSemesterYear(12);
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getCurrentSemesterCode(1));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getCurrentSemesterCode(2));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getCurrentSemesterCode(3));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getCurrentSemesterCode(4));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getCurrentSemesterCode(5));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getCurrentSemesterCode(6));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getCurrentSemesterCode(7));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getCurrentSemesterCode(8));
    assertEquals(String.format("9%s", year), UniversitySemesterUtil.getCurrentSemesterCode(9));
    assertEquals(String.format("9%s", year), UniversitySemesterUtil.getCurrentSemesterCode(10));
    assertEquals(String.format("9%s", year), UniversitySemesterUtil.getCurrentSemesterCode(11));
    assertEquals(String.format("0%s", nextYear), UniversitySemesterUtil.getCurrentSemesterCode(12));
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
  public void getPreviousSemesterCode() {
    String year = UniversitySemesterUtil.getCurrentSemesterYear();
    assertEquals(String.format("0%s", year), UniversitySemesterUtil.getPreviousSemesterCode(1));
    assertEquals(String.format("0%s", year), UniversitySemesterUtil.getPreviousSemesterCode(2));
    assertEquals(String.format("0%s", year), UniversitySemesterUtil.getPreviousSemesterCode(3));
    assertEquals(String.format("0%s", year), UniversitySemesterUtil.getPreviousSemesterCode(4));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getPreviousSemesterCode(5));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getPreviousSemesterCode(6));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getPreviousSemesterCode(7));
    assertEquals(String.format("1%s", year), UniversitySemesterUtil.getPreviousSemesterCode(8));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getPreviousSemesterCode(9));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getPreviousSemesterCode(10));
    assertEquals(String.format("5%s", year), UniversitySemesterUtil.getPreviousSemesterCode(11));
    assertEquals(String.format("9%s", year), UniversitySemesterUtil.getPreviousSemesterCode(12));
  }

  @Test
  public void getPreviousPreviousSemester() {
    assertEquals(
        UniversitySemester.FALL.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(1));
    assertEquals(
        UniversitySemester.FALL.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(2));
    assertEquals(
        UniversitySemester.FALL.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(3));
    assertEquals(
        UniversitySemester.FALL.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(4));
    assertEquals(
        UniversitySemester.WINTER.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(5));
    assertEquals(
        UniversitySemester.WINTER.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(6));
    assertEquals(
        UniversitySemester.WINTER.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(7));
    assertEquals(
        UniversitySemester.WINTER.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(8));
    assertEquals(
        UniversitySemester.SPRING.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(9));
    assertEquals(
        UniversitySemester.SPRING.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(10));
    assertEquals(
        UniversitySemester.SPRING.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(11));
    assertEquals(
        UniversitySemester.SUMMER.getSemester(),
        UniversitySemesterUtil.getPreviousPreviousSemester(12));
  }

  @Test
  public void getPreviousPreviousSemesterYear() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int previousYear = year - 1;
    assertEquals(
        String.valueOf(previousYear), UniversitySemesterUtil.getPreviousPreviousSemesterYear(1));
    assertEquals(
        String.valueOf(previousYear), UniversitySemesterUtil.getPreviousPreviousSemesterYear(2));
    assertEquals(
        String.valueOf(previousYear), UniversitySemesterUtil.getPreviousPreviousSemesterYear(3));
    assertEquals(
        String.valueOf(previousYear), UniversitySemesterUtil.getPreviousPreviousSemesterYear(4));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(5));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(6));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(7));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(8));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(9));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(10));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(11));
    assertEquals(String.valueOf(year), UniversitySemesterUtil.getPreviousPreviousSemesterYear(12));
  }

  @Test
  public void getPreviousPreviousSemesterCode() {
    int year = Calendar.getInstance().get(Calendar.YEAR);
    int previousYear = year - 1;
    assertEquals(
        String.format("9%s", previousYear),
        UniversitySemesterUtil.getPreviousPreviousSemesterCode(1));
    assertEquals(
        String.format("9%s", previousYear),
        UniversitySemesterUtil.getPreviousPreviousSemesterCode(2));
    assertEquals(
        String.format("9%s", previousYear),
        UniversitySemesterUtil.getPreviousPreviousSemesterCode(3));
    assertEquals(
        String.format("9%s", previousYear),
        UniversitySemesterUtil.getPreviousPreviousSemesterCode(4));
    assertEquals(
        String.format("0%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(5));
    assertEquals(
        String.format("0%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(6));
    assertEquals(
        String.format("0%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(7));
    assertEquals(
        String.format("0%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(8));
    assertEquals(
        String.format("1%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(9));
    assertEquals(
        String.format("1%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(10));
    assertEquals(
        String.format("1%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(11));
    assertEquals(
        String.format("5%s", year), UniversitySemesterUtil.getPreviousPreviousSemesterCode(12));
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
