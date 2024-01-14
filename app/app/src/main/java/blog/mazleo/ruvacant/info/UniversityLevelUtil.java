package blog.mazleo.ruvacant.info;

/** Utils around university levels. */
public final class UniversityLevelUtil {

  public static String getLevelCodeFromString(String levelString) {
    if (levelString.equals(UniversityLevel.UNDERGRADUATE.getLevel())) {
      return UniversityLevel.UNDERGRADUATE.getCode();
    } else if (levelString.equals(UniversityLevel.GRADUATE.getLevel())) {
      return UniversityLevel.GRADUATE.getCode();
    }
    throw new IllegalArgumentException(String.format("Level %s not supported.", levelString));
  }

  public static String getLevelNameFromCode(String code) {
    if (code.equals(UniversityLevel.UNDERGRADUATE.getCode())) {
      return UniversityLevel.UNDERGRADUATE.getLevel();
    } else if (code.equals(UniversityLevel.GRADUATE.getCode())) {
      return UniversityLevel.GRADUATE.getLevel();
    }
    throw new IllegalArgumentException(String.format("Level code %s not supported.", code));
  }
}
