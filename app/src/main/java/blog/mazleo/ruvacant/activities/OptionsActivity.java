package blog.mazleo.ruvacant.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.dialogfragments.OptionsPickerDialogFragment;

public class OptionsActivity extends AppCompatActivity {

    private static final String DIALOG_FRAGMENT_TAG = "OPTIONS_DIALOG_FRAGMENT";

    public Button semesterButton;
    public Button campusButton;
    public Button levelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        this.semesterButton = findViewById(R.id.options_semester_button);
        this.campusButton = findViewById(R.id.options_campus_button);
        this.levelButton = findViewById(R.id.options_level_button);

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

}
