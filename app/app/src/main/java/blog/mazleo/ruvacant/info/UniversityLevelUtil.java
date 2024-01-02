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
}
