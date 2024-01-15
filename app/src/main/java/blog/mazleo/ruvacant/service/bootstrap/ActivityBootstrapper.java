package blog.mazleo.ruvacant.service.bootstrap;

import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.binders.UiBinder;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;

/** Binds activity scoped binders. */
@ActivityScoped
public final class ActivityBootstrapper {

  private final ApplicationStateManager stateManager;

  private final UiBinder uiBinder;

  @Inject
  ActivityBootstrapper(ApplicationStateManager stateManager, UiBinder uiBinder) {
    this.stateManager = stateManager;

    this.uiBinder = uiBinder;
  }

  public void bootstrap() {
    stateManager.registerBinder(uiBinder);
    stateManager.bind();
  }
}
