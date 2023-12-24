package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import javax.annotation.Nullable;

/** Model for a Rutgers Building. */
@Entity
public final class RuBuilding {

  @PrimaryKey public String code;
  @Nullable public String name;
  public String campusName;
  public String uniCampusCode;

  @Ignore
  public RuBuilding(String code, @Nullable String name, String campusName, String uniCampusCode) {
    this.code = code;
    this.name = name;
    this.campusName = campusName;
    this.uniCampusCode = uniCampusCode;
  }
}
