package blog.mazleo.ruvacant.service.webservice;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.repository.CoursesRepository;
import blog.mazleo.ruvacant.service.deserializer.CourseInfoDeserializer;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseInfoWebService implements Observer<CourseInfoCollection> {
    private CoursesRepository coursesRepository;
    private List<CourseInfoCollection> courseInfos;
    private Disposable disposable;

    public CourseInfoWebService(CoursesRepository coursesRepository) {
        this.coursesRepository = coursesRepository;
        this.courseInfos = new ArrayList<>();
        this.disposable = null;
    }

    public void downloadCourseInfos(List<Subject> subjects, Option selectedOptions) {
        Log.d("APPDEBUG", "Beginning course download...");
        OkHttpClient client = getClient();
        Gson gson = getGson();
        Retrofit retrofit = getRetrofit(client, gson);
        CourseInfoService courseInfoService = retrofit.create(CourseInfoService.class);

        List<Observable> observablesList = getNewPopulatedObservablesList(subjects, selectedOptions, courseInfoService);
        Observable<CourseInfoCollection> finalObservable = Observable.mergeArray(observablesList.toArray(new Observable[observablesList.size()]));

        if (finalObservable != null) {
            finalObservable
                    .subscribeOn(Schedulers.computation())
                    .flatMap(courseInfoCollection -> Observable.just(courseInfoCollection).subscribeOn(Schedulers.computation()))
                    .doOnNext(courseInfoCollection -> appendCourseInfos(courseInfoCollection))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this);
        }
    }

    private static List<Observable> getNewPopulatedObservablesList(List<Subject> subjects, Option selectedOptions, CourseInfoService courseInfoService) {
        List<Observable> observablesList = new ArrayList<>();
        for (Subject subject : subjects) {
            observablesList.add(courseInfoService.retrieveCourseInfos(
                    subject.getCode(),
                    String.valueOf(selectedOptions.getSemesterMonth()) + String.valueOf(selectedOptions.getSemesterYear()) + "",
                    selectedOptions.getSchoolCampusCode(),
                    selectedOptions.getLevelCode()
            ));
        }
        return observablesList;
    }

    private static Retrofit getRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                    .baseUrl(CoursesUtil.RUTGERS_SIS_BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build();
    }

    private static Gson getGson() {
        return new GsonBuilder()
                    .registerTypeAdapter(CourseInfoCollection.class, new CourseInfoDeserializer())
                    .create();
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                    .callTimeout(15, TimeUnit.MINUTES)
                    .build();
    }

    private synchronized void appendCourseInfos(CourseInfoCollection courseInfoCollection) {
        this.courseInfos.add(courseInfoCollection);
    }

    private void returnCourseInfos() {
        this.coursesRepository.onCourseInfosDownloadComplete(courseInfos);
    }

    public void cleanUp() {
        Log.d("APPDEBUG", "Performing CourseInfo cleanup...");
        this.coursesRepository = null;
        if (this.courseInfos != null) {
            this.courseInfos.clear();
            this.courseInfos = null;
        }
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
            this.disposable = null;
        }
    }

    private void passError(Throwable e) {
        this.coursesRepository.passError(e);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(@NonNull CourseInfoCollection courseInfoCollection) {}

    @Override
    public void onError(@NonNull Throwable e) {
        Log.d("APPDEBUG", "An error has occurred while downloading courses...");
        e.printStackTrace();
        passError(e);
        cleanUp();
    }

    @Override
    public void onComplete() {
        Log.d("APPDEBUG", "Downloading courses complete...");
        returnCourseInfos();
        cleanUp();
    }
}
