package blog.mazleo.ruvacant.info;

/** Campuses included in the Rutgers API. */
public enum Campus {
  LIVINGSTON("Livingston", "livingston"),
  COLLEGE_AVE("College Avenue", "college avenue"),
  DOUGLAS_COOK("Douglas/Cook", "douglas/cook"),
  BUSCH("Busch", "busch"),
  DOWNTOWN_NEW_BRUNSWICK("Downtown New Brunswick", "downtown nb"),
  NEWARK("Newark", "newark"),
  CAMDEN("Camden", "camden");

  private final String name;
  private final String responseName;

  Campus(String name, String responseName) {
    this.name = name;
    this.responseName = responseName;
  }

  public String getName() {
    return name;
  }

  public String getResponseName() {
    return responseName;
  }
}
