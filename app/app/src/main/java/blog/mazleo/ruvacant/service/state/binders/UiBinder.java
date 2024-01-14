package blog.mazleo.ruvacant.service.state.binders;

import android.content.Context;
import android.util.Log;
import androidx.fragment.app.FragmentManager;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import blog.mazleo.ruvacant.ui.ApplicationActivity;
import blog.mazleo.ruvacant.ui.content.ContentFragment;
import blog.mazleo.ruvacant.ui.universityscene.UniversitySceneDataManager;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;

@ActivityScoped
public final class UiBinder implements ApplicationStateBinder {

  private final String appName;
  private final Context context;
  private final ApplicationActivity activity;
  private final FragmentManager fragmentManager;
  private final StateBinderUtil stateBinderUtil;

  private final UniversitySceneDataManager universitySceneDataManager;

  /** Binds UI tasks to certain states. */
  @Inject
  UiBinder(
      @AppName String appName,
      Context context,
      ApplicationActivity activity,
      FragmentManager fragmentManager,
      StateBinderUtil stateBinderUtil,
      UniversitySceneDataManager universitySceneDataManager) {
    this.appName = appName;
    this.context = context;
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    this.stateBinderUtil = stateBinderUtil;

    this.universitySceneDataManager = universitySceneDataManager;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindSelectionSceneEnd(stateManager);
    bindUniversityScene(stateManager);
    bindUniversitySceneDataLoading(stateManager);
  }

  private void bindSelectionSceneEnd(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.SELECTION_SCENE_END.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.SELECTION_SCENE_END.getState());
              stateManager.enterState(ApplicationState.UNIVERSITY_SCENE.getState());
              if (!stateManager.isInState(
                  ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState())) {
                stateManager.enterState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState());
              }
              return null;
            }),
        StateBinding.SELECTION_SCENE.getId());
  }

  private void bindUniversityScene(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.UNIVERSITY_SCENE.getState(),
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

  private void bindUniversitySceneDataLoading(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              if (!stateManager.isInState(ApplicationState.DATA_SAVING.getState())
                  && !stateManager.isInState(ApplicationState.DATA_SAVED.getState())
                  && !stateManager.isInState(ApplicationState.REQUESTING_DATA.getState())) {
                universitySceneDataManager.buildCards();
              } else {
                Log.d(appName, "Not ready to begin loading university scene data.");
                stateManager.exitState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState());
              }
              return null;
            }),
        StateBinding.UNIVERSITY_SCENE_BUILD_CARDS.getId());
  }

  @Override
  public void reset() {}
}
