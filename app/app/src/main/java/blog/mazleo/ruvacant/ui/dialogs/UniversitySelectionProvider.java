package blog.mazleo.ruvacant.ui.dialogs;

import blog.mazleo.ruvacant.info.UniversityCampus;
import blog.mazleo.ruvacant.info.UniversityLevel;
import java.util.ArrayList;
import java.util.List;

/** Provides selection options depending on the selection type. */
public final class UniversitySelectionProvider {

  public static List<String> get(String selection) {
    if (selection.equals(UniversitySelection.SEMESTER.getSelection())) {
      return null;
    } else if (selection.equals(UniversitySelection.CAMPUS.getSelection())) {
      return provideCampusSelections();
    } else if (selection.equals(UniversitySelection.LEVEL.getSelection())) {
      return provideLevelSelections();
    }
    throw new IllegalArgumentException(String.format("Selection %s not supported.", selection));
  }

  private static List<String> provideCampusSelections() {
    List<String> campusSelections = new ArrayList<>();
    campusSelections.add(UniversityCampus.NEW_BRUNSWICK.getCampus());
    campusSelections.add(UniversityCampus.NEWARK.getCampus());
    campusSelections.add(UniversityCampus.CAMDEN.getCampus());
    return campusSelections;
  }

  private static List<String> provideLevelSelections() {
    List<String> levelSelections = new ArrayList<>();
    levelSelections.add(UniversityLevel.UNDERGRADUATE.getLevel());
    levelSelections.add(UniversityLevel.GRADUATE.getLevel());
    return levelSelections;
  }
}
