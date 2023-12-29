package blog.mazleo.ruvacant.service.state;

import androidx.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/** The application state manager. */
@Singleton
public final class ApplicationStateManager {

  private String currentState = ApplicationState.APPLICATION_START.getState();

  @VisibleForTesting
  final Map<String, List<ApplicationStateBinding>> stateBindingMap = new HashMap<>();

  @VisibleForTesting final List<ApplicationStateBinder> stateBinders = new ArrayList<>();

  @VisibleForTesting
  @Inject
  public ApplicationStateManager() {}

  /** Sets the current state and runs all state binders associated. */
  public void setState(String state) {
    currentState = state;
    if (!stateBindingMap.containsKey(currentState)) {
      return;
    }
    for (ApplicationStateBinding stateBinding : stateBindingMap.get(currentState)) {
      stateBinding.onState();
    }
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

  public String getCurrentState() {
    return currentState;
  }
}
