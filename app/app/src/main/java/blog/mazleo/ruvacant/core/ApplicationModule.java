package blog.mazleo.ruvacant.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
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
  @AppName
  static String provideAppName(Resources resources) {
    return resources.getString(R.string.app_name);
  }

  @Provides
  @Singleton
  static ExecutorService provideExecutorService() {
    return Executors.newCachedThreadPool();
  }

  @Provides
  @Singleton
  static Resources provideResources(Context context) {
    return context.getResources();
  }

  @Provides
  static Context provideContext(Application application) {
    return application.getApplicationContext();
  }
}
