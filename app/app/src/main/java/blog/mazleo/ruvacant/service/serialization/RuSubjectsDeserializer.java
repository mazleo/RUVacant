package blog.mazleo.ruvacant.service.serialization;

import blog.mazleo.ruvacant.service.model.RuSubject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** A Json response deserializer into an RuSubject. */
public final class RuSubjectsDeserializer implements JsonDeserializer<List<RuSubject>> {
  @Override
  public List<RuSubject> deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    List<RuSubject> subjects = new ArrayList<>();
    json.getAsJsonArray()
        .iterator()
        .forEachRemaining(
            element -> {
              JsonObject jsonObject = element.getAsJsonObject();
              String code = jsonObject.get("code").getAsString();
              String name = jsonObject.get("description").getAsString().toLowerCase();
              subjects.add(new RuSubject(code, name));
            });
    return subjects;
  }
}
