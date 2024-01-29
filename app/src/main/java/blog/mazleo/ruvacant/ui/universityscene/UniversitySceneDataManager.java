package blog.mazleo.ruvacant.ui.universityscene;

import android.util.Log;
import androidx.databinding.Observable;
import androidx.databinding.ObservableInt;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.service.database.DatabaseService;
import blog.mazleo.ruvacant.service.model.RuBuilding;
import blog.mazleo.ruvacant.service.model.RuClassroom;
import blog.mazleo.ruvacant.service.model.RuMeeting;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.service.state.UniversityContext;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import blog.mazleo.ruvacant.ui.base.BaseCardValue;
import blog.mazleo.ruvacant.ui.base.CampusCardColorUtil;
import blog.mazleo.ruvacant.ui.base.CardSegment;
import blog.mazleo.ruvacant.ui.base.CardValue;
import blog.mazleo.ruvacant.ui.base.SceneDataManager;
import dagger.hilt.android.scopes.ActivityScoped;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;

@ActivityScoped
/** Data manager for the University scene. */
public final class UniversitySceneDataManager implements SceneDataManager {

  private static final int HOURS_PER_DAY = 24;
  private static final int MINUTES_PER_HOUR = 60;
  private static final int MINUTES_TIME_BLOCK = 5;
  private static final int NUM_TIME_BLOCKS =
      (HOURS_PER_DAY * MINUTES_PER_HOUR) / MINUTES_TIME_BLOCK;
  private static final int NUM_LETTERS = 27;

  private final String appName;
  private final ApplicationStateManager stateManager;
  private final DatabaseService databaseService;
  private final SharedApplicationData sharedApplicationData;
  private final ExecutorService executorService;

  private final List<CardValue> cards = new ArrayList<>();
  private final List<CardValue> filteredCards = new ArrayList<>();
  private final int[] letterIndex = new int[NUM_LETTERS];
  private UniversityContext universityContext;
  private ObservableInt cardStack = new ObservableInt();

  @Inject
  UniversitySceneDataManager(
      @AppName String appName,
      ApplicationStateManager stateManager,
      DatabaseService databaseService,
      SharedApplicationData sharedApplicationData,
      ExecutorService executorService) {
    this.appName = appName;
    this.stateManager = stateManager;
    this.databaseService = databaseService;
    this.sharedApplicationData = sharedApplicationData;
    this.executorService = executorService;
    cardStack.addOnPropertyChangedCallback(endCardBuildIfComplete());
    Arrays.fill(letterIndex, -1);
  }

  @Override
  public List<CardValue> getCards() {
    return cards;
  }

  @Override
  public void buildCards() {
    if (!sharedApplicationData.containsData(ApplicationData.UNIVERSITY_CONTEXT.getTag())) {
      Log.d(appName, "University context not ready to load.");
      stateManager.exitState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState());
      return;
    }
    buildCardForEachBuilding();
  }

  @Override
  public void sortCards() {
    cards.sort(Comparator.comparing(CardValue::title, String::compareTo));
    filteredCards.addAll(cards);
    buildLetterIndex();
    stateManager.exitState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADING.getState());
    stateManager.enterState(ApplicationState.UNIVERSITY_SCENE_DATA_LOADED.getState());
  }

  @Override
  public int[] getScrollerPositionIndex() {
    return letterIndex;
  }

  public List<CardValue> getFilteredCards() {
    return filteredCards;
  }

  public void setFilteredCards(List<CardValue> filteredCards) {
    this.filteredCards.clear();
    this.filteredCards.addAll(filteredCards);
  }

  public void buildLetterIndex() {
    int c = 0;
    char lastLetter = 0;
    for (CardValue card : filteredCards) {
      char letter = card.title().charAt(0);
      if (letter != lastLetter) {
        letterIndex[letter - 'a'] = c;
        lastLetter = letter;
      }
      c++;
    }
  }

  private void buildCardForEachBuilding() {
    universityContext =
        (UniversityContext)
            sharedApplicationData.getData(ApplicationData.UNIVERSITY_CONTEXT.getTag());
    List<RuBuilding> buildings =
        databaseService.getAllBuildingsInUniCampus(universityContext.campusCode);
    for (RuBuilding building : buildings) {
      executorService.execute(() -> buildCard(building));
    }
    cardStack.set(buildings.size());
  }

  private void buildCard(RuBuilding building) {
    BaseCardValue.Builder cardBuilder = BaseCardValue.builder();
    cardBuilder
        .setTitle(building.name != null ? building.name : "rutgers building")
        .setCode(building.code)
        .setColorTheme(CampusCardColorUtil.getColorThemeOfBuilding(building));
    List<RuClassroom> classrooms = databaseService.getAllClassroomsInBuilding(building.code);
    int numVacantClassrooms = 0;
    for (RuClassroom classroom : classrooms) {
      String semesterCode =
          String.format(
              "%s%s", universityContext.semesterMonthCode, universityContext.semesterYearCode);
      List<RuMeeting> meetings =
          databaseService.getMeetings(
              semesterCode,
              building.code,
              classroom.code,
              Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
      int currentMinute = getCurrentMinute();
      boolean[] timeBlocks = new boolean[NUM_TIME_BLOCKS];
      for (RuMeeting meeting : meetings) {
        for (int m = meeting.start; m < meeting.end; m += MINUTES_TIME_BLOCK) {
          timeBlocks[mapTimeBlockIndex(m)] = true;
        }
      }
      if (!timeBlocks[mapTimeBlockIndex(currentMinute)]) {
        numVacantClassrooms++;
      }
    }
    cardBuilder.setDescription(
        String.format("%s out of %s vacant", numVacantClassrooms, classrooms.size()));
    List<CardSegment> segments = new ArrayList<>();
    segments.add(
        new CardSegment(
            getPercentageInt(numVacantClassrooms, classrooms.size()), CardSegment.GREEN_DEFAULT));
    segments.add(
        new CardSegment(
            getPercentageInt(classrooms.size() - numVacantClassrooms, classrooms.size()),
            CardSegment.RED_DEFAULT));
    cardBuilder.setSegments(segments);
    addCard(cardBuilder.build());
  }

  private synchronized void addCard(CardValue card) {
    cards.add(card);
    cardStack.set(cardStack.get() - 1);
  }

  private int getPercentageInt(int amount, int outOf) {
    return (int) Math.round(((double) amount / (double) outOf) * 100.0);
  }

  private int getCurrentMinute() {
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.HOUR_OF_DAY) * MINUTES_PER_HOUR + calendar.get(Calendar.MINUTE);
  }

  private int mapTimeBlockIndex(int minute) {
    return (int) Math.floor(minute / MINUTES_TIME_BLOCK);
  }

  private Observable.OnPropertyChangedCallback endCardBuildIfComplete() {
    return new Observable.OnPropertyChangedCallback() {
      @Override
      public void onPropertyChanged(Observable sender, int propertyId) {
        if (((ObservableInt) sender).get() == 0) {
          sortCards();
        }
      }
    };
  }
}
