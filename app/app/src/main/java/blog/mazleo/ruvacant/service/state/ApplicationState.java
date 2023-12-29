package blog.mazleo.ruvacant.service.state;

/** All Application states. */
public enum ApplicationState {
  APPLICATION_START("application-start"),
  SUBJECTS_REQUEST("subjects-request"),
  COURSES_REQUEST("courses-request"),
  PLACES_READ("places-read"),
  DATA_SAVE("data-save"),
  BUILDING_VIEW_DATA_LOADING("building-view-data-loading"),
  BUILDING_VIEW_DATA_LOADED("building-view-data-loaded"),
  ROOM_VIEW_DATA_LOADING("room-view-data-loading"),
  ROOM_VIEW_DATA_LOADED("room-view-data-loaded"),
  CLASSROOM_VIEW_DATA_LOADING("classroom-view-data-loading"),
  CLASSROOM_VIEW_DATA_LOADED("classroom-view-data-loaded");

  private final String state;

  ApplicationState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }
}
