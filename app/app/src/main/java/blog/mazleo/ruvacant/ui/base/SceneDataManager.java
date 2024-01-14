package blog.mazleo.ruvacant.ui.base;

import java.util.List;

/** Data managers for scenes. */
public interface SceneDataManager {

  List<CardValue> getCards();

  void buildCards();

  void sortCards();
}
