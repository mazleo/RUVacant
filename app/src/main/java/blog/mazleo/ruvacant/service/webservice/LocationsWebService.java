package blog.mazleo.ruvacant.service.webservice;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.Locations;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.repository.LocationsRepository;
import blog.mazleo.ruvacant.service.deserializer.LocationsDeserializer;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import blog.mazleo.ruvacant.utils.LocationsUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.parallel.ParallelFlowable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationsWebService {
    private LocationsRepository locationsRepository;
    private Locations locations;
    private List<Disposable> disposables;
    private int numObservables;
    private int countCompletedObservables;

    public LocationsWebService(LocationsRepository locationsRepository) {
        this.locationsRepository = locationsRepository;
        this.locations = new Locations();
        this.disposables = new ArrayList<>();
    }

    public void downloadLocationsFromRutgersPlaces() {
        OkHttpClient client = getClient(true);
        Gson gson = getGson();
        Retrofit retrofit = getRetrofit(LocationsUtil.RUTGERS_PLACES_BASE_URL, client, gson);

        LocationsService locationsService = retrofit.create(LocationsService.class);
        Observable<Locations> locationsObservable = locationsService.retrieveLocationsFromRutgersPlaces();

        locationsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Locations>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull Locations retrievedLocations) {
                        appendLocations(retrievedLocations);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        passError(e);
                    }

                    @Override
                    public void onComplete() {
                        locationsRepository.onDownloadLocationsFromRutgersPlacesComplete(locations);
                    }
                });
    }

    public void downloadLocationsFromRutgersCourses(List<Subject> subjects, Option option) {
        OkHttpClient client = getClient(false);
        Gson gson = getGson();
        Retrofit retrofit = getRetrofit(CoursesUtil.RUTGERS_SIS_BASE_URL, client, gson);

        LocationsService locationsService = retrofit.create(LocationsService.class);

        List<Observable> observables = new ArrayList<>();
        populateLocationsObservableListFromSubjects(subjects, option, locationsService, observables);

//        numObservables = observables.size();
//        countCompletedObservables = 0;
//
//        for (Observable<Locations> observable : observables) {
//            observable
//                    .subscribeOn(Schedulers.computation())
//                    .doOnSubscribe(
//                            d -> {
//                                addDisposable(d);
//                            }
//                    )
//                    .doOnNext(
//                            newLocations -> {
//                                Log.i("APPDEBUG", "ONNEXT");
//                                Log.i("APPDEBUG", "ID: " + Thread.currentThread().getId());
//                                appendLocations(newLocations);
//                            }
//                    )
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnError(
//                            e -> {
//                                Log.i("APPDEBUG", "ONERR");
//                                Log.i("APPDEBUG", e.toString());
//                                Log.i("APPDEBUG", "NUM: " + countCompletedObservables);
//                                e.printStackTrace();
////                                locationsRepository.onError(e);
////                                cleanUp();
//                            }
//                    )
//                    .doOnComplete(
//                            () -> {
//                                incrementCountCompletedObservables();
//                                if (isLocationsRetrievalComplete()) {
//                                    locationsRepository.onDownloadLocationsFromRutgersCoursesComplete(locations);
//                                    cleanUp();
//                                    Log.i("APPDEBUG", "ONCOMP");
//                                }
//                            }
//                    )
//                    .subscribe(l->{},e->{});
//        }

        Observable<Locations> finalObservable = Observable.mergeArray((Observable[]) observables.toArray(new Observable[observables.size()]));

        finalObservable
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(d -> disposable = d)
                .doOnNext(
                        newLocations -> {
                            appendLocations(newLocations);
                        }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        l -> {},
                        e -> {
                            passError(e);
                            cleanUp();
                        },
                        () -> {
                            locationsRepository.onDownloadLocationsFromRutgersCoursesComplete(locations);
                            cleanUp();
                        }
                );
    }

    private synchronized void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }

    private synchronized void incrementCountCompletedObservables() {
        countCompletedObservables++;
    }

    private synchronized boolean isLocationsRetrievalComplete() {
        return numObservables == countCompletedObservables;
    }

    private void populateLocationsObservableListFromSubjects(List<Subject> subjects, Option option, LocationsService locationsService, List<Observable> observables) {
        for (Subject subject : subjects) {
            Observable<Locations> locationsObservable = locationsService.retrieveLocationsFromRutgersCourses(
                    subject.getCode(),
                    new StringBuilder()
                            .append(option.getSemesterMonth())
                            .append(option.getSemesterYear())
                            .toString(),
                    option.getSchoolCampusCode(),
                    option.getLevelCode()
            );
            observables.add(locationsObservable);
        }
    }

    private synchronized void appendLocations(Locations newLocations) {
        locations.getBuildings().addAll(newLocations.getBuildings());
        locations.getRooms().addAll(newLocations.getRooms());
    }

    private void passError(Throwable e) {
        locationsRepository.onError(e);
    }

    public void cleanUp() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    disposable = null;
                }
            }
        }

        locationsRepository = null;
        locations = null;
        disposables = null;
    }

    private static Retrofit getRetrofit(String baseUrl, OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                    .registerTypeAdapter(Locations.class, new LocationsDeserializer())
                    .create();
    }

    private static OkHttpClient getClient(boolean addConnectionSpecs) {
        if (addConnectionSpecs) {
            return new OkHttpClient.Builder()
                    .callTimeout(10, TimeUnit.SECONDS)
                    .connectionSpecs(Arrays.asList(ConnectionSpec.COMPATIBLE_TLS))
                    .build();
        }
        else {
            return new OkHttpClient.Builder()
                    .callTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
    }
}
