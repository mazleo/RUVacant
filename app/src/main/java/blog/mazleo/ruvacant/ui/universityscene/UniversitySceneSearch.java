package blog.mazleo.ruvacant.ui.universityscene;

import androidx.databinding.ObservableInt;
import blog.mazleo.ruvacant.ui.base.CardValue;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 * Class responsible for handling building searches in the university scene. The search algorithm
 * includes creating an index of letters and cards that contain such letters. Each query
 * modification creates a new index that filters for the character that is added or removed. The
 * list is then sorted by relevance according to the query characters and filtered where order
 * matters.
 */
public final class UniversitySceneSearch {

  @AssistedFactory
  public interface Factory {
    UniversitySceneSearch create(List<CardValue> allCards);
  }

  private final ObservableInt stateObservable = new ObservableInt(State.IDLE);
  private final Queue<QueryMod> queryModQueue = new LinkedList<>();
  private final List<QueryNode> index = new ArrayList<>();
  private final List<CardValue> allCards;
  private final ExecutorService executorService;

  private final List<CardValue> filteredCards = new ArrayList<>();
  private String query = "";

  @AssistedInject
  UniversitySceneSearch(ExecutorService executorService, @Assisted List<CardValue> allCards) {
    this.allCards = allCards;
    this.executorService = executorService;
  }

  public void onQueryMod(String newQuery) {
    if (newQuery.isEmpty()) {
      queryModQueue.add(new QueryMod(QueryMod.Mod.CLEAR));
      onIndex();
      return;
    }

    int c = 0;
    while (c < Math.max(query.length(), newQuery.length())) {
      char queryChar = c < query.length() ? query.charAt(c) : 0;
      char newQueryChar = c < newQuery.length() ? newQuery.charAt(c) : 0;
      if (queryChar != newQueryChar) {
        if (newQuery.length() > query.length()) {
          queryModQueue.add(new QueryMod(QueryMod.Mod.ADD, c, newQueryChar, newQuery));
          break;
        } else if (newQuery.length() < query.length()) {
          queryModQueue.add(new QueryMod(QueryMod.Mod.REMOVE, c, newQuery));
          break;
        }
      }
      c++;
    }

    query = newQuery;
    if (!queryModQueue.isEmpty() && stateObservable.get() != State.INDEX) {
      executorService.execute(this::onIndex);
    }
  }

  public List<CardValue> getFilteredCards() {
    return filteredCards;
  }

  public ObservableInt getStateObservable() {
    return stateObservable;
  }

  private synchronized void onIndex() {
    if (queryModQueue.isEmpty()) {
      aggregateResults();
      return;
    }
    if (stateObservable.get() != State.INDEX) {
      stateObservable.set(State.INDEX);
    }
    QueryMod mod = queryModQueue.poll();
    if (mod.mod == QueryMod.Mod.ADD) {
      addToIndex(mod);
    } else if (mod.mod == QueryMod.Mod.REMOVE) {
      removeFromIndex(mod);
    } else if (mod.mod == QueryMod.Mod.CLEAR) {
      index.clear();
      query = "";
    }
    onIndex();
  }

  private synchronized void aggregateResults() {
    if (stateObservable.get() != State.AGGREGATE) {
      stateObservable.set(State.AGGREGATE);
    }
    filteredCards.clear();
    if (index.isEmpty()) {
      filteredCards.addAll(allCards);
      stateObservable.set(State.IDLE);
    } else {
      for (List<CardValue> cardList : index.get(index.size() - 1).queryCharIndex.values()) {
        for (CardValue card : cardList) {
          if (!filteredCards.contains(card)) {
            filteredCards.add(card);
          }
        }
      }
      sort();
    }
  }

  private synchronized void sort() {
    if (stateObservable.get() != State.SORT) {
      stateObservable.set(State.SORT);
    }
    filteredCards.sort(
        (card1, card2) -> {
          int card1Rank = rankString(card1.title());
          int card2Rank = rankString(card2.title());
          return Integer.compare(card1Rank, card2Rank);
        });
    filter();
  }

