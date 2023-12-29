package blog.mazleo.ruvacant.core;

import blog.mazleo.ruvacant.ui.splash.SplashActivity;
import dagger.Component;
import javax.inject.Singleton;

/** Generates the Dagger graph for the application. */
@Singleton
@Component(modules = {CoreModule.class})
public interface ApplicationComponent {
  void inject(SplashActivity splashActivity);
}
