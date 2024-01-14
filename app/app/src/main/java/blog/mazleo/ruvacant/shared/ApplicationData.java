package blog.mazleo.ruvacant.shared;

/** The shared data being held. */
public enum ApplicationData {
  CLASS_INFOS_CACHE("class-infos-cache"),
  PLACES_CACHE("places-cache"),
  UNIVERSITY_CONTEXT("university-context"),
  CONTENT_ACTIVITY("content_activity");

  private final String tag;

  ApplicationData(String tag) {
    this.tag = tag;
  }

  public String getTag() {
    return tag;
  }
}
