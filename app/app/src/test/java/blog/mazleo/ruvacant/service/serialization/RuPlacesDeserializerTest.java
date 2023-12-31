package blog.mazleo.ruvacant.service.serialization;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import blog.mazleo.ruvacant.service.model.RuPlace;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import java.util.List;
import org.junit.Test;

/** Test cases for RuPlacesDeserializer. */
public final class RuPlacesDeserializerTest {
  @Test
  public void deserialize_expectedStructure() {
    String jsonString =
        "{\"all\":{\"3038_4622Scott Hall\":{\"title\":\"Scott Hall\",\"description\":\"Scott Hall"
            + " was erected in 1963 and was named after Austin Scott (1848-1922).  Scott was a"
            + " professor of history at Rutgers College.  From 1891-1906 Austin Scott was the"
            + " President of Rutgers College.      .Source: Catalouge of Buildings and Place Names"
            + " at Rutgers.\",\"cid\":\"C70754\",\"building_id\":\"4622\",\"building_number\":\"3038\",\"building_code\":\"SC\",\"campus_code\":\"10\",\"campus_name\":\"College"
            + " Avenue\",\"location\":{\"name\":\"Scott Hall\",\"street\":\"43 College"
            + " Avenue\",\"additional\":\"\",\"city\":\"New Brunswick\",\"state\":\"New"
            + " Jersey\",\"state_abbr\":\"NJ\",\"postal_code\":\"08901-1164\",\"country\":\"United"
            + " States\",\"country_abbr\":\"US\",\"latitude\":\"40.499810\",\"longitude\":\"-74.447610\"},\"offices\":[\"SAS"
            + " - Dean's Office\",\"SAS- Asian Language & Cultures\"],\"id\":\"3038_4622Scott"
            + " Hall\"}}}";
    Gson gson = new GsonBuilder().create();
    JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
    RuPlacesDeserializer deserializer = new RuPlacesDeserializer();
    List<RuPlace> places =
        deserializer.deserialize(jsonElement, /* typeOfT= */ null, /* context= */ null);
    assertNotNull(places);
    assertNotNull(places.get(0));
    assertNotNull(places.get(0).code);
    assertNotNull(places.get(0).name);
    assertEquals("SC", places.get(0).code);
    assertEquals("scott hall", places.get(0).name);
  }

  @Test
  public void deserialize_null() {
    JsonNull jsonNull = JsonNull.INSTANCE;
    RuPlacesDeserializer deserializer = new RuPlacesDeserializer();
    List<RuPlace> places =
        deserializer.deserialize(jsonNull, /* typeOfT= */ null, /* context= */ null);
    assertNull(places);
  }
}
