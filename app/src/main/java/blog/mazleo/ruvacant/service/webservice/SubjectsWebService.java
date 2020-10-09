package blog.mazleo.ruvacant.service.webservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.repository.LocationsRepository;
import blog.mazleo.ruvacant.repository.RepositoryInstance;
import blog.mazleo.ruvacant.service.deserializer.SubjectListDeserializer;
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

public class SubjectsWebService implements Observer {
    private RepositoryInstance repository;
    private List<Subject> subjects;
    private Disposable disposable;

    public SubjectsWebService(RepositoryInstance repository) {
        this.repository = repository;
        this.subjects = new ArrayList();
    }

    public void downloadSubjects(Option selectedOption) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(List.class, new SubjectListDeserializer())
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoursesUtil.RUTGERS_SIS_BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SubjectsService subjectsService = retrofit.create(SubjectsService.class);
        Observable<List> observable = subjectsService.retrieveSubjects(
                new StringBuilder()
                        .append(selectedOption.getSemesterMonth())
                        .append(selectedOption.getSemesterYear())
                        .toString(),
                selectedOption.getSchoolCampusCode(),
                selectedOption.getLevelCode()
        );

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    private void returnSubjects() {
        if (repository instanceof LocationsRepository) {
            ((LocationsRepository) repository).onSubjectsDownloadComplete(subjects);
        }
//        else if (repository instanceof CoursesRepository) {
            // TODO
//        }
    }

    private void appendSubjects(List newSubjects) {
        subjects.addAll(newSubjects);
    }

    private void passError(Throwable e) {
        if (repository instanceof LocationsRepository) {
            ((LocationsRepository) repository).onError(e);
        }
//        else if (repository instanceof CoursesRepository) {
            // TODO
//        }
    }

    public void cleanUp() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }

        repository = null;
        subjects = null;
        disposable = null;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(Object o) {
        if (o instanceof List) {
            appendSubjects((List) o);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        passError(e);
        cleanUp();
    }

    @Override
    public void onComplete() {
        returnSubjects();
        cleanUp();
    }
}
