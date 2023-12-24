package blog.mazleo.ruvacant.ui.splash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.RuVacantApplication;
import blog.mazleo.ruvacant.service.web.RequestService;
import javax.inject.Inject;

/** The splash activity. */
public class SplashActivity extends AppCompatActivity {

  @Inject RequestService requestService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ((RuVacantApplication) getApplicationContext()).applicationComponent.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
  }

  @Override
  protected void onStart() {
    super.onStart();
    requestService.initiateDataRequest();
  }
}
