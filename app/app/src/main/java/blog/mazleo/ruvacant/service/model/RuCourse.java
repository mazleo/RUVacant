package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** A Rutgers course. */
@Entity
public final class RuCourse {
  /** Format: "SUBJECT.COURSE" */
  @PrimaryKey public String code;

  public String title;
  public String expandedTitle;
  public String subjectCode;
  public String uniCampusCode;

  public RuCourse(
      String courseCode,
      String title,
      String expandedTitle,
      String subjectCode,
      String uniCampusCode) {
    code = String.format("%s.%s", subjectCode, courseCode);
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.uniCampusCode = uniCampusCode;
  }
}
