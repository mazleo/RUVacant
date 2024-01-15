package blog.mazleo.ruvacant.ui.content;

import androidx.recyclerview.widget.RecyclerView;
import blog.mazleo.ruvacant.ui.base.SceneDataManager;

/** A scroller for the content recycler view. */
public interface RecyclerViewScroller {
  void setRecyclerView(RecyclerView recyclerView);

  void setDataManager(SceneDataManager sceneDataManager);

  void buildScroller();
}
