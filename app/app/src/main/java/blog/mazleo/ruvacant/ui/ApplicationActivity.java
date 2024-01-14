package blog.mazleo.ruvacant.ui;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations;
import blog.mazleo.ruvacant.service.bootstrap.ActivityBootstrapper;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The main activity which holds all UI. */
@AndroidEntryPoint
public final class ApplicationActivity extends AppCompatActivity {

  @Inject ActivityBootstrapper activityBootstrapper;
  @Inject ApplicationStateManager stateManager;
  @Inject @ApplicationAnnotations.AppName String appName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_application);

    Log.d(appName, "Bootstrapping activity...");
    activityBootstrapper.bootstrap();
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  @Override
  protected void onResume() {
    super.onResume();
    stateManager.enterState(ApplicationState.SELECTION_SCENE.getState());
  }
}
