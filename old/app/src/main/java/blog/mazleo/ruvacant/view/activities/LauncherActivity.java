package blog.mazleo.ruvacant.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import blog.mazleo.ruvacant.R;

public class LauncherActivity extends AppCompatActivity {

    private ImageView launcherLogo;
    private final String TRANSITION_APP_LOGO_NAME = "transition_app_logo";
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        this.launcherLogo = findViewById(R.id.launcher_logo);
        this.activity = this;

        // TODO: Build database and check if it contains values

        new CountDownTimer(1000, 1000) {
            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, (View) launcherLogo, TRANSITION_APP_LOGO_NAME);
                startActivity(intent, options.toBundle());
            }

            @Override
            public void onTick(long l) {}
        }.start();
    }
}
