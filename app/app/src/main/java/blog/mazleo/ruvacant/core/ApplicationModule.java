package blog.mazleo.ruvacant.core;

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
  @Singleton
  @Provides
  static ExecutorService provideExecutorService() {
    return Executors.newCachedThreadPool();
  }
}
