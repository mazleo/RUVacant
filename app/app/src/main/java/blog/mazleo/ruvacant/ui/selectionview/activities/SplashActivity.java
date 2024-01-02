package blog.mazleo.ruvacant.ui.selectionview.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;

/** The splash screen. */
public final class SplashActivity extends AppCompatActivity {

  private static final int SPLASH_DURATION = 700;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
  }

  @Override
  protected void onResume() {
    super.onResume();

    new Handler()
        .postDelayed(
            () -> {
              ImageView logo = (ImageView) findViewById(R.id.logo_splash);
              Intent intent = new Intent(this, SelectionActivity.class);
              ActivityOptions options =
                  ActivityOptions.makeSceneTransitionAnimation(this, logo, "logo");
              startActivity(intent, options.toBundle());
            },
            SPLASH_DURATION);
  }
}
