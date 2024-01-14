package blog.mazleo.ruvacant.ui.selectionview;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.info.UniversityCampusUtil;
import blog.mazleo.ruvacant.info.UniversityLevelUtil;
import blog.mazleo.ruvacant.info.UniversitySemesterUtil;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.UniversityContext;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import blog.mazleo.ruvacant.ui.content.ContentActivityInfo;
import blog.mazleo.ruvacant.ui.content.ContentActivityInfoUtil;
import blog.mazleo.ruvacant.ui.content.ContentActivityType;
import blog.mazleo.ruvacant.ui.selectionview.dialogs.UniversitySelection;
import blog.mazleo.ruvacant.ui.selectionview.dialogs.UniversitySelectionDialogFactory;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The selection UI. */
@AndroidEntryPoint
public final class SelectionFragment extends Fragment {

  @Inject ApplicationStateManager stateManager;
  @Inject SharedApplicationData sharedApplicationData;
  @Inject ContentActivityInfoUtil contentActivityInfoUtil;

  private Button semesterButton;
  private Button campusButton;
  private Button levelButton;
  private Button selectButton;

  private Dialog semesterDialog;
  private Dialog campusDialog;
  private Dialog levelDialog;

  private boolean isSemesterSelected = true;
  private boolean isCampusSelected = false;
  private boolean isLevelSelected = false;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_selection, container, /* attachToRoot= */ false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    semesterButton = (Button) getView().findViewById(R.id.semester_selection);
    campusButton = (Button) getView().findViewById(R.id.campus_selection);
    levelButton = (Button) getView().findViewById(R.id.level_selection);
    selectButton = (Button) getView().findViewById(R.id.select_button_selections);

    semesterDialog =
        UniversitySelectionDialogFactory.create(
            UniversitySelection.SEMESTER.getSelection(),
            semesterButton,
            getContext(),
            () -> {},
            this::enableSaveButton);
    campusDialog =
        UniversitySelectionDialogFactory.create(
            UniversitySelection.CAMPUS.getSelection(),
            campusButton,
            getContext(),
            () -> isCampusSelected = true,
            this::enableSaveButton);
    levelDialog =
        UniversitySelectionDialogFactory.create(
            UniversitySelection.LEVEL.getSelection(),
            levelButton,
            getContext(),
            () -> isLevelSelected = true,
            this::enableSaveButton);

    semesterButton.setText(
        String.format(
            "%s %s",
            UniversitySemesterUtil.getCurrentSemester(),
            UniversitySemesterUtil.getCurrentSemesterYear()));

    semesterButton.setOnClickListener(v -> semesterDialog.show());
    campusButton.setOnClickListener(v -> campusDialog.show());
    levelButton.setOnClickListener(v -> levelDialog.show());

    selectButton.setOnClickListener(
        v -> {
          String semesterString = semesterButton.getText().toString().trim();
          String campusCode =
              UniversityCampusUtil.getCampusCodeFromString(String.valueOf(campusButton.getText()));
          String levelCode =
              UniversityLevelUtil.getLevelCodeFromString(String.valueOf(levelButton.getText()));

          sharedApplicationData.replaceData(
              ApplicationData.UNIVERSITY_CONTEXT.getTag(),
              new UniversityContext(semesterString, campusCode, levelCode));
          ContentActivityInfo contentActivityInfo =
              contentActivityInfoUtil.getNewContentActivityInfo();
          contentActivityInfo.contentActivityType = ContentActivityType.UNIVERSITY;
          sharedApplicationData.replaceData(
              ApplicationData.CONTENT_ACTIVITY.getTag(), contentActivityInfo);

          stateManager.exitState(ApplicationState.SELECTION_SCENE.getState());
          stateManager.enterState(ApplicationState.SELECTION_SCENE_END.getState());
        });
  }

  @Override
  public void onStart() {
    super.onStart();
    stateManager.exitState(ApplicationState.APPLICATION_START.getState());
  }

  private void enableSaveButton() {
    if (isSemesterSelected && isCampusSelected && isLevelSelected) {
      selectButton.setEnabled(true);
      selectButton.setBackgroundColor(
          getResources().getColor(R.color.red_main, getContext().getTheme()));
    }
  }
}
