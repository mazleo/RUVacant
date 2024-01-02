package blog.mazleo.ruvacant.service.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.serialization.RuClassInfosDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Test cases for RuCourseService. */
public final class RuCourseServiceTest {

  @Test
  public void getClassInfos() {
    String baseUrl = "https://sis.rutgers.edu/";
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(RuClassInfos.class, new RuClassInfosDeserializer())
            .create();
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    RuCourseService courseService = retrofit.create(RuCourseService.class);
    RuClassInfos classInfos = null;
    try {
      classInfos = courseService.getClassInfos("198", "92023", "NB", "U").execute().body();
    } catch (IOException exception) {
      fail();
    }
    assertNotNull(classInfos);
    assertNotNull(classInfos.courses);
    assertNotNull(classInfos.meetings);
    assertNotNull(classInfos.buildings);
    assertNotNull(classInfos.classrooms);
    assertTrue(classInfos.courses.size() != 0);
    assertTrue(classInfos.meetings.size() != 0);
    assertTrue(classInfos.buildings.size() != 0);
    assertTrue(classInfos.classrooms.size() != 0);
  }
}
