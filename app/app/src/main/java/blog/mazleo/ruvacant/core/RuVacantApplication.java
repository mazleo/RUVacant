package blog.mazleo.ruvacant.core;

import android.app.Application;
import androidx.room.Room;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.bootstrap.ApplicationBootstrapper;
import blog.mazleo.ruvacant.service.database.ApplicationDatabase;
import blog.mazleo.ruvacant.service.database.RuVacantDatabase;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import dagger.hilt.android.HiltAndroidApp;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;

/** The RuVacant Application class. */
@HiltAndroidApp
public final class RuVacantApplication extends Application {

  @Inject ApplicationBootstrapper bootstrapper;
  @Inject ApplicationStateManager stateManager;
  @Inject RuVacantDatabase ruVacantDatabase;
  @Inject ExecutorService executorService;

  @Override
  public void onCreate() {
    super.onCreate();
    ruVacantDatabase.initialize(
        Room.databaseBuilder(
                getApplicationContext(),
                ApplicationDatabase.class,
                getResources().getString(R.string.database_name))
            .build());
    bootstrapper.bootstrap();
    stateManager.enterState(ApplicationState.APPLICATION_START.getState());
  }
}
