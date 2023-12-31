package blog.mazleo.ruvacant.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import blog.mazleo.ruvacant.R;
import java.util.List;

/** Creates the selection dialog. */
public final class UniversitySelectionDialogFactory {

  public static Dialog create(String selection, Button dialogSelectionButton, Context context) {
    Dialog dialog = new Dialog(context);
    LinearLayout layout =
        (LinearLayout)
            LayoutInflater.from(context)
                .inflate(
                    R.layout.university_selection_dialog,
                    new FrameLayout(context),
                    /* attachToRoot= */ false);
    List<String> options = UniversitySelectionProvider.get(selection);
    setupButton(
        (Button) layout.findViewById(R.id.uni_selection_option_1),
        dialogSelectionButton,
        options.get(0),
        dialog);
    setupButton(
        (Button) layout.findViewById(R.id.uni_selection_option_2),
        dialogSelectionButton,
        options.get(1),
        dialog);
    if (options.size() == 3) {
      setupButton(
          (Button) layout.findViewById(R.id.uni_selection_option_3),
          dialogSelectionButton,
          options.get(2),
          dialog);
    } else {
      layout.findViewById(R.id.uni_selection_option_3).setVisibility(View.GONE);
    }
    dialog.setContentView(layout);
    dialog.setCancelable(true);
    return dialog;
  }

  private static void setupButton(
      Button buttonOption, Button dialogSelectionButton, String text, Dialog dialog) {
    buttonOption.setText(text);
    buttonOption.setOnClickListener(
        clickedButton -> {
          dialogSelectionButton.setText(((Button) clickedButton).getText());
          dialog.dismiss();
        });
  }
}
