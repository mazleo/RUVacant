package blog.mazleo.ruvacant.service.serialization.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

/** Util for deserializers. */
public final class DeserializerUtil {

  public static String getStringNonNull(JsonObject jsonObject, String memberName) {
    return jsonObject.get(memberName).getAsString().trim();
  }

  public static String getLowerCaseStringNonNull(JsonObject jsonObject, String memberName) {
    return jsonObject.get(memberName).getAsString().toLowerCase().trim();
  }

  @Nullable
  public static String getStringOrNull(JsonObject jsonObject, String memberName) {
    JsonElement jsonElement = jsonObject.get(memberName);
    return !jsonElement.isJsonNull() ? jsonElement.getAsString().trim() : null;
  }

  @Nullable
  public static String getLowerCaseStringOrNull(JsonObject jsonObject, String memberName) {
    JsonElement jsonElement = jsonObject.get(memberName);
    return !jsonElement.isJsonNull() ? jsonElement.getAsString().toLowerCase().trim() : null;
  }
}
