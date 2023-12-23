package blog.mazleo.ruvacant.service.webservice;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SubjectsService {
    @GET("oldsoc/subjects.json")
    public Observable<List> retrieveSubjects(@Query("semester") String semesterMonthYear, @Query("campus") String schoolCampusCode, @Query("level") String levelCode);
}
