package blog.mazleo.ruvacant.service.serialization.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/** Util for deserializers. */
public final class DeserializerUtil {
  public static String getStringOrNull(JsonObject jsonObject, String memberName) {
    JsonElement jsonElement = jsonObject.get(memberName);
    return !jsonElement.isJsonNull() ? jsonElement.getAsString().trim() : null;
  }

  public static String getLowerCaseStringOrNull(JsonObject jsonObject, String memberName) {
    JsonElement jsonElement = jsonObject.get(memberName);
    return !jsonElement.isJsonNull() ? jsonElement.getAsString().toLowerCase().trim() : null;
  }
}
