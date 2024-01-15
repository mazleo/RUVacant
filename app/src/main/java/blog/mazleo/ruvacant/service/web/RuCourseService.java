package blog.mazleo.ruvacant.service.web;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** Retrofit service for courses. */
public interface RuCourseService {
  @GET("oldsoc/courses.json")
  Call<JsonElement> getClassInfos(
      @Query("subject") String subject,
      @Query("semester") String semester,
      @Query("campus") String campus,
      @Query("level") String level);
}
