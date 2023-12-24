package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Model for a Rutgers Building. */
@Entity
public final class RuBuilding {

  @PrimaryKey public String code;
  public String name;
  public String campusName;
  public String uniCampusCode;

  public RuBuilding(String code, String name, String campusName, String uniCampusCode) {
    this.code = code;
    this.name = name;
    this.campusName = campusName;
    this.uniCampusCode = uniCampusCode;
  }
}
