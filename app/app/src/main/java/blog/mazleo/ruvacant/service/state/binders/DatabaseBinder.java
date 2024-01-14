package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.database.DatabaseService;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import javax.inject.Inject;
import javax.inject.Singleton;

/** State binder for the database service. */
@Singleton
public final class DatabaseBinder implements ApplicationStateBinder {

  private final DatabaseService databaseService;
  private final ApplicationStateManager stateManager;
  private final StateBinderUtil stateBinderUtil;

  @Inject
  DatabaseBinder(
      DatabaseService databaseService,
      ApplicationStateManager stateManager,
      StateBinderUtil stateBinderUtil) {
    this.databaseService = databaseService;
    this.stateManager = stateManager;
    this.stateBinderUtil = stateBinderUtil;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindDataSaving();
    bindDataSaved();
  }

  private void bindDataSaving() {
    stateManager.registerStateBinding(
        ApplicationState.DATA_SAVING.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              databaseService.saveAllData();
              return null;
            }),
        StateBinding.SAVE_TO_DATABASE.getId());
  }

  private void bindDataSaved() {
    stateManager.registerStateBinding(
        ApplicationState.DATA_SAVED.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.DATA_SAVED.getState());
              if (!stateManager.isInState(
                  ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState())) {
                stateManager.enterState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState());
              }
              return null;
            }),
        StateBinding.SAVE_TO_DATABASE.getId());
  }
}
