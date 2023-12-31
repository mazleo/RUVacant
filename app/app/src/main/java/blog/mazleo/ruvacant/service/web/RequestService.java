package blog.mazleo.ruvacant.service.web;

import android.util.Log;
import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import blog.mazleo.ruvacant.service.model.RuCourse;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import blog.mazleo.ruvacant.service.model.RuSubject;
import blog.mazleo.ruvacant.service.serialization.RuClassInfosDeserializer;
import blog.mazleo.ruvacant.service.serialization.RuSubjectsDeserializer;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Fetches data using Retrofit requests. */
public final class RequestService {

  private static final String COURSES_URL = "https://sis.rutgers.edu/";
  private static final int MAX_NUM_RETRIES = 5;

  private final ApplicationStateManager stateManager;
  private final SharedApplicationData sharedApplicationData;

  private final Set<String> cachedCourses = new HashSet<>();
  private final Set<String> cachedMeetings = new HashSet<>();
  private final Set<String> cachedBuildings = new HashSet<>();
  private final Set<String> cachedClassrooms = new HashSet<>();
  private int coursesNumSubjectsRetrieved = 0;
  private int subjectsRetries = 0;
  private int coursesRetries = 0;

  private final Callback<List<RuSubject>> subjectsResponseCallback =
      new Callback<List<RuSubject>>() {
        @Override
        public void onResponse(Call<List<RuSubject>> call, Response<List<RuSubject>> response) {
          List<RuSubject> subjects = response.body();
          sharedApplicationData.addData(ApplicationData.SUBJECTS_LIST_CACHE.getTag(), subjects);
          sharedApplicationData.addData(ApplicationData.SUBJECTS_NUM.getTag(), subjects.size());
          stateManager.exitState(ApplicationState.SUBJECTS_REQUEST.getState());
          stateManager.enterState(ApplicationState.SUBJECTS_REQUESTED.getState());
        }

        @Override
        public void onFailure(Call<List<RuSubject>> call, Throwable t) {
          subjectsRetries++;
          if (subjectsRetries <= MAX_NUM_RETRIES) {
            Log.d(
                "RuVacant",
                "An error occured while attempting to request course subject information. Trying"
                    + " again...");
            call.clone().enqueue(subjectsResponseCallback);
          } else {
            Log.d(
                "RuVacant",
                "An error occured while attempting to request course subject information.");
            // TODO: Handle error.
          }
        }
      };

  private final Callback<RuClassInfos> classInfosResponseCallback =
      new Callback<RuClassInfos>() {
        @Override
        public void onResponse(Call<RuClassInfos> call, Response<RuClassInfos> response) {
          RuClassInfos classInfosResponse = response.body();
          if (classInfosResponse != null) {
            cacheClassInfos(classInfosResponse);
          }
          coursesNumSubjectsRetrieved++;
          Integer numSubjects =
              (Integer) sharedApplicationData.getData(ApplicationData.SUBJECTS_NUM.getTag());
          if (coursesNumSubjectsRetrieved == numSubjects) {
            stateManager.exitState(ApplicationState.COURSES_REQUEST.getState());
            stateManager.enterState(ApplicationState.COURSES_REQUESTED.getState());
          }
        }

        @Override
        public void onFailure(Call<RuClassInfos> call, Throwable t) {
          coursesRetries++;
          if (coursesRetries <= MAX_NUM_RETRIES) {
            Log.d(
                "RuVacant",
                "An error occured while requesting course information. Trying again...");
            call.clone().enqueue(classInfosResponseCallback);
          } else {
            // TODO: Handle failure.
            Log.d("RuVacant", "An error occured while requesting course information.");
          }
        }
      };

  @Inject
  RequestService(
      ApplicationStateManager stateManager, SharedApplicationData sharedApplicationData) {
    this.stateManager = stateManager;
    this.sharedApplicationData = sharedApplicationData;
  }

  public void initiateSubjectsRequest() {
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
        .enqueue(subjectsResponseCallback);
  }

  public void initiateClassInfosRequests() {
    if (!sharedApplicationData.containsData(ApplicationData.SUBJECTS_LIST_CACHE.getTag())) {
      throw new IllegalStateException(
          "Subjects should be stored if calling initiateClassInfosRequests.");
    }
    List<RuSubject> subjects =
        (List<RuSubject>)
            sharedApplicationData.getData(ApplicationData.SUBJECTS_LIST_CACHE.getTag());
    Gson gson =
        new GsonBuilder()
            .registerTypeAdapter(RuClassInfos.class, new RuClassInfosDeserializer())
            .create();
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(COURSES_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    RuCourseService courseService = retrofit.create(RuCourseService.class);
    for (RuSubject subject : subjects) {
      // TODO: User actual variables.
      courseService
          .getClassInfos(
              subject.code, /* semester= */ "92023", /* campus= */ "NB", /* level= */ "U")
          .enqueue(classInfosResponseCallback);
    }
  }

  private synchronized void cacheClassInfos(RuClassInfos classInfosResponse) {
    RuClassInfos cachedClassInfos;
    if (sharedApplicationData.containsData(ApplicationData.CLASS_INFOS_CACHE.getTag())) {
      cachedClassInfos =
          (RuClassInfos) sharedApplicationData.getData(ApplicationData.CLASS_INFOS_CACHE.getTag());
      sharedApplicationData.removeData(ApplicationData.CLASS_INFOS_CACHE.getTag());
    } else {
      cachedClassInfos =
          new RuClassInfos(
              /* courses= */ new ArrayList<>(),
              /* meetings= */ new ArrayList<>(),
              /* buildings= */ new ArrayList<>(),
              /* classrooms= */ new ArrayList<>());
    }
    cacheCourses(cachedClassInfos, classInfosResponse);
    cacheMeetings(cachedClassInfos, classInfosResponse);
    cacheBuildings(cachedClassInfos, classInfosResponse);
    cacheClassrooms(cachedClassInfos, classInfosResponse);
    sharedApplicationData.addData(ApplicationData.CLASS_INFOS_CACHE.getTag(), cachedClassInfos);
  }

  private void cacheCourses(RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse) {
    for (RuCourse courseResponse : classInfosResponse.courses) {
      if (!cachedCourses.contains(courseResponse.key)) {
        cachedCourses.add(courseResponse.key);
        cachedClassInfos.courses.add(courseResponse);
      }
    }
  }

  private void cacheMeetings(RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse) {
    for (RuMeeting meetingResponse : classInfosResponse.meetings) {
      if (!cachedMeetings.contains(meetingResponse.key)) {
        cachedMeetings.add(meetingResponse.key);
        cachedClassInfos.meetings.add(meetingResponse);
      }
    }
  }

  private void cacheBuildings(RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse) {
    for (RuBuilding buildingResponse : classInfosResponse.buildings) {
      if (!cachedBuildings.contains(buildingResponse.code)) {
        cachedBuildings.add(buildingResponse.code);
        cachedClassInfos.buildings.add(buildingResponse);
      }
    }
  }

  private void cacheClassrooms(RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse) {
    for (RuClassroom classroomResponse : classInfosResponse.classrooms) {
      if (!cachedClassrooms.contains(classroomResponse.key)) {
        cachedClassrooms.add(classroomResponse.key);
        cachedClassInfos.classrooms.add(classroomResponse);
      }
    }
  }
}
