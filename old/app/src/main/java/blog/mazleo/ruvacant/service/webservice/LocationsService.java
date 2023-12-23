package blog.mazleo.ruvacant.service.webservice;

import blog.mazleo.ruvacant.model.Locations;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationsService {
    @GET("2/places.txt")
    public Observable<Locations> retrieveLocationsFromRutgersPlaces();
    @GET("oldsoc/courses.json")
    public Observable<Locations> retrieveLocationsFromRutgersCourses(@Query("subject") String subjectCode, @Query("semester") String semesterMonthYear, @Query("campus") String schoolCampusCode, @Query("level") String levelCode);
}
