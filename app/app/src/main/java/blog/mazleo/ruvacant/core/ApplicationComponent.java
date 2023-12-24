package blog.mazleo.ruvacant.core;

import blog.mazleo.ruvacant.ui.splash.SplashActivity;
import dagger.Component;

/** Generates the Dagger graph for the application. */
@Component
public interface ApplicationComponent {
  void inject(SplashActivity splashActivity);
}
