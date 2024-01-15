package blog.mazleo.ruvacant.info;

import java.util.ArrayList;
import java.util.List;

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

  public static List<UniversityLevel> getAllLevels() {
    List<UniversityLevel> levels = new ArrayList<>();
    levels.add(UNDERGRADUATE);
    levels.add(GRADUATE);
    return levels;
  }
}
