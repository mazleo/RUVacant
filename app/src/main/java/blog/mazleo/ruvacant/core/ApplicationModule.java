package blog.mazleo.ruvacant.core;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.MainThread;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

/** Core module of the app. */
@Module
@InstallIn(SingletonComponent.class)
public abstract class ApplicationModule {

  @Provides
  @Singleton
  @ApplicationContext
  static Resources provideResources(Application application) {
    return application.getResources();
  }

  @Provides
  @Singleton
  @AppName
  static String provideAppName(@ApplicationContext Resources resources) {
    return resources.getString(R.string.app_name);
  }

  @Provides
  static AssetManager provideAssetManager(@ApplicationContext Resources resources) {
    return resources.getAssets();
  }

  @Provides
  @Singleton
  static ExecutorService provideExecutorService() {
    return Executors.newCachedThreadPool();
  }

  @Provides
  @Singleton
  @MainThread
  static Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }
}
