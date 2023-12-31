package blog.mazleo.ruvacant.service.serialization;

import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getLowerCaseStringNonNull;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getStringOrNull;

import blog.mazleo.ruvacant.service.model.RuPlace;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** A JsonDeserializer for RuPlaces. */
public final class RuPlacesDeserializer implements JsonDeserializer<Map<String, RuPlace>> {
  @Override
  public Map<String, RuPlace> deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    if (json.isJsonNull()) {
      return null;
    }

    Map<String, RuPlace> placesMap = new HashMap<>();
    JsonElement allElement = json.getAsJsonObject().get("all");
    if (allElement.isJsonNull()) {
      return null;
    }
    JsonObject allObject = allElement.getAsJsonObject();
    List<String> keys =
        allObject.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
    keys.forEach(
        key -> {
          JsonElement locationElement = allObject.get(key);
          if (locationElement.isJsonNull()) {
            return;
          }
          JsonObject locationObject = locationElement.getAsJsonObject();
          String code = getStringOrNull(locationObject, "building_code");
          if (code != null) {
            String name = getLowerCaseStringNonNull(locationObject, "title");
            placesMap.put(code, new RuPlace(code, name));
          }
        });
    return placesMap;
  }
}
