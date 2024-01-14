package blog.mazleo.ruvacant.shared;

import androidx.annotation.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

/** Cached data shared throughout the entire application. */
@Singleton
public final class SharedApplicationData {

  private Map<String, Object> dataMap = new HashMap<>();

  @VisibleForTesting
  @Inject
  public SharedApplicationData() {}

  public void addData(String tag, Object data) {
    if (!dataMap.containsKey(tag)) {
      dataMap.put(tag, data);
    } else {
      throw new IllegalArgumentException(String.format("Data tag %s already exists.", tag));
    }
  }

  public boolean containsData(String tag) {
    return dataMap.containsKey(tag);
  }

  public Object getData(String tag) {

    if (dataMap.containsKey(tag)) {
      return dataMap.get(tag);
    }
    throw new IllegalArgumentException(
        String.format("Attempting to get data tag %s that does not exist", tag));
  }

  public void replaceData(String tag, Object data) {
    if (containsData(tag)) {
      removeData(tag);
    }
    addData(tag, data);
  }

  public void removeData(String tag) {
    if (dataMap.containsKey(tag)) {
      dataMap.remove(tag);
    }
  }
}
