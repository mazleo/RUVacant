package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.database.DatabaseService;
import blog.mazleo.ruvacant.service.file.FileService;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Binds the file reading service. */
@Singleton
public final class FileReadBinder implements ApplicationStateBinder {

  private final FileService fileService;
  private final DatabaseService databaseService;
  private final StateBinderUtil stateBinderUtil;

  @Inject
  FileReadBinder(
      FileService fileService, DatabaseService databaseService, StateBinderUtil stateBinderUtil) {
    this.fileService = fileService;
    this.databaseService = databaseService;
    this.stateBinderUtil = stateBinderUtil;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindApplicationStart(stateManager);
    bindPlacesReading(stateManager);
    bindPlacesRead(stateManager);
  }

  @Override
  public void reset() {
    fileService.reset();
  }

  private void bindApplicationStart(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.APPLICATION_START.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              if (!databaseService.hasData()) {
                stateManager.enterState(ApplicationState.PLACES_READING.getState());
              }
              stateManager.exitState(ApplicationState.APPLICATION_START.getState());
              return null;
            }),
        StateBinding.READ_PLACES_FILE.getId());
  }

  private void bindPlacesReading(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_READING.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              fileService.initiateParsePlacesFile();
              return null;
            }),
        StateBinding.READ_PLACES_FILE.getId());
  }

  private void bindPlacesRead(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_READ.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.PLACES_READ.getState());
              stateManager.enterState(ApplicationState.PLACES_AGGREGATING.getState());
              return null;
            }),
        StateBinding.READ_PLACES_FILE.getId());
  }
}
