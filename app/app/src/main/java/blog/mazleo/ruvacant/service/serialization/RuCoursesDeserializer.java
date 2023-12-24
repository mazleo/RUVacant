package blog.mazleo.ruvacant.service.serialization;

import blog.mazleo.ruvacant.service.model.RuCourse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/** JsonDeserializer for RuCourses. */
public final class RuCoursesDeserializer implements JsonDeserializer<List<RuCourse>> {
  @Override
  public List<RuCourse> deserialize(
      JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    List<RuCourse> courses = new ArrayList<>();
    json.getAsJsonArray()
        .iterator()
        .forEachRemaining(
            jsonElement -> {
              JsonObject courseObject = jsonElement.getAsJsonObject();
              String code = courseObject.get("courseNumber").getAsString();
              String title = courseObject.get("title").getAsString().toLowerCase();
              String expandedTitle =
                  !courseObject.get("expandedTitle").isJsonNull()
                      ? courseObject.get("expandedTitle").getAsString().toLowerCase().trim()
                      : null;
              String subjectCode = courseObject.get("subject").getAsString();
              String campusCode = courseObject.get("campusCode").getAsString();
              courses.add(new RuCourse(code, title, expandedTitle, subjectCode, campusCode));
            });
    return courses;
  }
}
