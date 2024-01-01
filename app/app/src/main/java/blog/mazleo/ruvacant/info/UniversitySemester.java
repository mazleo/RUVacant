package blog.mazleo.ruvacant.info;

/** The university semester. */
public enum UniversitySemester {
  FALL("Fall", 9, "9"),
  WINTER("Winter", 12, "0"),
  SPRING("Spring", 1, "1"),
  SUMMER("Summer", 5, "5");

  private final String semester;
  private final int startMonth;
  private final String code;

  UniversitySemester(String semester, int startMonth, String code) {
    this.semester = semester;
    this.startMonth = startMonth;
    this.code = code;
  }

  public String getSemester() {
    return semester;
  }

  public int getStartMonth() {
    return startMonth;
  }

  public String getCode() {
    return code;
  }
}
