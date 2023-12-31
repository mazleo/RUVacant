package blog.mazleo.ruvacant.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.ui.dialogs.UniversitySelection;
import blog.mazleo.ruvacant.ui.dialogs.UniversitySelectionDialogFactory;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The selection activity. */
@AndroidEntryPoint
public final class SelectionActivity extends AppCompatActivity {

  @Inject ApplicationStateManager stateManager;

  private Button semesterButton;
  private Button campusButton;
  private Button levelButton;

  private Dialog semesterDialog;
  private Dialog campusDialog;
  private Dialog levelDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_selection);

    semesterButton = (Button) findViewById(R.id.semester_selection);
    campusButton = (Button) findViewById(R.id.campus_selection);
    levelButton = (Button) findViewById(R.id.level_selection);

    campusDialog =
        UniversitySelectionDialogFactory.create(
            UniversitySelection.CAMPUS.getSelection(), campusButton, SelectionActivity.this);
    levelDialog =
        UniversitySelectionDialogFactory.create(
            UniversitySelection.LEVEL.getSelection(), levelButton, SelectionActivity.this);

    campusButton.setOnClickListener(view -> campusDialog.show());
    levelButton.setOnClickListener(view -> levelDialog.show());
  }

  @Override
  protected void onStart() {
    super.onStart();
    stateManager.exitState(ApplicationState.APPLICATION_START.getState());
    stateManager.enterState(ApplicationState.SUBJECTS_REQUEST.getState());
    stateManager.enterState(ApplicationState.PLACES_READING.getState());
  }
}
