package blog.mazleo.ruvacant.ui.content;

import androidx.annotation.Nullable;

/** Wrapper class for information required by the ContentActivity. */
public final class ContentActivityInfo {

  public String title;
  public @Nullable String subtitle;
  public ContentActivityType contentActivityType;
  public String campusName;
  public String uniCampusName;
  public String level;

  public ContentActivityInfo(
      String title,
      String subtitle,
      ContentActivityType contentActivityType,
      String campusName,
      String uniCampusName,
      String level) {
    this.title = title;
    this.subtitle = subtitle;
    this.contentActivityType = contentActivityType;
    this.campusName = campusName;
    this.uniCampusName = uniCampusName;
    this.level = level;
  }
}
