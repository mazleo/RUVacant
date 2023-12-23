package blog.mazleo.ruvacant.service;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.service.deserializer.SubjectListDeserializer;
import blog.mazleo.ruvacant.service.webservice.SubjectsService;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class SubjectsServiceTest {
    @Test
    public void retrieveSubjectsTest() {
        Gson gson = new GsonBuilder().registerTypeAdapter(List.class, new SubjectListDeserializer()).create();
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoursesUtil.RUTGERS_SIS_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        SubjectsService service = retrofit.create(SubjectsService.class);

        TestObserver observer = new TestObserver(new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {}

            @Override
            public void onNext(Object o) {
                assert o instanceof ArrayList;

                ArrayList<Subject> subjectsList = (ArrayList) o;
                if (subjectsList.size() > 0) {
                    assert subjectsList.get(0) instanceof Subject;
                }

                Log.i("APPDEBUG", subjectsList.toString());

                Assert.assertThat(subjectsList.size(), Matchers.greaterThan(0));
            }

            @Override
            public void onError(@NonNull Throwable e) {}

            @Override
            public void onComplete() {}
        });

        Observable<List> subjectsListObservable = service.retrieveSubjects(
            "92020",
            "NB",
            "U"
        );

        subjectsListObservable
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
