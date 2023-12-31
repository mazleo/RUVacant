package blog.mazleo.ruvacant.ui.dialogs;

/** The selection type. */
public enum UniversitySelection {
  SEMESTER("semester"),
  CAMPUS("campus"),
  LEVEL("level");

  private final String selection;

  UniversitySelection(String selection) {
    this.selection = selection;
  }

  public String getSelection() {
    return selection;
  }
}
