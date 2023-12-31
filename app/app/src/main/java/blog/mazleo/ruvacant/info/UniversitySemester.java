package blog.mazleo.ruvacant.info;

/** The university semester. */
public enum UniversitySemester {
  FALL("Fall", 9),
  WINTER("Winter", 12),
  SPRING("Spring", 1),
  SUMMER("Summer", 5);

  private final String semester;
  private final int startMonth;

  UniversitySemester(String semester, int startMonth) {
    this.semester = semester;
    this.startMonth = startMonth;
  }

  public String getSemester() {
    return semester;
  }

  public int getStartMonth() {
    return startMonth;
  }
}
