package blog.mazleo.ruvacant.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.processor.DataDownloadProcessor;
import blog.mazleo.ruvacant.utils.OptionsUtil;
import blog.mazleo.ruvacant.view.dialogfragments.OptionsPickerDialogFragment;

public class OptionsActivity extends AppCompatActivity {

    private static final String DIALOG_FRAGMENT_TAG = "OPTIONS_DIALOG_FRAGMENT";
    private Activity activity;

    public Button semesterButton;
    public Button campusButton;
    public Button levelButton;
    public Button saveButton;

    private View dimmingView;
    private ProgressBar dataRetrievalProgressBar;
    private ImageView dataRetrievalImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        this.activity = this;

        initializeViews();

        setClickListenerToSemesterButton();
        setClickListenerToCampusButton();
        setClickListenerToLevelButton();

        setSaveButtonStates();
        setClickListenerToSaveButton();
    }

    private void setClickListenerToSaveButton() {
        this.saveButton.setOnClickListener(
                view -> {
                    String chosenSemester = this.semesterButton.getText().toString();
                    String chosenCampus = this.campusButton.getText().toString();
                    String chosenLevel = this.levelButton.getText().toString();

                    int semesterMonth = OptionsUtil.getSemesterMonthFromSemesterString(chosenSemester);
                    int semesterYear = OptionsUtil.getSemesterYearFromSemesterString(chosenSemester);
                    String campusCode = OptionsUtil.getSchoolCampusCodeFromName(chosenCampus);
                    String levelCode = OptionsUtil.getLevelCodeFromName(chosenLevel);

                    Option selectedOptions = new Option(
                            semesterMonth,
                            semesterYear,
                            campusCode,
                            levelCode);

                    DataDownloadProcessor dataDownloadProcessor = new DataDownloadProcessor(activity, selectedOptions);

                    observeDataRetrivalProgress(dataDownloadProcessor);
                    observeLocationsDownloadProgress(dataDownloadProcessor);
                    observeCourseDownloadProgress(dataDownloadProcessor);
                    observeLocationsSaveProgress(dataDownloadProcessor);
                    observeCoursesSaveProgress(dataDownloadProcessor);
                    observeDownloadError(dataDownloadProcessor);
                    observeSaveError(dataDownloadProcessor);

                    dataDownloadProcessor.initializeDataDownload();
                }
        );
    }

    private void observeSaveError(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getOnSaveError().observe(
                this,
                hasSaveError -> {
                    if (hasSaveError) {
                        String message = dataDownloadProcessor.getErrorMessage();
                        if (message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void observeDownloadError(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getOnDownloadError().observe(
                this,
                hasDownloadError -> {
                    if (hasDownloadError) {
                        String message = dataDownloadProcessor.getErrorMessage();
                        if (message != null) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    private void observeCoursesSaveProgress(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getDataCoursesSaveProgress().observe(
                this,
                inProgress -> {
                    if (inProgress) {
                        dataRetrievalImageView.setBackground(getDrawable(R.drawable.ic_save_courses));
                    }
                }
        );
    }

    private void observeLocationsSaveProgress(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getDataLocationsSaveProgress().observe(
                this,
                inProgress -> {
                    if (inProgress) {
                        dataRetrievalImageView.setBackground(getDrawable(R.drawable.ic_save_locations));
                    }
                }
        );
    }

    private void observeCourseDownloadProgress(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getDataCoursesDownloadProgress().observe(
                this,
                inProgress -> {
                    if (inProgress) {
                        dataRetrievalImageView.setBackground(getDrawable(R.drawable.ic_download_courses));
                    }
                }
        );
    }

    private void observeLocationsDownloadProgress(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getDataLocationsDownloadProgress().observe(
                this,
                inProgress -> {
                    if (inProgress) {
                        dataRetrievalImageView.setBackground(getDrawable(R.drawable.ic_download_locations));
                    }
                }
        );
    }

    private void observeDataRetrivalProgress(DataDownloadProcessor dataDownloadProcessor) {
        dataDownloadProcessor.getDataRetrievalProgress().observe(
                this,
                inProgress -> {
                    if (inProgress) {
                        setSaveProgressViewsVisible();
                        setButtonsClickable(false);
                    }
                    else {
                        setSaveProgressViewsInvisible();
                        setButtonsClickable(true);
                    }
                }
        );
    }

    private void setSaveProgressViewsInvisible() {
        ObjectAnimator.ofFloat(dimmingView, "alpha", 1f, 0f)
                .setDuration(500)
                .start();
        ObjectAnimator.ofFloat(dataRetrievalProgressBar, "alpha", 1f, 0f)
                .setDuration(500)
                .start();
        ObjectAnimator.ofFloat(dataRetrievalImageView, "alpha", 1f, 0f)
                .setDuration(500)
                .start();
    }

    private void setButtonsClickable(boolean b) {
        semesterButton.setClickable(b);
        campusButton.setClickable(b);
        levelButton.setClickable(b);
        saveButton.setClickable(b);
    }

    private void setSaveProgressViewsVisible() {
        dataRetrievalProgressBar.setVisibility(View.VISIBLE);
        dataRetrievalImageView.setVisibility(View.VISIBLE);
        dimmingView.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(dimmingView, "alpha", 0f, 1f)
                .setDuration(500)
                .start();
        ObjectAnimator.ofFloat(dataRetrievalProgressBar, "alpha", 0f, 1f)
                .setDuration(500)
                .start();
        ObjectAnimator.ofFloat(dataRetrievalImageView, "alpha", 0f, 1f)
                .setDuration(500)
                .start();
    }

    private void setSaveButtonStates() {
        ColorStateList saveButtonColors = ContextCompat.getColorStateList(getApplicationContext(), R.color.save_button_colors);
        ColorStateList saveButtonTextColors = ContextCompat.getColorStateList(getApplicationContext(), R.color.save_button_text_colors);
        this.saveButton.setBackgroundTintList(saveButtonColors);
        this.saveButton.setTextColor(saveButtonTextColors);
    }

    private void setClickListenerToLevelButton() {
        this.levelButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_LEVEL_PICKER);
                }
        );
    }

    private void setClickListenerToCampusButton() {
        this.campusButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_CAMPUS_PICKER);
                }
        );
    }

    private void setClickListenerToSemesterButton() {
        this.semesterButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_SEMESTER_PICKER);
                }
        );
    }

    private void initializeViews() {
        this.semesterButton = findViewById(R.id.options_semester_button);
        this.campusButton = findViewById(R.id.options_campus_button);
        this.levelButton = findViewById(R.id.options_level_button);
        this.saveButton = findViewById(R.id.options_save_button);

        this.dimmingView = findViewById(R.id.dim_act_options);
        this.dataRetrievalProgressBar = findViewById(R.id.data_retrieval_progress_bar);
        this.dataRetrievalImageView = findViewById(R.id.data_retrieval_imageview);
    }

    private void showOptionsPicker(int pickerType) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        removeExistingFragments(fragmentTransaction);

        DialogFragment optionsPickerDialogFragment = OptionsPickerDialogFragment.newInstance(pickerType);
        optionsPickerDialogFragment.show(fragmentTransaction, DIALOG_FRAGMENT_TAG);
    }

    private void removeExistingFragments(FragmentTransaction fragmentTransaction) {
        Fragment previousFragment = getSupportFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
        if (previousFragment != null) {
            fragmentTransaction.remove(previousFragment);
        }
        fragmentTransaction.addToBackStack(null);
    }

    public void enableSaveButton() {
        this.saveButton.setEnabled(true);
    }

    public boolean isAllOptionsPicked() {
        if (
                isSemesterPicked()
                && isCampusPicked()
                && isLevelPicked()
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isSemesterPicked() {
        if (this.semesterButton.getText().equals("Semester")) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isCampusPicked() {
        if (this.campusButton.getText().equals("Campus")) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean isLevelPicked() {
        if (this.levelButton.getText().equals("Level")) {
            return false;
        }
        else {
            return true;
        }
    }
}
