package blog.mazleo.ruvacant.service.bootstrap;

import android.util.Log;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.binders.RequestBinder;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Bootstraps the application. */
@Singleton
public final class ApplicationBootstrapper {

  private final ApplicationStateManager stateManager;
  private final RequestBinder requestBinder;

  @Inject
  ApplicationBootstrapper(ApplicationStateManager stateManager, RequestBinder requestBinder) {
    this.stateManager = stateManager;
    this.requestBinder = requestBinder;
  }

  public void bootstrap() {
    Log.d("RuVacant", "Bootstrapping application...");
    registerBinders();
    stateManager.bind();
  }

  private void registerBinders() {
    stateManager.registerBinder(requestBinder);
  }
}
