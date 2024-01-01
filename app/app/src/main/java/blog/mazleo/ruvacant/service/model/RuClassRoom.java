package blog.mazleo.ruvacant.service.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/** A Rutgers classroom. */
@Entity
public final class RuClassroom {

  /** Format: "CAMPUS.BUILDING.ROOM" */
  @PrimaryKey @NonNull public String key;

  public String code;
  public String buildingCode;
  public String campusName;
  public String semesterCode;
  public String uniCampusCode;
  public String levelCode;

  public RuClassroom(
      String key,
      String code,
      String buildingCode,
      String campusName,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    this.key = key;
    this.code = code;
    this.buildingCode = buildingCode;
    this.campusName = campusName;
    this.semesterCode = semesterCode;
    this.uniCampusCode = campusName;
    this.levelCode = levelCode;
  }

  @Ignore
  public RuClassroom(
      String code,
      String buildingCode,
      String campusName,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    key = String.format("%s.%s.%s", uniCampusCode, buildingCode, code);
    this.code = code;
    this.buildingCode = buildingCode;
    this.campusName = campusName;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }
}
