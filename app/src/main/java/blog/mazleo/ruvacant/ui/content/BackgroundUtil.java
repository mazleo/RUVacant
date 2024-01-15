package blog.mazleo.ruvacant.ui.content;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.info.Campus;

/** Background utility related to the content UI. */
public final class BackgroundUtil {

  public static int getBackgroundColorId(ContentActivityType type, String campusName) {
    if (type.equals(ContentActivityType.UNIVERSITY)) {
      return R.color.red_main;
    } else {
      if (campusName.equals(Campus.LIVINGSTON.getName())) {
        return R.color.blue_main;
      } else if (campusName.equals(Campus.COLLEGE_AVE.getName())) {
        return R.color.yellow_main;
      } else if (campusName.equals(Campus.BUSCH.getName())) {
        return R.color.purple_main;
      } else if (campusName.equals(Campus.DOUGLAS_COOK.getName())) {
        return R.color.green_main;
      } else if (campusName.equals(Campus.DOWNTOWN_NEW_BRUNSWICK.getName())) {
        return R.color.red_main;
      } else if (campusName.equals(Campus.NEWARK.getName())) {
        return R.color.red_main;
      } else if (campusName.equals(Campus.CAMDEN.getName())) {
        return R.color.red_main;
      }
    }
    throw new IllegalArgumentException(String.format("Campus name %s not supported.", campusName));
  }

  public static int getBackgroundColorAccentId(ContentActivityType type, String campusName) {
    if (type.equals(ContentActivityType.UNIVERSITY)) {
      return R.color.red_dark;
    } else {
      if (campusName.equals(Campus.LIVINGSTON.getName())) {
        return R.color.blue_accent;
      } else if (campusName.equals(Campus.COLLEGE_AVE.getName())) {
        return R.color.yellow_accent;
      } else if (campusName.equals(Campus.BUSCH.getName())) {
        return R.color.purple_accent;
      } else if (campusName.equals(Campus.DOUGLAS_COOK.getName())) {
        return R.color.green_accent;
      } else if (campusName.equals(Campus.DOWNTOWN_NEW_BRUNSWICK.getName())) {
        return R.color.red_dark;
      } else if (campusName.equals(Campus.NEWARK.getName())) {
        return R.color.red_dark;
      } else if (campusName.equals(Campus.CAMDEN.getName())) {
        return R.color.red_dark;
      }
    }
    throw new IllegalArgumentException(String.format("Campus name %s not supported.", campusName));
  }

  public static int getForegroundColorId(ContentActivityType type, String campusName) {
    if (type.equals(ContentActivityType.UNIVERSITY)) {
      return R.color.white;
    } else {
      if (campusName.equals(Campus.LIVINGSTON.getName())) {
        return R.color.white;
      } else if (campusName.equals(Campus.COLLEGE_AVE.getName())) {
        return R.color.black;
      } else if (campusName.equals(Campus.BUSCH.getName())) {
        return R.color.white;
      } else if (campusName.equals(Campus.DOUGLAS_COOK.getName())) {
        return R.color.black;
      } else if (campusName.equals(Campus.DOWNTOWN_NEW_BRUNSWICK.getName())) {
        return R.color.white;
      } else if (campusName.equals(Campus.NEWARK.getName())) {
        return R.color.white;
      } else if (campusName.equals(Campus.CAMDEN.getName())) {
        return R.color.white;
      }
    }
    throw new IllegalArgumentException(String.format("Campus name %s not supported.", campusName));
  }

  public static int getBackgroundDrawableId(ContentActivityType contentActivityType) {
    if (contentActivityType.equals(ContentActivityType.UNIVERSITY)) {
      return R.drawable.building;
    } else if (contentActivityType.equals(ContentActivityType.BUILDING)) {
      return R.drawable.room;
    } else if (contentActivityType.equals(ContentActivityType.CLASSROOM)) {
      return R.drawable.college_cap;
    }
    throw new IllegalArgumentException("ContentActivityType not supported.");
  }
}
