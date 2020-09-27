package blog.mazleo.ruvacant.service.webservice;

import blog.mazleo.ruvacant.model.Locations;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface LocationsService {
    @GET("2/places.txt")
    public Observable<Locations> retrieveLocationsFromRutgersPlaces();
}
