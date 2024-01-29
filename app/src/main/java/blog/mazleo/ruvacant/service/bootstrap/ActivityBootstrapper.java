package blog.mazleo.ruvacant.service.bootstrap;

import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.binders.UniversitySceneBinder;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;

/** Binds activity scoped binders. */
@ActivityScoped
public final class ActivityBootstrapper {

  private final ApplicationStateManager stateManager;

  private final UniversitySceneBinder universitySceneBinder;

  @Inject
  ActivityBootstrapper(
      ApplicationStateManager stateManager, UniversitySceneBinder universitySceneBinder) {
    this.stateManager = stateManager;

    this.universitySceneBinder = universitySceneBinder;
  }

  public void bootstrap() {
    stateManager.registerBinder(universitySceneBinder);
    stateManager.bind();
  }
}
