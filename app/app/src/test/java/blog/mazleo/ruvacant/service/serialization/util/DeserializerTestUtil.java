package blog.mazleo.ruvacant.service.serialization.util;

import static org.junit.Assert.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import org.junit.Assert;

/** A test util for deserializer tests. */
public final class DeserializerTestUtil {

  public static JsonObject assertIsJsonObject(JsonElement jsonElement) {
    assertTrue(jsonElement.isJsonObject());
    return jsonElement.getAsJsonObject();
  }

  public static JsonArray assertIsJsonArray(JsonElement jsonElement) {
    assertTrue(jsonElement.isJsonArray());
    return jsonElement.getAsJsonArray();
  }

  public static String assertIsMemberString(JsonObject jsonObject, String memberName) {
    assertTrue(jsonObject.get(memberName).isJsonPrimitive());
    return jsonObject.get(memberName).getAsString();
  }

  public static String assertNotNull(String string) {
    Assert.assertNotNull(string);
    return string;
  }

  public static List assertNotNull(List list) {
    Assert.assertNotNull(list);
    return list;
  }
}
