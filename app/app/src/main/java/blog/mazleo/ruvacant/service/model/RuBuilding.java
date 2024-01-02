package blog.mazleo.ruvacant.service.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import javax.annotation.Nullable;

/** Model for a Rutgers Building. */
@Entity
public final class RuBuilding {

  /** Format: "CAMPUS.BUILDING" */
  @PrimaryKey @NonNull public String key;

  public String code;
  @Nullable public String name;
  public String campusName;
  public String semesterCode;
  public String uniCampusCode;
  public String levelCode;

  public RuBuilding(
      String key,
      String code,
      @Nullable String name,
      String campusName,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    this.key = key;
    this.code = code;
    this.name = name;
    this.campusName = campusName;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }

  @Ignore
  public RuBuilding(
      String code,
      @Nullable String name,
      String campusName,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    key = String.format("%s.%s", uniCampusCode, code);
    this.code = code;
    this.name = name;
    this.campusName = campusName;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }
}
