package blog.mazleo.ruvacant.ui.universityscene;

import static blog.mazleo.ruvacant.util.Assertions.assertNotNull;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.ui.base.SceneDataManager;
import blog.mazleo.ruvacant.ui.content.RecyclerViewScroller;
import java.util.ArrayList;
import java.util.List;

/** Scroller for the university scene. */
public final class UniversitySceneScroller extends LinearLayout implements RecyclerViewScroller {

  private SceneDataManager dataManager;
  private RecyclerView recyclerView;

  private List<String> letterList = new ArrayList<>();
  private List<TextView> letterViews = new ArrayList<>();
  private boolean[] letterScrollEnablement;
  private int[] positionIndex;
  ConstraintLayout contentContainer;
  FrameLayout scrollerTab;
  private TextView thumb;
  private int scrollerPosition = -1;

  public UniversitySceneScroller(Context context) {
    super(context);
  }

  public UniversitySceneScroller(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public UniversitySceneScroller(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public UniversitySceneScroller(
      Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public void buildScroller() {
    assertNotNull(dataManager);
    assertNotNull(recyclerView);

    contentContainer = getRootView().findViewById(R.id.content_container);
    scrollerTab = getRootView().findViewById(R.id.content_scroller_tab);
    positionIndex = dataManager.getScrollerPositionIndex();
    buildLetterList();
    buildLetterViews();
    buildThumb();
    setScrollOnTouch();
  }

  @Override
  public void setDataManager(SceneDataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Override
  public void setRecyclerView(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
  }

  private void buildLetterList() {
    for (int l = 0; l < positionIndex.length; l++) {
      if (positionIndex[l] != -1) {
        letterList.add(String.valueOf((char) (l + 'A')));
      }
    }
  }

  private void buildLetterViews() {
    for (int l = 0; l < letterList.size(); l++) {
      String letter = letterList.get(l);
      TextView letterView =
          (TextView)
              ((ViewGroup) inflate(getContext(), R.layout.scroller_item, this)).getChildAt(l);
      letterView.setText(letter);
      letterViews.add(letterView);
    }
  }

  private void setScrollOnTouch() {
    fillScrollEnablementMap();
    setOnTouchListener(this::scrollOnEnter);
  }

  private void fillScrollEnablementMap() {
    letterScrollEnablement = new boolean[letterList.size()];
    for (int l = 0; l < letterScrollEnablement.length; l++) {
      letterScrollEnablement[0] = true;
    }
  }

  private void buildThumb() {
    thumb =
        inflate(getContext(), R.layout.scroller_thumb, contentContainer)
            .findViewById(R.id.content_scroller_thumb);
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(contentContainer);
    constraintSet.connect(thumb.getId(), ConstraintSet.TOP, scrollerTab.getId(), ConstraintSet.TOP);
    constraintSet.connect(
        thumb.getId(), ConstraintSet.BOTTOM, scrollerTab.getId(), ConstraintSet.BOTTOM);
    constraintSet.connect(
        thumb.getId(), ConstraintSet.RIGHT, scrollerTab.getId(), ConstraintSet.LEFT);
    constraintSet.applyTo(contentContainer);
  }

  private boolean scrollOnEnter(View view, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      scrollerPosition = -1;
      removeThumb();
      return true;
    }
    if (event.getX() < 0 || event.getX() > view.getWidth()) {
      scrollerPosition = -1;
      removeThumb();
      return true;
    }
    int position = positionFromY(event.getY());
    if (positionWithinBounds(position) && position != scrollerPosition) {
      onPositionExit(position);
      onPositionEnter(position);
    }
    return true;
  }

  private void onPositionEnter(int position) {
    String letter = letterList.get(position);
    char letterChar = letter.charAt(0);
    int scrollPositionsIndex = letterChar - 'A';
    int scrollTo = positionIndex[scrollPositionsIndex];
    presentThumb(position);
    smoothScrollToPosition(scrollTo);
  }

  private void presentThumb(int textViewIndex) {
    thumb.setVisibility(VISIBLE);
    TextView letterView = letterViews.get(textViewIndex);
    float verticalBias =
        (float) textViewIndex / (float) (letterList.size() - 1)
            + (((float) thumb.getHeight() / 2) / getHeight());
    ConstraintSet constraintSet = new ConstraintSet();
    constraintSet.clone(contentContainer);
    constraintSet.setVerticalBias(thumb.getId(), verticalBias);
    constraintSet.applyTo(contentContainer);
    thumb.setText(letterView.getText());
  }

  private void smoothScrollToPosition(int scrollTo) {
    RecyclerView.SmoothScroller smoothScroller =
        new LinearSmoothScroller(getContext()) {
          @Override
          protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
          }
        };
    smoothScroller.setTargetPosition(scrollTo);
    recyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
  }

  private void onPositionExit(int newPosition) {
    scrollerPosition = newPosition;
    removeThumb();
  }

  private void removeThumb() {
    thumb.setVisibility(GONE);
  }

  private boolean positionWithinBounds(int position) {
    return position >= 0 && position < letterList.size();
  }

  private int positionFromY(float y) {
    int itemHeight = letterViews.get(0).getHeight();
    return (int) Math.floor((double) y / (double) itemHeight);
  }
}
