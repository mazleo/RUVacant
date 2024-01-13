package blog.mazleo.ruvacant.service.cache;

import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassInfos;
import blog.mazleo.ruvacant.service.model.RuPlace;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/** Aggregates the cached building data. */
public final class RuPlacesAggregatorService {

  private final ApplicationStateManager stateManager;
  private final SharedApplicationData sharedApplicationData;

  @Inject
  RuPlacesAggregatorService(
      ApplicationStateManager stateManager, SharedApplicationData sharedApplicationData) {
    this.stateManager = stateManager;
    this.sharedApplicationData = sharedApplicationData;
  }

  public void initiatePlacesAggregation() {
    if (!stateManager.isInState(ApplicationState.APPLICATION_START.getState())
        && !stateManager.isInState(ApplicationState.REQUESTING_DATA.getState())
        && !stateManager.isInState(ApplicationState.PLACES_READING.getState())
        && !stateManager.isInState(ApplicationState.PLACES_READ.getState())) {
      RuClassInfos cachedClassInfos =
          (RuClassInfos) sharedApplicationData.getData(ApplicationData.CLASS_INFOS_CACHE.getTag());
      Map<String, RuPlace> cachedPlaces =
          (Map<String, RuPlace>)
              sharedApplicationData.getData(ApplicationData.PLACES_CACHE.getTag());

      List<RuBuilding> buildings = cachedClassInfos.buildings;
      for (RuBuilding building : buildings) {
        building.name =
            cachedPlaces.containsKey(building.code) ? cachedPlaces.get(building.code).name : null;
      }

      sharedApplicationData.replaceData(
          ApplicationData.CLASS_INFOS_CACHE.getTag(), cachedClassInfos);

      stateManager.exitState(ApplicationState.PLACES_AGGREGATING.getState());
      stateManager.enterState(ApplicationState.PLACES_AGGREGATED.getState());
    }
  }
}
