package blog.mazleo.ruvacant.core;

import android.content.Context;
import android.content.res.AssetManager;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

/** Core module of the app. */
@Module
public abstract class CoreModule {
  @Provides
  static AssetManager provideAssets(Context context) {
    return context.getAssets();
  }

  @Singleton
  @Provides
  static ExecutorService provideExecutorService() {
    return Executors.newCachedThreadPool();
  }
}
