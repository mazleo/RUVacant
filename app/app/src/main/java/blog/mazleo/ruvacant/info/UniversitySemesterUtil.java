package blog.mazleo.ruvacant.info;

import java.util.Calendar;

/** Util for getting university semesters. */
public final class UniversitySemesterUtil {

  public static final String[] SEMESTER_ORDER =
      new String[] {
        UniversitySemester.FALL.getSemester(),
        UniversitySemester.WINTER.getSemester(),
        UniversitySemester.SPRING.getSemester(),
        UniversitySemester.SUMMER.getSemester()
      };

  public static String formatSemesterCode(String monthCode, String year) {
    return String.format("%s%s", monthCode, year);
  }

  public static String getSemesterMonthCode(String semester) {
    if (semester.equals(UniversitySemester.SPRING.getSemester())) {
      return UniversitySemester.SPRING.getCode();
    } else if (semester.equals(UniversitySemester.SUMMER.getSemester())) {
      return UniversitySemester.SUMMER.getCode();
    } else if (semester.equals(UniversitySemester.FALL.getSemester())) {
      return UniversitySemester.FALL.getCode();
    } else if (semester.equals(UniversitySemester.WINTER.getSemester())) {
      return UniversitySemester.WINTER.getCode();
    }
    throw new IllegalArgumentException(String.format("Semester %s not supported.", semester));
  }

  public static String getCurrentSemester() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getCurrentSemester(currentMonth);
  }

  public static String getCurrentSemester(int currentMonth) {
    if (currentMonth >= UniversitySemester.WINTER.getStartMonth()) {
      return UniversitySemester.WINTER.getSemester();
    } else if (currentMonth >= UniversitySemester.FALL.getStartMonth()) {
      return UniversitySemester.FALL.getSemester();
    } else if (currentMonth >= UniversitySemester.SUMMER.getStartMonth()) {
      return UniversitySemester.SUMMER.getSemester();
    } else if (currentMonth >= UniversitySemester.SPRING.getStartMonth()) {
      return UniversitySemester.SPRING.getSemester();
    }
    throw new IllegalStateException(String.format("Month %i not supported.", currentMonth));
  }

  public static String getCurrentSemesterYear() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getCurrentSemesterYear(currentMonth);
  }

  public static String getCurrentSemesterYear(int currentMonth) {
    if (currentMonth < 12) {
      return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    } else {
      return String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + 1);
    }
  }

  public static String getCurrentSemesterCode() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getCurrentSemesterCode(currentMonth);
  }

  public static String getCurrentSemesterCode(int currentMonth) {
    String semester = getCurrentSemester(currentMonth);
    String monthCode = getSemesterMonthCode(semester);
    return formatSemesterCode(monthCode, getCurrentSemesterYear(currentMonth));
  }

  public static String getNextSemester() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getNextSemester(currentMonth);
  }

  public static String getNextSemester(int currentMonth) {
    String currentSemester = getCurrentSemester(currentMonth);
    for (int s = 0; s < SEMESTER_ORDER.length; s++) {
      if (currentSemester.equals(SEMESTER_ORDER[s])) {
        if (s == SEMESTER_ORDER.length - 1) {
          return SEMESTER_ORDER[0];
        } else {
          return SEMESTER_ORDER[s + 1];
        }
      }
    }
    throw new IllegalStateException("Couldn't get next semester.");
  }

  public static String getNextSemesterYear() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getNextSemesterYear(currentMonth);
  }

  public static String getNextSemesterYear(int currentMonth) {
    String nextSemester = getNextSemester(currentMonth);
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    if (nextSemester.equals(UniversitySemester.WINTER.getSemester())
        || nextSemester.equals(UniversitySemester.SPRING.getSemester())) {
      return String.valueOf(currentYear + 1);
    }
    return String.valueOf(currentYear);
  }

  public static String getPreviousSemester() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousSemester(currentMonth);
  }

  public static String getPreviousSemester(int currentMonth) {
    String currentSemester = getCurrentSemester(currentMonth);
    for (int s = 0; s < SEMESTER_ORDER.length; s++) {
      if (currentSemester.equals(SEMESTER_ORDER[s])) {
        if (s == 0) {
          return SEMESTER_ORDER[SEMESTER_ORDER.length - 1];
        } else {
          return SEMESTER_ORDER[s - 1];
        }
      }
    }
    throw new IllegalStateException("Couldn't get previous semester.");
  }

  public static String getPreviousSemesterYear() {
    return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
  }

  public static String getPreviousSemesterCode() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousSemesterCode(currentMonth);
  }

  public static String getPreviousSemesterCode(int currentMonth) {
    String semester = getPreviousSemester(currentMonth);
    String monthCode = getSemesterMonthCode(semester);
    return formatSemesterCode(monthCode, getPreviousSemesterYear());
  }

  public static String getPreviousPreviousSemester() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousPreviousSemester(currentMonth);
  }

  public static String getPreviousPreviousSemester(int currentMonth) {
    String previousSemester = getPreviousSemester(currentMonth);
    for (int s = 0; s < SEMESTER_ORDER.length; s++) {
      if (previousSemester.equals(SEMESTER_ORDER[s])) {
        if (s == 0) {
          return SEMESTER_ORDER[SEMESTER_ORDER.length - 1];
        } else {
          return SEMESTER_ORDER[s - 1];
        }
      }
    }
    throw new IllegalStateException("Couldn't get previous semester.");
  }

  public static String getPreviousPreviousSemesterYear() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousPreviousSemesterYear(currentMonth);
  }

  public static String getPreviousPreviousSemesterYear(int currentMonth) {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    if (getCurrentSemester(currentMonth).equals(UniversitySemester.SPRING.getSemester())) {
      return String.valueOf(currentYear - 1);
    }
    return String.valueOf(currentYear);
  }

  public static String getPreviousPreviousSemesterCode() {
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousPreviousSemesterCode(currentMonth);
  }

  public static String getPreviousPreviousSemesterCode(int currentMonth) {
    String semester = getPreviousPreviousSemester(currentMonth);
    String monthCode = getSemesterMonthCode(semester);
    return formatSemesterCode(monthCode, getPreviousPreviousSemesterYear(currentMonth));
  }

  public static String getSemesterCodeFromString(String semesterString) {
    String semester = semesterString.split(" ")[0].trim();
    if (semester.equals(UniversitySemester.FALL.getSemester())) {
      return "9";
    } else if (semester.equals(UniversitySemester.WINTER.getSemester())) {
      return "0";
    } else if (semester.equals(UniversitySemester.SPRING.getSemester())) {
      return "1";
    } else if (semester.equals(UniversitySemester.SUMMER.getSemester())) {
      return "5";
    }
    throw new IllegalArgumentException(String.format("Semester %s not supported.", semesterString));
  }

  public static String getSemesterYearFromString(String semesterString) {
    return semesterString.split(" ")[1].trim();
  }
}
