package blog.mazleo.ruvacant.service.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import javax.annotation.Nullable;

/** A Rutgers course. */
@Entity
public final class RuCourse {
  /** Format: "SUBJECT.COURSE" */
  @PrimaryKey @NonNull public String key;

  public String code;
  public String title;
  @Nullable public String expandedTitle;
  public String subjectCode;
  public String uniCampusCode;

  public RuCourse(
      String key,
      String code,
      String title,
      String expandedTitle,
      String subjectCode,
      String uniCampusCode) {
    this.key = key;
    this.code = code;
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.uniCampusCode = uniCampusCode;
  }

  @Ignore
  public RuCourse(
      String code,
      String title,
      @Nullable String expandedTitle,
      String subjectCode,
      String uniCampusCode) {
    key = String.format("%s.%s", subjectCode, code);
    this.code = code;
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.uniCampusCode = uniCampusCode;
  }
}
