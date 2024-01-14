package blog.mazleo.ruvacant.service.state.binders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.ContentBodyFragment;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import blog.mazleo.ruvacant.ui.ApplicationActivity;
import blog.mazleo.ruvacant.ui.content.ContentFragment;
import blog.mazleo.ruvacant.ui.universityscene.UniversitySceneAdapter;
import blog.mazleo.ruvacant.ui.universityscene.UniversitySceneDataManager;
import dagger.hilt.android.scopes.ActivityScoped;
import javax.inject.Inject;
import javax.inject.Provider;

@ActivityScoped
public final class UiBinder implements ApplicationStateBinder {

  private final String appName;
  private final Context context;
  private final ApplicationActivity activity;
  private final FragmentManager fragmentManager;
  private final StateBinderUtil stateBinderUtil;

  private final Provider<View> contentBodyViewProvider;
  private final UniversitySceneDataManager universitySceneDataManager;
  private final UniversitySceneAdapter universitySceneAdapter;

  /** Binds UI tasks to certain states. */
  @Inject
  UiBinder(
      @AppName String appName,
      Context context,
      ApplicationActivity activity,
      FragmentManager fragmentManager,
      StateBinderUtil stateBinderUtil,
      @ContentBodyFragment Provider<View> contentBodyViewProvider,
      UniversitySceneDataManager universitySceneDataManager,
      UniversitySceneAdapter universitySceneAdapter) {
    this.appName = appName;
    this.context = context;
    this.activity = activity;
    this.fragmentManager = fragmentManager;
    this.stateBinderUtil = stateBinderUtil;

    this.contentBodyViewProvider = contentBodyViewProvider;
    this.universitySceneDataManager = universitySceneDataManager;
    this.universitySceneAdapter = universitySceneAdapter;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindSelectionSceneEnd(stateManager);
    bindUniversityScene(stateManager);
    bindUniversitySceneDataLoading(stateManager);
    bindUniversitySceneDataLoaded(stateManager);
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
                  .replace(R.id.app_fragment, ContentFragment.class, null, "university-scene")
                  .setReorderingAllowed(true)
                  .addToBackStack(/* name= */ null)
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

  private void bindUniversitySceneDataLoaded(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.UNIVERSITY_SCENE_DATA_LOADED.getState(),
        stateBinderUtil.getAsyncMainThreadBinding(
            unused -> {
              View contentBody = contentBodyViewProvider.get();
              RecyclerView buildingsListing =
                  (RecyclerView)
                      ((ViewGroup)
                              LayoutInflater.from(contentBody.getContext())
                                  .inflate(
                                      R.layout.content_recycler_view,
                                      (ViewGroup) contentBody,
                                      /* attachToRoot= */ true))
                          .getChildAt(0);
              buildingsListing.setLayoutManager(new LinearLayoutManager(contentBody.getContext()));
              buildingsListing.setAdapter(universitySceneAdapter);
              return null;
            }),
        StateBinding.UNIVERSITY_SCENE_PRESENT_CARDS.getId());
  }

  @Override
  public void reset() {}
}
