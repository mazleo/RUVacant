package blog.mazleo.ruvacant.service.state;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/** Test cases for the ApplicationStateManager. */
public final class ApplicationStateManagerTest {

  private ApplicationStateManager stateManager;

  @Before
  public void setup() {
    stateManager = new ApplicationStateManager("RuVacantTest");
  }

  /** Checks that a state binder is registered. */
  @Test
  public void registerStateBinder() {
    ApplicationStateBinder noopStateBinder = (stateManager) -> {};
    stateManager.registerBinder(noopStateBinder);
    assertNotNull(stateManager.stateBinders);
    assertTrue(stateManager.stateBinders.size() > 0);
    assertTrue(stateManager.stateBinders.contains(noopStateBinder));
  }

  /** Checks that a state binding is registered. */
  @Test
  public void registerStateBinding() {
    ApplicationStateBinding noopStateBinding = () -> {};
    String fakeState = "fake-state";
    stateManager.registerStateBinding(fakeState, noopStateBinding);
    assertTrue(stateManager.stateBindingMap.containsKey(fakeState));
    assertNotNull(stateManager.stateBindingMap.get(fakeState));
    assertTrue(stateManager.stateBindingMap.get(fakeState).contains(noopStateBinding));
  }

  /** Checks that all state bindings associated with a state runs when the state is set. */
  @Test
  public void addState() {
    final RunCheck runCheck = new RunCheck();
    ApplicationStateBinding stateBinding = () -> runCheck.hasRun = true;
    String fakeState = "fake-state";
    ApplicationStateBinder stateBinder =
        stateManager -> stateManager.registerStateBinding(fakeState, stateBinding);
    stateManager.registerBinder(stateBinder);
    stateManager.bind();
    stateManager.enterState(fakeState);
    assertTrue(stateManager.isInState(fakeState));
    assertTrue(runCheck.hasRun);
  }

  /** Helper class for checking that a binding has run. */
  private static class RunCheck {
    boolean hasRun = false;
  }
}
