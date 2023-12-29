package blog.mazleo.ruvacant.ui.splash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.RuVacantApplication;
import blog.mazleo.ruvacant.service.bootstrap.ApplicationBootstrapper;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import javax.inject.Inject;

/** The splash activity. */
public class SplashActivity extends AppCompatActivity {

  @Inject ApplicationBootstrapper bootstrapper;
  @Inject ApplicationStateManager stateManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ((RuVacantApplication) getApplicationContext()).applicationComponent.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
  }

  @Override
  protected void onStart() {
    super.onStart();
    bootstrapper.bootstrap();
    stateManager.enterState(ApplicationState.SUBJECTS_REQUEST.getState());
  }
}
