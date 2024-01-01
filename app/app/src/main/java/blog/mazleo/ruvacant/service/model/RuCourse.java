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
  public String semesterCode;
  public String uniCampusCode;
  public String levelCode;

  public RuCourse(
      String key,
      String code,
      String title,
      String expandedTitle,
      String subjectCode,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    this.key = key;
    this.code = code;
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }

  @Ignore
  public RuCourse(
      String code,
      String title,
      @Nullable String expandedTitle,
      String subjectCode,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    key = String.format("%s.%s", subjectCode, code);
    this.code = code;
    this.title = title;
    this.expandedTitle = expandedTitle;
    this.subjectCode = subjectCode;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }
}
