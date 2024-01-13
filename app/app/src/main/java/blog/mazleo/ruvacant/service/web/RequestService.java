package blog.mazleo.ruvacant.service.web;

import android.util.Log;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.info.UniversityCampus;
import blog.mazleo.ruvacant.info.UniversityLevel;
import blog.mazleo.ruvacant.info.UniversitySemesterUtil;
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
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/** Fetches data using Retrofit requests. */
public final class RequestService {

  private static final String COURSES_URL = "https://sis.rutgers.edu/";
  private static final int MAX_NUM_RETRIES = 5;
  private static final int NUM_SEMESTERS_REQUEST = 3;
  private static final int NUM_CAMPUSES = UniversityCampus.getAllCampuses().size();
  private static final int NUM_LEVELS = UniversityLevel.getAllLevels().size();
  private static final int NUM_SUBJECT_REQUESTS = NUM_SEMESTERS_REQUEST * NUM_CAMPUSES * NUM_LEVELS;

  private static Retrofit createRetrofit(
      Class outputClass, JsonDeserializer deserializerInstance, String baseUrl) {
    Gson gson = new GsonBuilder().registerTypeAdapter(outputClass, deserializerInstance).create();
    return new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }

  private void handleError(
      int currentNumRetries,
      Call call,
      Callback callback,
      String message,
      Throwable t,
      List<Call> calls) {
    if (currentNumRetries <= MAX_NUM_RETRIES) {
      Log.d(appName, message + " Trying again...");
      printStackTrace(t.getStackTrace(), appName);
      call.clone().enqueue(callback);
    } else {
      Log.d(appName, message);
      printStackTrace(t.getStackTrace(), appName);
      cancelCalls(calls);
      // TODO: Handle error.
    }
  }

  private static void cancelCalls(List<Call> cachedCalls) {
    for (Call call : cachedCalls) {
      if (!call.isCanceled()) {
        call.cancel();
      }
    }
  }

  private static void printStackTrace(StackTraceElement[] stackTraceElements, String appName) {
    for (StackTraceElement element : stackTraceElements) {
      Log.d(appName, element.toString());
    }
  }

  private final String appName;
  private final ApplicationStateManager stateManager;
  private final SharedApplicationData sharedApplicationData;
  private final ExecutorService executorService;

  private final Set<String> cachedSubjects = new HashSet<>();
  private final Set<String> cachedCourses = new HashSet<>();
  private final Set<String> cachedMeetings = new HashSet<>();
  private final Set<String> cachedBuildings = new HashSet<>();
  private final Set<String> cachedClassrooms = new HashSet<>();
  private final Map<String, Integer> subjectsRetries = new HashMap<>();
  private final Map<String, Integer> classInfosRetries = new HashMap<>();
  private final List<Call> cachedCalls = new ArrayList<>();
  private int coursesNumSubjectsRetrieved = 0;
  private int subjectRequests = 0;

  private final Callback<List<RuSubject>> subjectsResponseCallback =
      new Callback<List<RuSubject>>() {
        @Override
        public void onResponse(Call<List<RuSubject>> call, Response<List<RuSubject>> response) {
          subjectRequests++;
          List<RuSubject> subjects = response.body();
          if (subjects != null) {
            cacheSubjects(subjects);
          }
          if (subjectRequests == NUM_SUBJECT_REQUESTS) {
            int numSubjects =
                ((List<RuSubject>)
                        sharedApplicationData.getData(ApplicationData.SUBJECTS_LIST_CACHE.getTag()))
                    .size();
            sharedApplicationData.addData(ApplicationData.SUBJECTS_NUM.getTag(), numSubjects);
            stateManager.exitState(ApplicationState.SUBJECTS_REQUEST.getState());
            stateManager.enterState(ApplicationState.SUBJECTS_REQUESTED.getState());
          }
        }

        @Override
        public void onFailure(Call<List<RuSubject>> call, Throwable t) {
          HttpUrl url = call.request().url();
          String retryKey =
              String.format(
                  "%s.%s.%s",
                  url.queryParameter("semester"),
                  url.queryParameter("campus"),
                  url.queryParameter("level"));
          if (!subjectsRetries.containsKey(retryKey)) {
            subjectsRetries.put(retryKey, 0);
          }
          subjectsRetries.replace(retryKey, subjectsRetries.get(retryKey) + 1);
          handleError(
              subjectsRetries.get(retryKey),
              call,
              subjectsResponseCallback,
              "An error occured while attempting to request course subject information.",
              t,
              cachedCalls);
        }
      };

