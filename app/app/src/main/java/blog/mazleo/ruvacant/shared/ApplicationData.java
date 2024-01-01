package blog.mazleo.ruvacant.shared;

/** The shared data being held. */
public enum ApplicationData {
  SUBJECTS_LIST_CACHE("subjects-list-cache"),
  SUBJECTS_NUM("subjects-num"),
  CLASS_INFOS_CACHE("class-infos-cache"),
  PLACES_CACHE("places-cache"),
  SEMESTER_CODE("semester-code"),
  CAMPUS_CODE("campus_code"),
  LEVEL_CODE("level_code");

  private final String tag;

  ApplicationData(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }
}
