package blog.mazleo.ruvacant.service;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import blog.mazleo.ruvacant.model.Locations;
import blog.mazleo.ruvacant.service.deserializer.LocationsDeserializer;
import blog.mazleo.ruvacant.service.webservice.LocationsService;
import blog.mazleo.ruvacant.utils.LocationsUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class LocationsServiceTest {
    @Test
    public void downloadLocationsFromRutgersPlacesTest() {
        Gson gson = new GsonBuilder().registerTypeAdapter(Locations.class, new LocationsDeserializer()).create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LocationsUtil.RUTGERS_PLACES_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        LocationsService service = retrofit.create(LocationsService.class);

        TestObserver observer = new TestObserver(new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(@NonNull Object o) {
                Locations locations = null;
                if (o instanceof Locations) {
                    locations = (Locations) o;
                }
                else {
                    Log.i("APPDEBUG", "Not Locations instance");
                    return;
                }

                Log.i("APPDEBUG", locations.getBuildings().toString());
                Log.i("APPDEBUG", locations.getRooms().toString());

                Assert.assertThat(
                        locations.getBuildings().size(),
                        Matchers.greaterThan(0)
                );
                Assert.assertEquals(
                        new Integer(0),
                        new Integer(locations.getRooms().size())
                );
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        });

        Observable<Locations> locationsObservable = service.retrieveLocationsFromRutgersPlaces();
        locationsObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

        try {
            observer.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        observer.assertComplete();
    }
}