  private synchronized void filter() {
    if (stateObservable.get() != State.FILTER) {
      stateObservable.set(State.FILTER);
    }
    List<CardValue> cardsToRemove = new ArrayList<>();
    for (CardValue card : filteredCards) {
      int c = 0;
      int matchingTitleIndex = -1;
      int matchingCodeIndex = -1;
      boolean firstPass = true;
      while (c < query.length()) {
        char queryChar = query.charAt(c);
        if (matchingTitleIndex != -1 || firstPass) {
          matchingTitleIndex =
              matchingTitleIndex < card.title().length()
                  ? card.title().indexOf(queryChar, matchingTitleIndex + 1)
                  : -1;
        }
        if (matchingCodeIndex != -1 || firstPass) {
          matchingCodeIndex =
              matchingCodeIndex < card.code().length()
                  ? card.code().indexOf(queryChar, matchingCodeIndex + 1)
                  : -1;
        }
        firstPass = false;
        c++;
        if (matchingCodeIndex == -1 && matchingTitleIndex == -1) {
          cardsToRemove.add(card);
        }
      }
    }
    for (CardValue cardToRemove : cardsToRemove) {
      filteredCards.remove(cardToRemove);
    }
    stateObservable.set(State.IDLE);
  }

  private int rankString(String string) {
    if (query.isEmpty()) {
      return 0;
    }
    int rank = 0;
    int matchingIndex = 0;
    int previousMatchingIndex = -1;
    int c = 0;
    while (c < query.length() && c < string.length()) {
      char targetChar = query.charAt(c);
      matchingIndex = string.indexOf(targetChar, previousMatchingIndex + 1);
      if (matchingIndex == -1) {
        rank += (string.length() - 1) - previousMatchingIndex;
        break;
      } else {
        rank += matchingIndex - previousMatchingIndex - 1;
      }
      previousMatchingIndex = matchingIndex;
      c++;
    }
    if (query.length() != string.length()) {
      rank += Math.abs(query.length() - string.length());
    }
    return rank;
  }

  private void addToIndex(QueryMod mod) {
    if (index.isEmpty()) {
      index.add(new QueryNode(mod.newChar, allCards));
    } else {
      List<CardValue> previousCardMatches =
          index.get(index.size() - 1).queryCharIndex.get(mod.newChar);
      index.add(new QueryNode(mod.newChar, previousCardMatches));
    }
  }

  private void removeFromIndex(QueryMod mod) {
    int n = mod.index;
    while (n < index.size() - 1) {
      QueryNode subsequentNode = index.get(n + 1);
      QueryNode previousNode = index.get(n - 1);
      index.remove(n);
      index.add(
          n,
          new QueryNode(
              subsequentNode.queryChar, previousNode.queryCharIndex.get(subsequentNode.queryChar)));
      n++;
    }
    index.remove(n);
  }

  static class QueryNode {

    Character queryChar;
    Map<Character, List<CardValue>> queryCharIndex;

    QueryNode(Character queryChar, List<CardValue> previousCardMatches) {
      this.queryChar = queryChar;
      index(previousCardMatches);
    }

    void index(List<CardValue> previousCardMatches) {
      queryCharIndex = new HashMap<>();
      for (CardValue card : previousCardMatches) {
        indexString(card.title(), card);
        indexString(card.code(), card);
      }
    }

    void indexString(String string, CardValue card) {
      String lowercaseString = string.toLowerCase();
      for (int c = 0; c < lowercaseString.length(); c++) {
        Character currentChar = lowercaseString.charAt(c);
        if (!queryCharIndex.containsKey(currentChar)) {
          queryCharIndex.put(currentChar, new ArrayList<>());
        }
        if (!queryCharIndex.get(currentChar).contains(card)) {
          queryCharIndex.get(currentChar).add(card);
        }
      }
    }
  }

  static class QueryMod {

    Mod mod;
    int index;
    char newChar;
    String newQuery;

    QueryMod(Mod mod) {
      this.mod = mod;
    }

    QueryMod(Mod mod, int index, String newQuery) {
      this.mod = mod;
      this.index = index;
      this.newQuery = newQuery;
    }

    QueryMod(Mod mod, int index, char newChar, String newQuery) {
      this.mod = mod;
      this.index = index;
      this.newChar = newChar;
      this.newQuery = newQuery;
    }

    enum Mod {
      ADD,
      REMOVE,
      CLEAR
    }
  }

  public static class State {
    public static int IDLE = 0;
    public static int INDEX = 1;
    public static int AGGREGATE = 2;
    public static int SORT = 3;
    public static int FILTER = 4;
  }
}
