package blog.mazleo.ruvacant.ui.splash;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The selection activity. */
@AndroidEntryPoint
public final class SelectionActivity extends AppCompatActivity {

  @Inject ApplicationStateManager stateManager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection);
  }

  @Override
  protected void onStart() {
    super.onStart();
    stateManager.exitState(ApplicationState.APPLICATION_START.getState());
    stateManager.enterState(ApplicationState.SUBJECTS_REQUEST.getState());
    stateManager.enterState(ApplicationState.PLACES_READING.getState());
  }
}
