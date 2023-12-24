package blog.mazleo.ruvacant.service.web;

import android.util.Log;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuSubject;
import blog.mazleo.ruvacant.service.serialization.RuCoursesDeserializer;
import blog.mazleo.ruvacant.service.serialization.RuSubjectsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Fetches data using Retrofit requests. */
public final class RequestService {

  private static final String COURSES_URL = "https://sis.rutgers.edu/";

  private final Callback<List<RuSubject>> subjectsDeserializationCallback =
      new Callback<List<RuSubject>>() {
        @Override
        public void onResponse(Call<List<RuSubject>> call, Response<List<RuSubject>> response) {
          List<RuSubject> subjects = response.body();
          initiateCoursesRequests(subjects);
        }

        @Override
        public void onFailure(Call<List<RuSubject>> call, Throwable t) {
          // TODO: Handle this error.
          Log.d("TESTING", t.getMessage());
        }
      };

  private final Callback<List<RuCourse>> coursesDeserializationCallback =
      new Callback<List<RuCourse>>() {
        @Override
        public void onResponse(Call<List<RuCourse>> call, Response<List<RuCourse>> response) {
          List<RuCourse> courses = response.body();
          // TODO: Handle response.
        }

        @Override
        public void onFailure(Call<List<RuCourse>> call, Throwable t) {
          // TODO: Handle failure.
          Log.d("TESTING", t.getMessage());
        }
      };

  @Inject
  RequestService() {}

  public void initiateDataRequest() {
    initiateSubjectsRequest();
  }

  private void initiateSubjectsRequest() {
    Gson gson =
        new GsonBuilder().registerTypeAdapter(List.class, new RuSubjectsDeserializer()).create();
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(COURSES_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    RuSubjectsService subjectsService = retrofit.create(RuSubjectsService.class);
    // TODO: Use variables for subject queries.
    subjectsService
        .getSubjects(/* semester= */ "92023", /* campus= */ "NB", /* level= */ "U")
        .enqueue(subjectsDeserializationCallback);
  }

  private void initiateCoursesRequests(List<RuSubject> subjects) {
    Gson gson =
        new GsonBuilder().registerTypeAdapter(List.class, new RuCoursesDeserializer()).create();
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(COURSES_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    RuCourseService courseService = retrofit.create(RuCourseService.class);
    for (RuSubject subject : subjects) {
      // TODO: User actual variables.
      courseService
          .getCourses(subject.code, /* semester= */ "92023", /* campus= */ "NB", /* level= */ "U")
          .enqueue(coursesDeserializationCallback);
    }
  }
}
