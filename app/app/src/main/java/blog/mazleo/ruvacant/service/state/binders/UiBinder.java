package blog.mazleo.ruvacant.service.state.binders;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import blog.mazleo.ruvacant.ui.ApplicationActivity;
import blog.mazleo.ruvacant.ui.content.ContentFragment;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;

@ActivityScoped
public final class UiBinder implements ApplicationStateBinder {

  private final Context context;
  private final ApplicationActivity activity;
  private final FragmentManager fragmentManager;
  private final StateBinderUtil stateBinderUtil;

  /** Binds UI tasks to certain states. */
  @Inject
  UiBinder(
      Context context,
      ApplicationActivity activity,
      FragmentManager fragmentManager,
      StateBinderUtil stateBinderUtil) {
    this.context = context;
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    this.stateBinderUtil = stateBinderUtil;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindSelectionSceneEnd(stateManager);
    bindUniversityScene(stateManager);
  }

  private void bindSelectionSceneEnd(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.SELECTION_ACTIVITY_END.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.SELECTION_ACTIVITY_END.getState());
              stateManager.enterState(ApplicationState.UNIVERSITY_VIEW_ACTIVITY.getState());
              return null;
            }),
        StateBinding.SELECTION_SCENE.getId());
  }

  private void bindUniversityScene(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.UNIVERSITY_VIEW_ACTIVITY.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              fragmentManager
                  .beginTransaction()
                  .replace(R.id.app_fragment, ContentFragment.class, null)
                  .commit();
              return null;
            }),
        StateBinding.UNIVERSITY_SCENE.getId());
  }

  @Override
  public void reset() {}
}
