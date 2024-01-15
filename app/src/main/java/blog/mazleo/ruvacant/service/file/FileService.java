package blog.mazleo.ruvacant.service.file;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;
import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.core.ApplicationAnnotations.AppName;
import blog.mazleo.ruvacant.service.model.RuPlace;
import blog.mazleo.ruvacant.service.serialization.RuPlacesDeserializer;
import blog.mazleo.ruvacant.service.state.ApplicationState;
import blog.mazleo.ruvacant.service.state.ApplicationStateManager;
import blog.mazleo.ruvacant.shared.ApplicationData;
import blog.mazleo.ruvacant.shared.SharedApplicationData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.inject.Inject;

/** The file reading service. */
public final class FileService {

  private static final int MAX_NUM_FILE_READ_TRIES = 5;

  private final String appName;
  private final AssetManager assetManager;
  private final Resources resources;
  private final ApplicationStateManager stateManager;
  private final SharedApplicationData sharedApplicationData;

  private int numFileReadTries = 0;

  @Inject
  FileService(
      @AppName String appName,
      AssetManager assetManager,
      @ApplicationContext Resources resources,
      ApplicationStateManager stateManager,
      SharedApplicationData sharedApplicationData) {
    this.appName = appName;
    this.assetManager = assetManager;
    this.resources = resources;
    this.stateManager = stateManager;
    this.sharedApplicationData = sharedApplicationData;
  }

  public void initiateParsePlacesFile() {
    String filename = resources.getString(R.string.places_file_name);
    String jsonString = attemptReadFile(filename);
    sharedApplicationData.replaceData(
        ApplicationData.PLACES_CACHE.getTag(), parseJsonString(jsonString));
    stateManager.exitState(ApplicationState.PLACES_READING.getState());
    stateManager.enterState(ApplicationState.PLACES_READ.getState());
  }

  public void reset() {
    numFileReadTries = 0;
  }

  private Map<String, RuPlace> parseJsonString(String jsonString) {
    Gson gson = new GsonBuilder().create();
    JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
    Map<String, RuPlace> places =
        new RuPlacesDeserializer()
            .deserialize(jsonElement, /* typeOfT= */ null, /* context= */ null);
    return places;
  }

  private String attemptReadFile(String filename) {
    numFileReadTries++;
    String errorMessage = "An error occurred while attempting to read file.";
    String jsonString = "";
    try {
      InputStream inputStream = assetManager.open(filename);
      int fileLength = inputStream.available();
      int numBytesRead = 0;
      while (numBytesRead != -1) {
        byte[] bytes = new byte[fileLength];
        numBytesRead = inputStream.read(bytes);
        String partialJsonString = new String(bytes);
        jsonString += partialJsonString;
      }
    } catch (IOException exception) {
      if (numFileReadTries <= MAX_NUM_FILE_READ_TRIES) {
        Log.d(appName, errorMessage + " Trying again...");
        jsonString = attemptReadFile(filename);
      } else {
        Log.d(appName, errorMessage);
        // TODO: Handle error.
      }
    }
    return jsonString.trim();
  }
}
