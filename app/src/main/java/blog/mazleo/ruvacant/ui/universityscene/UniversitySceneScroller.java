package blog.mazleo.ruvacant.ui.universityscene;

import static blog.mazleo.ruvacant.util.Assertions.assertNotNull;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.ui.base.SceneDataManager;
import blog.mazleo.ruvacant.ui.content.RecyclerViewScroller;
import java.util.ArrayList;
import java.util.List;

/** Scroller for the university scene. */
public final class UniversitySceneScroller extends LinearLayout implements RecyclerViewScroller {

  private static final int SCROLLER_HIDE_DELAY = 3000;
  private static final String VISIBILITY_MESSAGE_ID = "VISIBILITY_MESSAGE";

  private SceneDataManager dataManager;
  private RecyclerView recyclerView;

  private final StateModel stateModel = new StateModel();
  private List<String> letterList = new ArrayList<>();
  private List<TextView> letterViews = new ArrayList<>();
  private Handler handler = new Handler();
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
    setVisibilityStateObservers();
    setVisibilityStateSetters();
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

  private void setVisibilityStateSetters() {
    recyclerView.addOnScrollListener(
        new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING
                || newState == RecyclerView.SCROLL_STATE_SETTLING) {
              stateModel.getStateObservable().set(StateModel.State.VISIBLE);
            } else if (newState == RecyclerView.SCROLL_STATE_IDLE
                && stateModel.getStateObservable().get() != StateModel.State.SCROLLING) {
              stateModel.getStateObservable().set(StateModel.State.GONE);
            }
          }
        });
  }

  private void setVisibilityStateObservers() {
    stateModel
        .getStateObservable()
        .addOnPropertyChangedCallback(
            new Observable.OnPropertyChangedCallback() {
              @Override
              public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableInt) sender).get() == StateModel.State.VISIBLE) {
                  UniversitySceneScroller.this.setVisibility(VISIBLE);
                  UniversitySceneScroller.this.setAlpha(0.5F);
                  cancelHide();
                  return;
                } else if (((ObservableInt) sender).get() == StateModel.State.GONE) {
                  handler.postDelayed(
                      () -> UniversitySceneScroller.this.setVisibility(GONE),
                      VISIBILITY_MESSAGE_ID,
                      SCROLLER_HIDE_DELAY);
                  return;
                } else if (((ObservableInt) sender).get() == StateModel.State.SCROLLING) {
                  UniversitySceneScroller.this.setVisibility(VISIBLE);
                  UniversitySceneScroller.this.setAlpha(1F);
                  cancelHide();
                  return;
                }
                throw new IllegalArgumentException("State not supported.");
              }
            });
  }

  private void cancelHide() {
    handler.removeCallbacksAndMessages(VISIBILITY_MESSAGE_ID);
  }

  private void setScrollOnTouch() {
    setOnTouchListener(this::scrollOnEnter);
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
      stateModel.getStateObservable().set(StateModel.State.GONE);
      setAlpha(0.5F);
      return true;
    }
    if (event.getX() < 0 || event.getX() > view.getWidth()) {
      scrollerPosition = -1;
      removeThumb();
      stateModel.getStateObservable().set(StateModel.State.SCROLLING);
      return true;
    }
    int position = positionFromY(event.getY());
    if (positionWithinBounds(position) && position != scrollerPosition) {
      onPositionExit(position);
      onPositionEnter(position);
    } else if (positionWithinBounds(position)) {
      stateModel.getStateObservable().set(StateModel.State.SCROLLING);
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
    stateModel.getStateObservable().set(StateModel.State.SCROLLING);
    setAlpha(1F);
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

  static class StateModel {

    private final ObservableInt stateObservable = new ObservableInt(State.GONE);

    ObservableInt getStateObservable() {
      return stateObservable;
    }

    static class State {
      static int VISIBLE = 1;
      static int GONE = 2;
      static int SCROLLING = 3;
    }
  }
}
