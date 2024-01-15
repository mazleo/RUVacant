package blog.mazleo.ruvacant.service.serialization;

import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertIsJsonObject;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertIsMemberString;
import static blog.mazleo.ruvacant.service.serialization.util.DeserializerTestUtil.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import blog.mazleo.ruvacant.service.model.RuSubject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.List;
import org.junit.Test;

/** Test cases for RuSubjectsDeserializer. */
public final class RuSubjectsDeserializerTest {
  @Test
  public void deserialize_expectedStructure() {
    String jsonString =
        "[{\"description\":\"ACCOUNTING\",\"code\":\"010\",\"modifiedDescription\":false}]";
    Gson gson = new Gson();
    JsonArray jsonArray = gson.fromJson(jsonString, JsonArray.class);
    JsonObject subjectObject = assertIsJsonObject(jsonArray.get(0));
    assertIsMemberString(subjectObject, "description");
    assertIsMemberString(subjectObject, "code");
    RuSubjectsDeserializer deserializer = new RuSubjectsDeserializer();
    List<RuSubject> subjects =
        deserializer.deserialize(jsonArray, /* typeOfT= */ null, /* context= */ null);
    assertNotNull(subjects);
    assertTrue(subjects.size() != 0);
    RuSubject subject = subjects.get(0);
    assertNotNull(subject.code);
    assertNotNull(subject.name);
  }

  @Test
  public void deserialize_null() {
    JsonNull jsonNull = JsonNull.INSTANCE;
    RuSubjectsDeserializer deserializer = new RuSubjectsDeserializer();
    assertNull(deserializer.deserialize(jsonNull, /* typeOfT= */ null, /* context= */ null));
  }
}
