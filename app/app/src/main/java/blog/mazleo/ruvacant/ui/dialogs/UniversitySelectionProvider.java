package blog.mazleo.ruvacant.ui.dialogs;

import blog.mazleo.ruvacant.info.UniversityCampus;
import blog.mazleo.ruvacant.info.UniversityLevel;
import blog.mazleo.ruvacant.info.UniversitySemesterUtil;
import java.util.ArrayList;
import java.util.List;

/** Provides selection options depending on the selection type. */
public final class UniversitySelectionProvider {

  public static List<String> get(String selection) {
    if (selection.equals(UniversitySelection.SEMESTER.getSelection())) {
      return provideSemesterSelections();
    } else if (selection.equals(UniversitySelection.CAMPUS.getSelection())) {
      return provideCampusSelections();
    } else if (selection.equals(UniversitySelection.LEVEL.getSelection())) {
      return provideLevelSelections();
    }
    throw new IllegalArgumentException(String.format("Selection %s not supported.", selection));
  }

  private static List<String> provideSemesterSelections() {
    List<String> semesterSelections = new ArrayList<>();
    semesterSelections.add(
        String.format(
            "%s %s",
            UniversitySemesterUtil.getCurrentSemester(),
            UniversitySemesterUtil.getCurrentSemesterYear()));
    semesterSelections.add(
        String.format(
            "%s %s",
            UniversitySemesterUtil.getPreviousSemester(),
            UniversitySemesterUtil.getPreviousSemesterYear()));
    semesterSelections.add(
        String.format(
            "%s %s",
            UniversitySemesterUtil.getPreviousPreviousSemester(),
            UniversitySemesterUtil.getPreviousPreviousSemesterYear()));
    return semesterSelections;
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
