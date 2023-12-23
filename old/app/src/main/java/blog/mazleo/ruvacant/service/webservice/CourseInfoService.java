package blog.mazleo.ruvacant.service.webservice;

import blog.mazleo.ruvacant.model.CourseInfoCollection;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CourseInfoService {
    @GET("oldsoc/courses.json")
    public Observable<CourseInfoCollection> retrieveCourseInfos(@Query("subject") String subjectCode, @Query("semester") String semesterMonthYear, @Query("campus") String campusCode, @Query("level") String levelCode);
}
