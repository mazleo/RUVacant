package blog.mazleo.ruvacant.info;

/** The Rutgers university campus. */
public enum UniversityCampus {
  NEW_BRUNSWICK("New Brunswick"),
  NEWARK("Newark"),
  CAMDEN("Camden");

  private final String campus;

  UniversityCampus(String campus) {
    this.campus = campus;
  }

  public String getCampus() {
    return campus;
  }
}
