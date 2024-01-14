package blog.mazleo.ruvacant.ui.universityscene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.ui.base.CardSegment;
import blog.mazleo.ruvacant.ui.base.CardValue;
import blog.mazleo.ruvacant.ui.base.CardValueColor;
import dagger.hilt.android.scopes.ActivityScoped;
import java.util.List;
import javax.inject.Inject;

/** Adapter for the recycler view of the university scene. */
@ActivityScoped
public final class UniversitySceneAdapter extends RecyclerView.Adapter {

  private final UniversitySceneDataManager dataManager;

  @Inject
  UniversitySceneAdapter(UniversitySceneDataManager dataManager) {
    this.dataManager = dataManager;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext())
            .inflate(R.layout.content_card, parent, /* attachToRoot= */ false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    CardValue card = dataManager.getCards().get(position);
    ViewHolder viewHolder = (ViewHolder) holder;
    viewHolder.setCode(card.code());
    viewHolder.setColorTheme(card.colorTheme());
    viewHolder.setTitle(card.title());
    viewHolder.setDescription(card.description());
    viewHolder.setSegments(card.segments());
    viewHolder.setLetterDivider(card.title(), position, dataManager.getLetterIndex());
  }

  @Override
  public int getItemCount() {
    return dataManager.getCards().size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private View containerView;
    private TextView code;
    private ImageView background;
    private TextView title;
    private TextView description;
    private ConstraintLayout segments;
    private TextView letterDivider;

    public ViewHolder(View view) {
      super(view);

      containerView = view;
      code = (TextView) view.findViewById(R.id.card_code_text);
      background = (ImageView) view.findViewById(R.id.card_background);
      title = (TextView) view.findViewById(R.id.card_title);
      description = (TextView) view.findViewById(R.id.card_description);
      segments = (ConstraintLayout) view.findViewById(R.id.card_segments);
      letterDivider = (TextView) view.findViewById(R.id.letter_divider);
    }

    public void setCode(String codeString) {
      code.setText(codeString);
    }

    public void setColorTheme(CardValueColor color) {
      if (color.equals(CardValueColor.BLUE)) {
        background.setImageDrawable(
            containerView
                .getResources()
                .getDrawable(R.drawable.blue_card, containerView.getContext().getTheme()));
        setTextColor(R.color.white);
      } else if (color.equals(CardValueColor.YELLOW)) {
        background.setImageDrawable(
            containerView
                .getResources()
                .getDrawable(R.drawable.yellow_card, containerView.getContext().getTheme()));
        setTextColor(R.color.black);
      } else if (color.equals(CardValueColor.GREEN)) {
        background.setImageDrawable(
            containerView
                .getResources()
                .getDrawable(R.drawable.green_card, containerView.getContext().getTheme()));
        setTextColor(R.color.black);
      } else if (color.equals(CardValueColor.PURPLE)) {
        background.setImageDrawable(
            containerView
                .getResources()
                .getDrawable(R.drawable.purple_card, containerView.getContext().getTheme()));
        setTextColor(R.color.white);
      } else if (color.equals(CardValueColor.RED)) {
        background.setImageDrawable(
            containerView
                .getResources()
                .getDrawable(R.drawable.red_card, containerView.getContext().getTheme()));
        setTextColor(R.color.white);
      }
    }

    public void setTitle(String titleString) {
      title.setText(titleString);
    }

    public void setDescription(String descriptionString) {
      description.setText(descriptionString);
    }

    private void setTextColor(int colorId) {
      title.setTextColor(
          containerView.getResources().getColor(colorId, containerView.getContext().getTheme()));
      description.setTextColor(
          containerView.getResources().getColor(colorId, containerView.getContext().getTheme()));
    }

    private void setSegments(List<CardSegment> cardSegments) {
      ConstraintSet constraintSet = new ConstraintSet();
      int[] chainIds = new int[cardSegments.size()];
      float[] weights = new float[cardSegments.size()];
      for (int s = 0; s < cardSegments.size(); s++) {
        CardSegment cardSegment = cardSegments.get(s);
        View segmentView = buildSegmentView(cardSegment);
        segmentView.setId(View.generateViewId());
        chainIds[s] = segmentView.getId();
        weights[s] = cardSegment.getPercentage();
        segments.addView(segmentView);
      }
      constraintSet.clone(segments);
      constraintSet.createHorizontalChain(
          ConstraintSet.PARENT_ID,
          ConstraintSet.LEFT,
          ConstraintSet.PARENT_ID,
          ConstraintSet.RIGHT,
          chainIds,
          weights,
          ConstraintSet.CHAIN_SPREAD);
      constraintSet.applyTo(segments);
    }

    private void setLetterDivider(String title, int position, int[] letterIndex) {
      char letter = title.charAt(0);
      int index = letter - 'a';
      if (letterIndex[index] == position) {
        letterDivider.setText(String.valueOf(letter).toUpperCase());
        letterDivider.setVisibility(View.VISIBLE);
      } else {
        letterDivider.setVisibility(View.GONE);
      }
    }

    private View buildSegmentView(CardSegment cardSegment) {
      View segmentView =
          LayoutInflater.from(containerView.getContext())
              .inflate(R.layout.card_segment, (ViewGroup) containerView, /* attachToRoot= */ false);
      ConstraintLayout.LayoutParams params =
          new ConstraintLayout.LayoutParams(/* width= */ 0, ViewGroup.LayoutParams.MATCH_PARENT);
      segmentView.setLayoutParams(params);
      segmentView.setBackgroundColor(cardSegment.getColor());
      return segmentView;
    }
  }
}
