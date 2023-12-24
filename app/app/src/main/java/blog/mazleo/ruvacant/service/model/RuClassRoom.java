package blog.mazleo.ruvacant.service.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** A Rutgers classroom. */
@Entity
public final class RuClassRoom {
  /** Format: "BUILDING.ROOM" */
  @PrimaryKey public String code;

  public String roomCode;
  public String buildingCode;
  public String campusName;
  public String uniCampusCode;

  public RuClassRoom(
      String roomCode, String buildingCode, String campusName, String uniCampusCode) {
    code = String.format("%s:%s", buildingCode, roomCode);
    this.roomCode = roomCode;
    this.buildingCode = buildingCode;
    this.campusName = campusName;
    this.uniCampusCode = uniCampusCode;
  }
}
