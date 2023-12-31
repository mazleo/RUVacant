package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinding;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.web.RequestService;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/** State binder for the request service. */
@Singleton
public final class RequestBinder implements ApplicationStateBinder {

  private final Provider<RequestService> requestServiceProvider;
  private final ExecutorService executorService;

  private RequestService requestService;

  @Inject
  RequestBinder(Provider<RequestService> requestServiceProvider, ExecutorService executorService) {
    this.requestServiceProvider = requestServiceProvider;
    this.executorService = executorService;
    requestService = requestServiceProvider.get();
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindSubjectsRequest(stateManager);
    bindSubjectsRequested(stateManager);
    bindCoursesRequest(stateManager);
    bindCoursesRequested(stateManager);
  }

  @Override
  public void reset() {
    requestService = requestServiceProvider.get();
  }

  private void bindSubjectsRequest(ApplicationStateManager stateManager) {
    ApplicationStateBinding subjectsRequestBinding =
        getAsyncBinding(
            unused -> {
              requestService.initiateSubjectsRequest();
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.SUBJECTS_REQUEST.getState(), subjectsRequestBinding);
  }

  private void bindSubjectsRequested(ApplicationStateManager stateManager) {
    ApplicationStateBinding subjectsRequestedBinding =
        getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.SUBJECTS_REQUESTED.getState());
              stateManager.enterState(ApplicationState.COURSES_REQUEST.getState());
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.SUBJECTS_REQUESTED.getState(), subjectsRequestedBinding);
  }

  private void bindCoursesRequest(ApplicationStateManager stateManager) {
    ApplicationStateBinding coursesRequestBinding =
        getAsyncBinding(
            unused -> {
              requestService.initiateClassInfosRequests();
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.COURSES_REQUEST.getState(), coursesRequestBinding);
  }

  private void bindCoursesRequested(ApplicationStateManager stateManager) {
    ApplicationStateBinding coursesRequestedBinding =
        getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.COURSES_REQUESTED.getState());
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.COURSES_REQUESTED.getState(), coursesRequestedBinding);
  }

  private ApplicationStateBinding getAsyncBinding(Function<Void, Void> function) {
    return () -> executorService.execute(() -> function.apply(null));
  }
}
