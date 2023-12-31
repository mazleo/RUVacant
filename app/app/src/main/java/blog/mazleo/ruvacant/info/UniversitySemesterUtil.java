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
    return String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
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
    if (nextSemester.equals(UniversitySemester.SPRING.getSemester())) {
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
    int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    return getPreviousSemesterYear(currentMonth);
  }

  public static String getPreviousSemesterYear(int currentMonth) {
    String previousSemester = getPreviousSemester(currentMonth);
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    if (previousSemester.equals(UniversitySemester.WINTER.getSemester())) {
      return String.valueOf(currentYear - 1);
    }
    return String.valueOf(currentYear);
  }
}
