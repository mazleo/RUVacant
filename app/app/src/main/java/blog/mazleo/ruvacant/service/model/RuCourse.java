package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import javax.annotation.Nullable;

/** A Rutgers course. */
@Entity
public final class RuCourse {
  /** Format: "SUBJECT.COURSE" */
  @PrimaryKey public String code;

  public String title;
  @Nullable public String expandedTitle;
  public String subjectCode;
  public String uniCampusCode;

  @Ignore
  public RuCourse(
      String courseCode,
      String title,
      @Nullable String expandedTitle,
      String subjectCode,
      String uniCampusCode) {
    code = String.format("%s.%s", subjectCode, courseCode);
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.uniCampusCode = uniCampusCode;
  }
}
