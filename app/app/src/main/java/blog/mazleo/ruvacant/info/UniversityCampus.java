package blog.mazleo.ruvacant.info;

/** The Rutgers university campus. */
public enum UniversityCampus {
  NEW_BRUNSWICK("New Brunswick", "NB"),
  NEWARK("Newark", "NK"),
  CAMDEN("Camden", "CM");

  private final String campus;
  private final String code;

  UniversityCampus(String campus, String code) {
    this.campus = campus;
    this.code = code;
  }

  public String getCampus() {
    return campus;
  }

  public String getCode() {
    return code;
  }
}
