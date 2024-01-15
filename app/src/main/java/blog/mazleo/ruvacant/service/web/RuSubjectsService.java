package blog.mazleo.ruvacant.service.web;

import blog.mazleo.ruvacant.service.model.RuSubject;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RuSubjectsService {
  @GET("oldsoc/subjects.json")
  Call<List<RuSubject>> getSubjects(
      @Query("semester") String semester,
      @Query("campus") String campus,
      @Query("level") String level);
}
