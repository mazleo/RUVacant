package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** A Rutgers course. */
@Entity
public final class RuCourse {
  @PrimaryKey public String code;
  public String title;
  public String expandedTitle;
  public String subjectCode;
  public String campusCode;

  public RuCourse(
      String code, String title, String expandedTitle, String subjectCode, String campusCode) {
    this.code = code;
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.campusCode = campusCode;
  }
}
