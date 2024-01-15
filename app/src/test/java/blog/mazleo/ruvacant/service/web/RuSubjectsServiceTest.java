package blog.mazleo.ruvacant.service.web;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import blog.mazleo.ruvacant.service.model.RuSubject;
import blog.mazleo.ruvacant.service.serialization.RuSubjectsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Test cases for RuSubjectsService. */
public final class RuSubjectsServiceTest {

  @Test
  public void getSubjects() {
    Gson gson =
        new GsonBuilder().registerTypeAdapter(List.class, new RuSubjectsDeserializer()).create();
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl("https://sis.rutgers.edu")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    RuSubjectsService subjectsService = retrofit.create(RuSubjectsService.class);
    List<RuSubject> subjects = null;
    try {
      subjects = subjectsService.getSubjects("92023", "NB", "U").execute().body();
    } catch (IOException exception) {
      fail();
    }
    assertNotNull(subjects);
    assertTrue(subjects.size() != 0);
  }
}
