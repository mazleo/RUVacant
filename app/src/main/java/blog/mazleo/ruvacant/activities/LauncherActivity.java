package blog.mazleo.ruvacant.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import blog.mazleo.ruvacant.R;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        // TODO: Build database and check if it contains values

        // TODO: Transition to next activity
        new CountDownTimer(1000, 1000) {
            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTick(long l) {}
        }.start();
    }
}
