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
  public String uniCampusCode;

  public RuClassroom(
      String key, String code, String buildingCode, String campusName, String uniCampusCode) {
    this.key = key;
    this.code = code;
    this.buildingCode = buildingCode;
    this.campusName = campusName;
    this.uniCampusCode = uniCampusCode;
  }

  @Ignore
  public RuClassroom(String code, String buildingCode, String campusName, String uniCampusCode) {
    key = String.format("%s.%s.%s", uniCampusCode, buildingCode, code);
    this.code = code;
    this.buildingCode = buildingCode;
    this.campusName = campusName;
    this.uniCampusCode = uniCampusCode;
  }
}
