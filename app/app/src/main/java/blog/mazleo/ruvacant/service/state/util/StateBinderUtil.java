package blog.mazleo.ruvacant.service.state.util;

import android.os.Handler;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.MainThread;
import blog.mazleo.ruvacant.service.state.ApplicationStateBinding;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Util for creating state bindings. */
@Singleton
public final class StateBinderUtil {

  private final ExecutorService executorService;
  private final Handler mainThreadHandler;

  @Inject
  StateBinderUtil(ExecutorService executorService, @MainThread Handler mainThreadHandler) {
    this.executorService = executorService;
    this.mainThreadHandler = mainThreadHandler;
  }

  public ApplicationStateBinding getAsyncBinding(Function<Void, Void> function) {
    return () -> executorService.execute(() -> function.apply(null));
  }

  public ApplicationStateBinding getAsyncMainThreadBinding(Function<Void, Void> function) {
    return () -> mainThreadHandler.post(() -> function.apply(null));
  }
}
