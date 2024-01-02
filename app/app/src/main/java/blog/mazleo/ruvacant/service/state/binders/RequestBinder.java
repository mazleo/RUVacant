package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinding;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import blog.mazleo.ruvacant.service.web.RequestService;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/** State binder for the request service. */
@Singleton
public final class RequestBinder implements ApplicationStateBinder {

  private final Provider<RequestService> requestServiceProvider;
  private final StateBinderUtil stateBinderUtil;

  private RequestService requestService;

  @Inject
  RequestBinder(
      Provider<RequestService> requestServiceProvider,
      ExecutorService executorService,
      StateBinderUtil stateBinderUtil) {
    this.requestServiceProvider = requestServiceProvider;
    this.stateBinderUtil = stateBinderUtil;
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
        stateBinderUtil.getAsyncBinding(
            unused -> {
              requestService.initiateSubjectsRequest();
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.SUBJECTS_REQUEST.getState(), subjectsRequestBinding);
  }

  private void bindSubjectsRequested(ApplicationStateManager stateManager) {
    ApplicationStateBinding subjectsRequestedBinding =
        stateBinderUtil.getAsyncBinding(
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
        stateBinderUtil.getAsyncBinding(
            unused -> {
              requestService.initiateClassInfosRequests();
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.COURSES_REQUEST.getState(), coursesRequestBinding);
  }

  private void bindCoursesRequested(ApplicationStateManager stateManager) {
    ApplicationStateBinding coursesRequestedBinding =
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.COURSES_REQUESTED.getState());
              stateManager.exitState(ApplicationState.REQUESTING_DATA.getState());
              stateManager.enterState(ApplicationState.PLACES_AGGREGATING.getState());
              return null;
            });
    stateManager.registerStateBinding(
        ApplicationState.COURSES_REQUESTED.getState(), coursesRequestedBinding);
  }
}
