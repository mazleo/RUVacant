package blog.mazleo.ruvacant.ui.content;

import static blog.mazleo.ruvacant.info.UniversityCampusUtil.getNameFromCampusCode;
import static blog.mazleo.ruvacant.info.UniversityLevelUtil.getLevelNameFromCode;

import blog.mazleo.ruvacant.service.state.UniversityContext;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import javax.inject.Inject;

/** Util for the ContentActivityInfo. */
public final class ContentActivityInfoUtil {

  private final SharedApplicationData sharedApplicationData;

  @Inject
  ContentActivityInfoUtil(SharedApplicationData sharedApplicationData) {
    this.sharedApplicationData = sharedApplicationData;
  }

  public ContentActivityInfo getNewContentActivityInfo() {
    UniversityContext universityContext =
        (UniversityContext)
            sharedApplicationData.getData(ApplicationData.UNIVERSITY_CONTEXT.getTag());
    String title = universityContext.semesterString;
    String uniCampusName = getNameFromCampusCode(universityContext.campusCode);
    String level = getLevelNameFromCode(universityContext.levelCode);
    return new ContentActivityInfo(
        title,
        /* subtitle= */ null,
        /* contentActivityType= */ null,
        /* campusName= */ null,
        uniCampusName,
        level);
  }
}
