package blog.mazleo.ruvacant.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.view.dialogfragments.OptionsPickerDialogFragment;

public class OptionsActivity extends AppCompatActivity {

    private static final String DIALOG_FRAGMENT_TAG = "OPTIONS_DIALOG_FRAGMENT";

    public Button semesterButton;
    public Button campusButton;
    public Button levelButton;
    public Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        this.semesterButton = findViewById(R.id.options_semester_button);
        this.campusButton = findViewById(R.id.options_campus_button);
        this.levelButton = findViewById(R.id.options_level_button);
        this.saveButton = findViewById(R.id.options_save_button);

        this.semesterButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_SEMESTER_PICKER);
                }
        );
        this.campusButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_CAMPUS_PICKER);
                }
        );
        this.levelButton.setOnClickListener(
                view -> {
                    showOptionsPicker(OptionsPickerDialogFragment.TYPE_LEVEL_PICKER);
                }
        );
        ColorStateList saveButtonColors = ContextCompat.getColorStateList(getApplicationContext(), R.color.save_button_colors);
        ColorStateList saveButtonTextColors = ContextCompat.getColorStateList(getApplicationContext(), R.color.save_button_text_colors);
        this.saveButton.setBackgroundTintList(saveButtonColors);
        this.saveButton.setTextColor(saveButtonTextColors);
        this.saveButton.setOnClickListener(
                view -> {
                    // TODO: If saved enabled, process data
                    Toast.makeText(getApplicationContext(), "SAVE BUTTON CLICKED", Toast.LENGTH_SHORT).show();
                }
        );
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
