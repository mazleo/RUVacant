package blog.mazleo.ruvacant.service;

import android.util.Log;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import blog.mazleo.ruvacant.model.Level;
import blog.mazleo.ruvacant.service.dbservice.AppDatabase;
import blog.mazleo.ruvacant.utils.DatabaseUtil;
import io.reactivex.Completable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

@RunWith(AndroidJUnit4.class)
public class SetupDaoTest {
    @Test
    public void insertLevelsTest() {
        AppDatabase db = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(), AppDatabase.class, DatabaseUtil.DATABASE_NAME).build();
        db.clearAllTables();

        List<Level> levels = new ArrayList<>();
        levels.add(new Level("U", "Undergraduate"));
        levels.add(new Level("G", "Graduate"));

        Completable completable = db.setupDao().insertLevels(levels.toArray(new Level[levels.size()]));
        TestObserver<Object> observer = TestObserver.create(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("APPDEBUG", "ON SUB!");
            }

            @Override
            public void onNext(@NonNull Object o) {

                Log.i("APPDEBUG", "ON NEXT!");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("APPDEBUG", "ERROR!");
            }

            @Override
            public void onComplete() {
                Log.i("APPDEBUG", "INSERT DONE!!!");
            }
        });

        completable
                .subscribeOn(Schedulers.computation())
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
