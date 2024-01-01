package blog.mazleo.ruvacant.info;

/** The university level. */
public enum UniversityLevel {
  UNDERGRADUATE("Undergraduate", "U"),
  GRADUATE("Graduate", "G");

  private final String level;
  private final String code;

  UniversityLevel(String level, String code) {
    this.level = level;
    this.code = code;
  }

  public String getLevel() {
    return level;
  }

  public String getCode() {
    return code;
  }
}
