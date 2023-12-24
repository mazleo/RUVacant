package blog.mazleo.ruvacant.core;

import android.app.Application;

/** The RuVacant Application class. */
public class RuVacantApplication extends Application {
  public ApplicationComponent applicationComponent = DaggerApplicationComponent.create();
}