  private final Callback<JsonElement> classInfosResponseCallback =
      new Callback<JsonElement>() {
        @Override
        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
          executorService.execute(() -> handleClassInfosResponse(response.body(), call));
        }

        @Override
        public void onFailure(Call<JsonElement> call, Throwable t) {
          HttpUrl url = call.request().url();
          String retryKey =
              String.format(
                  "%s.%s.%s.%s",
                  url.queryParameter("subject"),
                  url.queryParameter("semester"),
                  url.queryParameter("campus"),
                  url.queryParameter("level"));
          if (!classInfosRetries.containsKey(retryKey)) {
            classInfosRetries.put(retryKey, 0);
          }
          classInfosRetries.replace(retryKey, classInfosRetries.get(retryKey) + 1);
          handleError(
              classInfosRetries.get(retryKey),
              call,
              classInfosResponseCallback,
              "An error occured while requesting course information.",
              t,
              cachedCalls);
        }
      };

  @Inject
  RequestService(
      @AppName String appName,
      ApplicationStateManager stateManager,
      SharedApplicationData sharedApplicationData,
      ExecutorService executorService) {
    this.appName = appName;
    this.stateManager = stateManager;
    this.sharedApplicationData = sharedApplicationData;
    this.executorService = executorService;
  }

  public synchronized void initiateSubjectsRequest() {
    Retrofit retrofit = createRetrofit(List.class, new RuSubjectsDeserializer(), COURSES_URL);
    RuSubjectsService subjectsService = retrofit.create(RuSubjectsService.class);

    List<String> semesters = new ArrayList<>();
    semesters.add(UniversitySemesterUtil.getCurrentSemesterCode());
    semesters.add(UniversitySemesterUtil.getPreviousSemesterCode());
    semesters.add(UniversitySemesterUtil.getPreviousPreviousSemesterCode());
    for (String semester : semesters) {
      for (UniversityCampus campus : UniversityCampus.getAllCampuses()) {
        for (UniversityLevel level : UniversityLevel.getAllLevels()) {
          Call call = subjectsService.getSubjects(semester, campus.getCode(), level.getCode());
          cachedCalls.add(call);
          call.enqueue(subjectsResponseCallback);
        }
      }
    }
  }

  public synchronized void initiateClassInfosRequests() {
    if (!sharedApplicationData.containsData(ApplicationData.SUBJECTS_LIST_CACHE.getTag())) {
      throw new IllegalStateException(
          "Subjects should be stored if calling initiateClassInfosRequests.");
    }
    List<RuSubject> subjects =
        (List<RuSubject>)
            sharedApplicationData.getData(ApplicationData.SUBJECTS_LIST_CACHE.getTag());
    Retrofit retrofit =
        new Retrofit.Builder()
            .baseUrl(COURSES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RuCourseService courseService = retrofit.create(RuCourseService.class);
    List<String> semesters = new ArrayList<>();
    semesters.add(UniversitySemesterUtil.getCurrentSemesterCode());
    semesters.add(UniversitySemesterUtil.getPreviousSemesterCode());
    semesters.add(UniversitySemesterUtil.getPreviousPreviousSemesterCode());
    for (String semester : semesters) {
      for (UniversityCampus campus : UniversityCampus.getAllCampuses()) {
        for (UniversityLevel level : UniversityLevel.getAllLevels()) {
          for (RuSubject subject : subjects) {
            Call call =
                courseService.getClassInfos(
                    subject.code, semester, campus.getCode(), level.getCode());
            cachedCalls.add(call);
            call.enqueue(classInfosResponseCallback);
          }
        }
      }
    }
  }

  private void handleClassInfosResponse(JsonElement jsonElement, Call call) {
    coursesNumSubjectsRetrieved++;
    RuClassInfosDeserializer deserializer = new RuClassInfosDeserializer();
    RuClassInfos classInfosResponse =
        deserializer.deserialize(jsonElement, /* typeOfT= */ null, /* context= */ null);
    if (classInfosResponse != null) {
      cacheClassInfos(classInfosResponse, call.request().url());
    }
    Integer numSubjects =
        (Integer) sharedApplicationData.getData(ApplicationData.SUBJECTS_NUM.getTag());
    if (coursesNumSubjectsRetrieved == numSubjects * NUM_SUBJECT_REQUESTS) {
      stateManager.exitState(ApplicationState.COURSES_REQUEST.getState());
      stateManager.enterState(ApplicationState.COURSES_REQUESTED.getState());
    }
  }

  private synchronized void cacheSubjects(List<RuSubject> subjectsResponse) {
    List<RuSubject> subjects = new ArrayList<>();
    if (sharedApplicationData.containsData(ApplicationData.SUBJECTS_LIST_CACHE.getTag())) {
      subjects =
          (List<RuSubject>)
              sharedApplicationData.getData(ApplicationData.SUBJECTS_LIST_CACHE.getTag());
    }
    for (RuSubject subjectResponse : subjectsResponse) {
      if (!cachedSubjects.contains(subjectResponse.code)) {
        cachedSubjects.add(subjectResponse.code);
        subjects.add(subjectResponse);
      }
    }
    sharedApplicationData.replaceData(ApplicationData.SUBJECTS_LIST_CACHE.getTag(), subjects);
  }

  private synchronized void cacheClassInfos(RuClassInfos classInfosResponse, HttpUrl url) {
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
    cacheCourses(cachedClassInfos, classInfosResponse, url);
    cacheMeetings(cachedClassInfos, classInfosResponse, url);
    cacheBuildings(cachedClassInfos, classInfosResponse, url);
    cacheClassrooms(cachedClassInfos, classInfosResponse, url);
    sharedApplicationData.addData(ApplicationData.CLASS_INFOS_CACHE.getTag(), cachedClassInfos);
  }

  private void cacheCourses(
      RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse, HttpUrl url) {
    for (RuCourse courseResponse : classInfosResponse.courses) {
      if (!cachedCourses.contains(courseResponse.key)) {
        cachedCourses.add(courseResponse.key);
        courseResponse.semesterCode = getSemesterCodeFromUrl(url);
        courseResponse.uniCampusCode = getCampusCodeFromUrl(url);
        courseResponse.levelCode = getLevelCodeFromUrl(url);
        cachedClassInfos.courses.add(courseResponse);
      }
    }
  }

  private void cacheMeetings(
      RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse, HttpUrl url) {
    for (RuMeeting meetingResponse : classInfosResponse.meetings) {
      if (!cachedMeetings.contains(meetingResponse.key)) {
        cachedMeetings.add(meetingResponse.key);
        meetingResponse.semesterCode = getSemesterCodeFromUrl(url);
        meetingResponse.uniCampusCode = getCampusCodeFromUrl(url);
        meetingResponse.levelCode = getLevelCodeFromUrl(url);
        cachedClassInfos.meetings.add(meetingResponse);
      }
    }
  }

  private void cacheBuildings(
      RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse, HttpUrl url) {
    for (RuBuilding buildingResponse : classInfosResponse.buildings) {
      if (!cachedBuildings.contains(buildingResponse.key)) {
        cachedBuildings.add(buildingResponse.key);
        buildingResponse.uniCampusCode = getCampusCodeFromUrl(url);
        cachedClassInfos.buildings.add(buildingResponse);
      }
    }
  }

  private void cacheClassrooms(
      RuClassInfos cachedClassInfos, RuClassInfos classInfosResponse, HttpUrl url) {
    for (RuClassroom classroomResponse : classInfosResponse.classrooms) {
      if (!cachedClassrooms.contains(classroomResponse.key)) {
        cachedClassrooms.add(classroomResponse.key);
        classroomResponse.uniCampusCode = getCampusCodeFromUrl(url);
        cachedClassInfos.classrooms.add(classroomResponse);
      }
    }
  }

  private String getSemesterCodeFromUrl(HttpUrl url) {
    return url.queryParameter("semester");
  }

  private String getCampusCodeFromUrl(HttpUrl url) {
    return url.queryParameter("campus");
  }

  private String getLevelCodeFromUrl(HttpUrl url) {
    return url.queryParameter("level");
  }
}
