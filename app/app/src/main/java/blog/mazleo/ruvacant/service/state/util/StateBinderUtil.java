package blog.mazleo.ruvacant.service.state.util;

import blog.mazleo.ruvacant.service.state.ApplicationStateBinding;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Util for creating state bindings. */
@Singleton
public final class StateBinderUtil {

  private final ExecutorService executorService;

  @Inject
  StateBinderUtil(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public ApplicationStateBinding getAsyncBinding(Function<Void, Void> function) {
    return () -> executorService.execute(() -> function.apply(null));
  }
}
