package blog.mazleo.ruvacant.core;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.ContentBodyFragment;
import blog.mazleo.ruvacant.ui.ApplicationActivity;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

/** Activity scoped dependencies. */
@Module
@InstallIn(ActivityComponent.class)
public abstract class ActivityModule {

  @Provides
  @ActivityScoped
  static ApplicationActivity provideApplicationActivity(AppCompatActivity activity) {
    return (ApplicationActivity) activity;
  }

  @Provides
  @ActivityScoped
  static AppCompatActivity provideAppCompatActivity(Activity activity) {
    return (AppCompatActivity) activity;
  }

  @Provides
  @ActivityScoped
  static Context provideContext(Activity activity) {
    return activity;
  }

  @Provides
  @ActivityScoped
  static Resources provideResources(Context context) {
    return context.getResources();
  }

  @Provides
  static FragmentManager provideFragmentManager(AppCompatActivity activity) {
    return activity.getSupportFragmentManager();
  }

  @Provides
  @ContentBodyFragment
  static View provideContentBodyFragment(ApplicationActivity activity) {
    return activity.findViewById(R.id.content_body);
  }
}
