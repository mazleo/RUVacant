package blog.mazleo.ruvacant.service.web;

import blog.mazleo.ruvacant.service.model.RuCourse;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** Retrofit service for courses. */
public interface RuCourseService {
  @GET("oldsoc/courses.json")
  Call<List<RuCourse>> getCourses(
      @Query("subject") String subject,
      @Query("semester") String semester,
      @Query("campus") String campus,
      @Query("level") String level);
}
