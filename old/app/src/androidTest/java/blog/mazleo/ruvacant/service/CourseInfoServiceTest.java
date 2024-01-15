package blog.mazleo.ruvacant.service;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import blog.mazleo.ruvacant.model.Class;
import blog.mazleo.ruvacant.model.ClassInstructor;
import blog.mazleo.ruvacant.model.Course;
import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Instructor;
import blog.mazleo.ruvacant.model.Meeting;
import blog.mazleo.ruvacant.service.deserializer.CourseInfoDeserializer;
import blog.mazleo.ruvacant.service.webservice.CourseInfoService;
import blog.mazleo.ruvacant.utils.CoursesUtil;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.TestObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(AndroidJUnit4.class)
public class CourseInfoServiceTest {
    @Test
    public void retrieveCourseInfosTest() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CourseInfoCollection.class, new CourseInfoDeserializer())
                .create();
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CoursesUtil.RUTGERS_SIS_BASE_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        CourseInfoService courseInfoService = retrofit.create(CourseInfoService.class);

        Observable<CourseInfoCollection> observable = courseInfoService.retrieveCourseInfos("198", "92019", "NB", "U");

        TestObserver<CourseInfoCollection> testObserver = TestObserver.create(new Observer<CourseInfoCollection>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("APPDEBUG", "ON SUB");
            }
            @Override
            public void onNext(CourseInfoCollection courseInfoCollection) {
                Log.i("APPDEBUG", "ON NEXT");

                Assert.assertNotNull(courseInfoCollection.getCourses());
                Assert.assertNotNull(courseInfoCollection.getClasses());
                Assert.assertNotNull(courseInfoCollection.getInstructors());
                Assert.assertNotNull(courseInfoCollection.getClassesInstructors());
                Assert.assertNotNull(courseInfoCollection.getMeetings());

                for (Course course : courseInfoCollection.getCourses()) {
                    Log.i("APPDEBUG", course.toString());
                }
                for (Class ruClass : courseInfoCollection.getClasses()) {
                    Log.i("APPDEBUG", ruClass.toString());
                }
                for (Instructor instructor : courseInfoCollection.getInstructors()) {
                    Log.i("APPDEBUG", instructor.toString());
                }
                for (ClassInstructor classInstructor : courseInfoCollection.getClassesInstructors()) {
                    Log.i("APPDEBUG", classInstructor.toString());
                }
                for (Meeting meeting : courseInfoCollection.getMeetings()) {
                    Log.i("APPDEBUG", meeting.toString());
                }

                Assert.assertThat(courseInfoCollection.getCourses().size(), Matchers.greaterThan(0));
                Assert.assertThat(courseInfoCollection.getClasses().size(), Matchers.greaterThan(0));
                Assert.assertThat(courseInfoCollection.getInstructors().size(), Matchers.greaterThan(0));
                Assert.assertThat(courseInfoCollection.getClassesInstructors().size(), Matchers.greaterThan(0));
                Assert.assertThat(courseInfoCollection.getMeetings().size(), Matchers.greaterThan(0));
            }
            @Override
            public void onError(@NonNull Throwable e) {e.printStackTrace();Log.i("APPDEBUG", "ON ERR");}
            @Override
            public void onComplete() {
                Log.i("APPDEBUG", "ON COMP");
            }
        });

        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(testObserver);

        try {
            testObserver.await();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        testObserver.assertComplete();
    }
}
