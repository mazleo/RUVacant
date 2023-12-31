package blog.mazleo.ruvacant.service.bootstrap;

import android.util.Log;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.binders.FileReadBinder;
import blog.mazleo.ruvacant.service.state.binders.PlacesAggregatorBinder;
import blog.mazleo.ruvacant.service.state.binders.RequestBinder;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Bootstraps the application. */
@Singleton
public final class ApplicationBootstrapper {

  private final String appName;
  private final ApplicationStateManager stateManager;
  private final RequestBinder requestBinder;
  private final FileReadBinder fileReadBinder;
  private final PlacesAggregatorBinder placesAggregatorBinder;

  @Inject
  ApplicationBootstrapper(
      @AppName String appName,
      ApplicationStateManager stateManager,
      RequestBinder requestBinder,
      FileReadBinder fileReadBinder,
      PlacesAggregatorBinder placesAggregatorBinder) {
    this.appName = appName;
    this.stateManager = stateManager;

    this.requestBinder = requestBinder;
    this.fileReadBinder = fileReadBinder;
    this.placesAggregatorBinder = placesAggregatorBinder;
  }

  public void bootstrap() {
    Log.d(appName, "Bootstrapping application...");
    registerBinders();
    stateManager.bind();
  }

  private void registerBinders() {
    stateManager.registerBinder(requestBinder);
    stateManager.registerBinder(fileReadBinder);
    stateManager.registerBinder(placesAggregatorBinder);
  }
}
