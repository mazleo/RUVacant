package blog.mazleo.ruvacant.service.state;

import static blog.mazleo.ruvacant.info.UniversitySemesterUtil.getSemesterCodeFromString;
import static blog.mazleo.ruvacant.info.UniversitySemesterUtil.getSemesterYearFromString;

/** The context holding the user options throughout the session. */
public final class UniversityContext {

  public String semesterString;
  public String semesterMonthCode;
  public String semesterYearCode;
  public String campusCode;
  public String levelCode;

  public UniversityContext(String semesterString, String campusCode, String levelCode) {
    this.semesterString = semesterString;
    this.campusCode = campusCode;
    this.levelCode = levelCode;
    semesterMonthCode = getSemesterCodeFromString(semesterString);
    semesterYearCode = getSemesterYearFromString(semesterString);
  }
}
