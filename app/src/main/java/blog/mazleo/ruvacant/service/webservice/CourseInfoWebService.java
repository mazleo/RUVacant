package blog.mazleo.ruvacant.service.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.service.deserializer.CourseInfoDeserializer;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CourseInfoWebService implements Observer<CourseInfoCollection> {
    private CourseRepository courseRepository;
    private List<CourseInfoCollection> courseInfos;
    private Disposable disposable;

    public CourseInfoWebService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        this.courseInfos = new ArrayList<>();
        this.disposable = null;
    }

    public void downloadCourseInfos(List<Subject> subjects, Option selectedOptions) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CourseInfoCollection.class, new CourseInfoDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoursesUtil.RUTGERS_SIS_BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        CourseInfoService courseInfoService = retrofit.create(CourseInfoService.class);

        List<Observable> observablesList = new ArrayList<>();
        for (Subject subject : subjects) {
            observablesList.add(courseInfoService.retrieveCourseInfos(
                    subject.getCode(),
                    selectedOptions.getSemesterMonth() + selectedOptions.getSemesterYear() + "",
                    selectedOptions.getSchoolCampusCode(),
                    selectedOptions.getLevelCode()
            ));
        }
        Observable<CourseInfoCollection> finalObservable = Observable.mergeArray(observablesList.toArray(new Observable[observablesList.size()]));

        finalObservable
                .subscribeOn(Schedulers.computation())
                .flatMap(courseInfoCollection -> Observable.just(courseInfoCollection))
                .doOnNext(courseInfoCollection -> appendCourseInfos(courseInfoCollection))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    private synchronized void appendCourseInfos(CourseInfoCollection courseInfoCollection) {
        this.courseInfos.add(courseInfoCollection);
    }

    private void returnCourseInfos() {
        this.courseRepository.passDownloadedCourseInfos(this.courseInfos);
    }

    public void cleanUp() {
        this.courseRepository = null;
        this.courseInfos.clear();
        this.courseInfos = null;
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
        this.disposable = null;
    }

    private void passError(Throwable e) {
        this.courseRepository.passCourseDownloadError(e);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.disposable = d;
    }

    @Override
    public void onNext(@NonNull CourseInfoCollection courseInfoCollection) {}

    @Override
    public void onError(@NonNull Throwable e) {
        passError(e);
        cleanUp();
    }

    @Override
    public void onComplete() {
        returnCourseInfos();
        cleanUp();
    }
}
