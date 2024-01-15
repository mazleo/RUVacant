package blog.mazleo.ruvacant.info;

/** Utils around campuses. */
public final class UniversityCampusUtil {

  public static String getCampusCodeFromString(String campusString) {
    if (campusString.equals(UniversityCampus.NEW_BRUNSWICK.getCampus())) {
      return UniversityCampus.NEW_BRUNSWICK.getCode();
    } else if (campusString.equals(UniversityCampus.NEWARK.getCampus())) {
      return UniversityCampus.NEWARK.getCode();
    } else if (campusString.equals(UniversityCampus.CAMDEN.getCampus())) {
      return UniversityCampus.CAMDEN.getCode();
    }
    throw new IllegalArgumentException(String.format("Campus %s not supported.", campusString));
  }

  public static String getNameFromCampusCode(String code) {
    if (code.equals(UniversityCampus.NEW_BRUNSWICK.getCode())) {
      return UniversityCampus.NEW_BRUNSWICK.getCampus();
    } else if (code.equals(UniversityCampus.NEWARK.getCode())) {
      return UniversityCampus.NEWARK.getCampus();
    } else if (code.equals(UniversityCampus.CAMDEN.getCode())) {
      return UniversityCampus.CAMDEN.getCampus();
    }
    throw new IllegalArgumentException(String.format("Campus code %s not supported.", code));
  }
}
