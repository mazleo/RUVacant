package blog.mazleo.ruvacant.info;

/** The university level. */
public enum UniversityLevel {
  UNDERGRADUATE("Undergraduate"),
  GRADUATE("Graduate");

  private final String level;

  UniversityLevel(String level) {
    this.level = level;
  }

  public String getLevel() {
    return level;
  }
}
