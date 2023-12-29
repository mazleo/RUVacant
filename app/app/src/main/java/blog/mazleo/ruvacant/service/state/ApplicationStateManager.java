package blog.mazleo.ruvacant.service.state;

import android.util.Log;
import androidx.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

/** The application state manager. */
@Singleton
public final class ApplicationStateManager {

  private final Set<String> currentState = new HashSet<>();

  @VisibleForTesting
  final Map<String, List<ApplicationStateBinding>> stateBindingMap = new HashMap<>();

  @VisibleForTesting final List<ApplicationStateBinder> stateBinders = new ArrayList<>();

  @VisibleForTesting
  @Inject
  public ApplicationStateManager() {
    currentState.add(ApplicationState.APPLICATION_START.getState());
  }

  /** Adds a current state and runs all state binders associated asynchronously. */
  public void enterState(String state) {
    currentState.add(state);
    if (!stateBindingMap.containsKey(state)) {
      return;
    }
    Log.d("RuVacant", String.format("Entering State: %s.", state));
    for (ApplicationStateBinding stateBinding : stateBindingMap.get(state)) {
      stateBinding.onState();
    }
  }

  /** Removes a state from the current state. */
  public void exitState(String state) {
    if (currentState.contains(state)) {
      Log.d("RuVacant", String.format("Exiting state: %s.", state));
      currentState.remove(state);
    }
  }

  /** Determines whether the application is in a certain state. */
  public boolean isInState(String state) {
    return currentState.contains(state);
  }

  /** Register a state binder. */
  public void registerBinder(ApplicationStateBinder stateBinder) {
    if (!stateBinders.contains(stateBinder)) {
      stateBinders.add(stateBinder);
    }
  }

  /** Bind all state binders. */
  public void bind() {
    for (ApplicationStateBinder stateBinder : stateBinders) {
      stateBinder.bind(this);
    }
  }

  /** Helper function to register a single state binding. */
  public void registerStateBinding(String state, ApplicationStateBinding stateBinding) {
    if (stateBindingMap.containsKey(state)) {
      List<ApplicationStateBinding> stateBindings = stateBindingMap.get(state);
      if (!stateBindings.contains(stateBinding)) {
        stateBindings.add(stateBinding);
      }
    } else {
      List<ApplicationStateBinding> stateBindings = new ArrayList<>();
      stateBindings.add(stateBinding);
      stateBindingMap.put(state, stateBindings);
    }
  }

  public Set<String> getCurrentState() {
    return currentState;
  }
}
