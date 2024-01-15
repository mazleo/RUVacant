package blog.mazleo.ruvacant.service.state.binders;

/** ID's for checking whether specific bindings already exist. */
public enum StateBinding {
  SAVE_TO_DATABASE("save-to-database"),
  READ_PLACES_FILE("read-places-file"),
  AGGREGATE_PLACES_AND_COURSES("aggregate-places-and-courses"),
  REQUEST_SUBJECT_INFO("request-subject-info"),
  REQUEST_COURSE_INFO("request-course-info"),
  SELECTION_SCENE("selection-scene"),
  UNIVERSITY_SCENE("university-scene"),
  UNIVERSITY_SCENE_BUILD_CARDS("university-scene-build-cards"),
  UNIVERSITY_SCENE_PRESENT_CARDS("university-scene-present-cards");

  private final String id;

  StateBinding(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
