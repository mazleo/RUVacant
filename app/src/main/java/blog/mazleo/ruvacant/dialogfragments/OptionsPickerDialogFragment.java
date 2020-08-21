package blog.mazleo.ruvacant.dialogfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.activities.OptionsActivity;
import blog.mazleo.ruvacant.utils.OptionsUtil;

public class OptionsPickerDialogFragment extends DialogFragment {
    public static final int TYPE_SEMESTER_PICKER = 0;
    public static final int TYPE_CAMPUS_PICKER = 1;
    public static final int TYPE_LEVEL_PICKER = 2;
    public static final String PICKER_KEY = "PICKER_TYPE";

    private LinearLayout optionsPickerContainer;
    private int pickerType;

    public OptionsPickerDialogFragment(int pickerType) {
        super();
        this.pickerType = pickerType;
    }

    public static OptionsPickerDialogFragment newInstance(int pickerType) {
        OptionsPickerDialogFragment dialogFragment = new OptionsPickerDialogFragment(pickerType);

        Bundle args = new Bundle();
        args.putInt(PICKER_KEY, pickerType);
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pickerType = getArguments().getInt(PICKER_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_options_picker, container, false);
        this.optionsPickerContainer = view.findViewById(R.id.options_picker_container);

        List<Button> optionsPickerButtons = new ArrayList<>();
        generateOptionButtonsByPickerType(optionsPickerButtons);
        addGeneratedOptionButtonsToLayout(optionsPickerButtons);

        return view;
    }

    private void generateOptionButtonsByPickerType(List<Button> optionsPickerButtons) {
        switch (this.pickerType) {
            case TYPE_SEMESTER_PICKER:
                generateSemesterOptionButtons(optionsPickerButtons);
                break;
            case TYPE_CAMPUS_PICKER:
                generateCampusOptionButtons(optionsPickerButtons);
                break;
            case TYPE_LEVEL_PICKER:
                generateLevelOptionButtons(optionsPickerButtons);
                break;
        }
    }

    private void generateLevelOptionButtons(List<Button> optionsPickerButtons) {
        List<String> levelOptions = OptionsUtil.getLevelOptionsStrings();

        for (String option: levelOptions) {
            Button button = new Button(getContext());

            button.setText(option);
            button.getBackground().setAlpha(0);
            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            button.setOnClickListener(
                    buttonView -> {
                        ((OptionsActivity) getActivity())
                                .levelButton
                                .setText(((Button) buttonView).getText());

                        ((DialogFragment) this).dismiss();
                    }
            );
            optionsPickerButtons.add(button);
        }
    }

    private void generateCampusOptionButtons(List<Button> optionsPickerButtons) {
        List<String> campusOptions = OptionsUtil.getCampusOptionsStrings();

        for (String option: campusOptions) {
            Button button = new Button(getContext());

            button.setText(option);
            button.getBackground().setAlpha(0);
            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            button.setOnClickListener(
                    buttonView -> {
                        ((OptionsActivity) getActivity())
                                .campusButton
                                .setText(((Button) buttonView).getText());

                        ((DialogFragment) this).dismiss();
                    }
            );
            optionsPickerButtons.add(button);
        }
    }

    private void addGeneratedOptionButtonsToLayout(List<Button> optionsPickerButtons) {
        for (Button optionButton : optionsPickerButtons) {
            this.optionsPickerContainer.addView(optionButton);
        }
    }

    private void generateSemesterOptionButtons(List<Button> optionsPickerButtons) {
        List<String> semesterOptions = OptionsUtil.getSemesterOptionsStrings();
        for (String option : semesterOptions) {
            Button optionButton = new Button(getContext());

            optionButton.setText(option);
            optionButton.getBackground().setAlpha(0);
            optionButton.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            optionButton.setOnClickListener(
                    buttonView -> {
                        ((OptionsActivity) getActivity())
                                .semesterButton
                                .setText(((Button) buttonView).getText());

                        ((DialogFragment) this).dismiss();
                    }
            );

            optionsPickerButtons.add(optionButton);
        }
    }
}
