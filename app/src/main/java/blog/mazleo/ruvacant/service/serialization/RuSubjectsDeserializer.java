package blog.mazleo.ruvacant.service.serialization;

import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getLowerCaseStringNonNull;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerUtil.getStringNonNull;

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
    if (json.isJsonNull()) {
      return null;
    }
    List<RuSubject> subjects = new ArrayList<>();
    json.getAsJsonArray()
        .iterator()
        .forEachRemaining(
            element -> {
              JsonObject jsonObject = element.getAsJsonObject();
              String code = getStringNonNull(jsonObject, "code");
              String name = getLowerCaseStringNonNull(jsonObject, "description");
              subjects.add(new RuSubject(code, name));
            });
    return subjects;
  }
}
