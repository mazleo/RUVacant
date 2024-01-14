package blog.mazleo.ruvacant.ui.content;

import static blog.mazleo.ruvacant.ui.content.BackgroundUtil.getBackgroundColorAccentId;
import static blog.mazleo.ruvacant.ui.content.BackgroundUtil.getBackgroundColorId;
import static blog.mazleo.ruvacant.ui.content.BackgroundUtil.getBackgroundDrawableId;
import static blog.mazleo.ruvacant.ui.content.BackgroundUtil.getForegroundColorId;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import com.google.android.material.button.MaterialButton;
import dagger.hilt.android.AndroidEntryPoint;
import javax.inject.Inject;

/** The base fragment for the app's content UIs. */
@AndroidEntryPoint
public class ContentFragment extends Fragment {

  private String title;
  private @Nullable String subtitle;
  private ContentActivityType contentActivityType;
  private @Nullable String campusName;
  private String uniCampusName;
  private String level;

  @Inject SharedApplicationData sharedApplicationData;
  @Inject ContentActivityInfoUtil contentActivityInfoUtil;
  @Inject ApplicationStateManager stateManager;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ContentActivityInfo contentActivityInfo =
        (ContentActivityInfo)
            sharedApplicationData.getData(ApplicationData.CONTENT_ACTIVITY.getTag());
    title = contentActivityInfo.title;
    subtitle = contentActivityInfo.subtitle;
    contentActivityType = contentActivityInfo.contentActivityType;
    campusName = contentActivityInfo.campusName;
    uniCampusName = contentActivityInfo.uniCampusName;
    level = contentActivityInfo.level;
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_content, container, /* attachToRoot= */ false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setTitle();
    setSubtitle();
    setBackground();
    setBackgroundImage();
    setMenuButtonColor();
  }

  @Override
  public void onResume() {
    super.onResume();
    if (stateManager.isInState(ApplicationState.UNIVERSITY_SCENE.getState())) {
      stateManager.enterState(ApplicationState.UNIVERSITY_SCENE_ON_RUN.getState());
    }
  }

  private void setTitle() {
    ((TextView) getView().findViewById(R.id.content_title)).setText(title);
  }

  private void setSubtitle() {
    TextView subtitleView = (TextView) getView().findViewById(R.id.content_subtitle);
    if (subtitle != null) {
      subtitleView.setText(subtitle);
      subtitleView.setVisibility(View.VISIBLE);
    } else {
      subtitleView.setVisibility(View.GONE);
    }
    ((TextView) getView().findViewById(R.id.uni_campus_name)).setText(uniCampusName);
    ((TextView) getView().findViewById(R.id.uni_level)).setText(level);
  }

  private void setBackground() {
    getView()
        .findViewById(R.id.content_container)
        .setBackgroundColor(
            getResources()
                .getColor(
                    getBackgroundColorId(contentActivityType, campusName),
                    getContext().getTheme()));
    getActivity()
        .getWindow()
        .setStatusBarColor(
            getResources()
                .getColor(
                    getBackgroundColorAccentId(contentActivityType, campusName),
                    getContext().getTheme()));
  }

  private void setBackgroundImage() {
    ImageView backgroundImage = (ImageView) getView().findViewById(R.id.header_image);
    backgroundImage.setImageDrawable(
        getResources()
            .getDrawable(getBackgroundDrawableId(contentActivityType), getContext().getTheme()));
    backgroundImage.setColorFilter(
        getResources()
            .getColor(
                getBackgroundColorAccentId(contentActivityType, campusName),
                getContext().getTheme()));
  }

  private void setMenuButtonColor() {
    ((MaterialButton) getView().findViewById(R.id.content_menu))
        .setIconTint(
            ColorStateList.valueOf(
                getResources()
                    .getColor(
                        getForegroundColorId(contentActivityType, campusName),
                        getContext().getTheme())));
  }
}
