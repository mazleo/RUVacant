package blog.mazleo.ruvacant.core;

import android.app.Application;
import blog.mazleo.ruvacant.service.bootstrap.ApplicationBootstrapper;
import dagger.hilt.android.HiltAndroidApp;
import javax.inject.Inject;

/** The RuVacant Application class. */
@HiltAndroidApp
public final class RuVacantApplication extends Application {

  @Inject ApplicationBootstrapper bootstrapper;

  @Override
  public void onCreate() {
    super.onCreate();
    bootstrapper.bootstrap();
  }
}
