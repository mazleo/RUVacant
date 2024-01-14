package blog.mazleo.ruvacant.ui.base;

import blog.mazleo.ruvacant.info.Campus;
import blog.mazleo.ruvacant.service.model.RuBuilding;

/** Color util regarding Rutgers campuses. */
public final class CampusCardColorUtil {
  public static CardValueColor getColorThemeOfBuilding(RuBuilding building) {
    if (building.campusName.equals(Campus.LIVINGSTON.getResponseName())) {
      return CardValueColor.BLUE;
    } else if (building.campusName.equals(Campus.COLLEGE_AVE.getResponseName())) {
      return CardValueColor.YELLOW;
    } else if (building.campusName.equals(Campus.BUSCH.getResponseName())) {
      return CardValueColor.PURPLE;
    } else if (building.campusName.equals(Campus.DOUGLAS_COOK.getResponseName())) {
      return CardValueColor.GREEN;
    } else if (building.campusName.equals(Campus.DOWNTOWN_NEW_BRUNSWICK.getResponseName())) {
      return CardValueColor.RED;
    } else if (building.campusName.equals(Campus.NEWARK.getResponseName())) {
      return CardValueColor.RED;
    } else if (building.campusName.equals(Campus.CAMDEN.getResponseName())) {
      return CardValueColor.RED;
    }
    throw new IllegalArgumentException("Building campus not supported.");
  }
}
