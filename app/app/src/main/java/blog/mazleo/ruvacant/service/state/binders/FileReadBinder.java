package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.file.FileService;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/** Binds the file reading service. */
@Singleton
public final class FileReadBinder implements ApplicationStateBinder {

  private final Provider<FileService> fileServiceProvider;
  private final StateBinderUtil stateBinderUtil;

  private FileService fileService;

  @Inject
  FileReadBinder(Provider<FileService> fileServiceProvider, StateBinderUtil stateBinderUtil) {
    this.fileServiceProvider = fileServiceProvider;
    this.stateBinderUtil = stateBinderUtil;
    fileService = fileServiceProvider.get();
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindPlacesReading(stateManager);
    bindPlacesRead(stateManager);
  }

  @Override
  public void reset() {
    fileService = fileServiceProvider.get();
  }

  private void bindPlacesReading(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_READING.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              fileService.initiateParsePlacesFile();
              return null;
            }));
  }

  private void bindPlacesRead(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_READ.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.PLACES_READ.getState());
              stateManager.enterState(ApplicationState.PLACES_AGGREGATING.getState());
              return null;
            }));
  }
}
