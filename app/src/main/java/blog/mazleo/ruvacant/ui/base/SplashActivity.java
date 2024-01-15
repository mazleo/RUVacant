package blog.mazleo.ruvacant.ui.base;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.ui.ApplicationActivity;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The splash screen. */
@AndroidEntryPoint
public final class SplashActivity extends AppCompatActivity {

  private static final int SPLASH_DURATION = 700;

  @Inject ApplicationStateManager stateManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
  }

  @Override
  protected void onStart() {
    super.onStart();
    stateManager.enterState(ApplicationState.APPLICATION_START.getState());
  }

  @Override
  protected void onResume() {
    super.onResume();

    new Handler()
        .postDelayed(
            () -> {
              ImageView logo = (ImageView) findViewById(R.id.logo_splash);
              Intent intent = new Intent(this, ApplicationActivity.class);
              ActivityOptions options =
                  ActivityOptions.makeSceneTransitionAnimation(this, logo, "logo");
              startActivity(intent, options.toBundle());
            },
            SPLASH_DURATION);
  }
}
