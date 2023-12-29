package blog.mazleo.ruvacant.service.state;

/** Binds ApplicationStateBinders to ApplicationStates. */
public interface ApplicationStateBinder {
  void bind(ApplicationStateManager stateManager);
}
