package blog.mazleo.ruvacant.service.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import javax.annotation.Nullable;

/** Model for a Rutgers Building. */
@Entity
public final class RuBuilding {

  @PrimaryKey @NonNull public String code;
  @Nullable public String name;
  public String campusName;
  public String semesterCode;
  public String uniCampusCode;
  public String levelCode;

  public RuBuilding(
      String code,
      @Nullable String name,
      String campusName,
      String semesterCode,
      String uniCampusCode,
      String levelCode) {
    this.code = code;
    this.name = name;
    this.campusName = campusName;
    this.semesterCode = semesterCode;
    this.uniCampusCode = uniCampusCode;
    this.levelCode = levelCode;
  }
}
