package blog.mazleo.ruvacant.core;

import android.app.Application;
import blog.mazleo.ruvacant.service.bootstrap.ApplicationBootstrapper;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import dagger.hilt.android.HiltAndroidApp;
import javax.inject.Inject;

/** The RuVacant Application class. */
@HiltAndroidApp
public final class RuVacantApplication extends Application {

  @Inject ApplicationBootstrapper bootstrapper;
  @Inject ApplicationStateManager stateManager;

  @Override
  public void onCreate() {
    super.onCreate();
    stateManager.enterState(ApplicationState.APPLICATION_START.getState());
    bootstrapper.bootstrap();
  }
}
