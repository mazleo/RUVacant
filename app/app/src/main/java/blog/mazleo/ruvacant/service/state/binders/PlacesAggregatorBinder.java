package blog.mazleo.ruvacant.service.state.binders;

import blog.mazleo.ruvacant.service.cache.RuPlacesAggregatorService;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinder;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.util.StateBinderUtil;
import javax.inject.Inject;
import javax.inject.Singleton;

/** State binder for aggregating the buildings cached data. */
@Singleton
public final class PlacesAggregatorBinder implements ApplicationStateBinder {

  private final RuPlacesAggregatorService aggregatorService;
  private final StateBinderUtil stateBinderUtil;

  @Inject
  PlacesAggregatorBinder(
      RuPlacesAggregatorService aggregatorService, StateBinderUtil stateBinderUtil) {
    this.aggregatorService = aggregatorService;
    this.stateBinderUtil = stateBinderUtil;
  }

  @Override
  public void bind(ApplicationStateManager stateManager) {
    bindPlacesAggregating(stateManager);
    bindPlacesAggregated(stateManager);
  }

  private void bindPlacesAggregating(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_AGGREGATING.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              aggregatorService.initiatePlacesAggregation();
              return null;
            }));
  }

  private void bindPlacesAggregated(ApplicationStateManager stateManager) {
    stateManager.registerStateBinding(
        ApplicationState.PLACES_AGGREGATED.getState(),
        stateBinderUtil.getAsyncBinding(
            unused -> {
              stateManager.exitState(ApplicationState.PLACES_AGGREGATED.getState());
              stateManager.enterState(ApplicationState.DATA_SAVING.getState());
              return null;
            }));
  }
}
