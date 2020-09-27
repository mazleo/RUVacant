package blog.mazleo.ruvacant.service.deserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Locations;
import blog.mazleo.ruvacant.model.Room;

public class LocationsDeserializer implements JsonDeserializer<Locations> {
    @Override
    public Locations deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Locations locations = new Locations((Class) typeOfT);


        if (json.isJsonObject()) {
            JsonObject responseJsonObject = json.getAsJsonObject();

            JsonObject all = responseJsonObject.getAsJsonObject("all");

            Set keys = all.keySet();
            Iterator<String> keysIterator = keys.iterator();
            while (keysIterator.hasNext()) {
                String keyString = keysIterator.next();
                JsonObject buildingObject = all.getAsJsonObject(keyString);

                if (buildingObject.has("building_code")) {
                    String code = null;
                    String name = null;
                    String campusName = null;
                    String campusCode = null;

                    if (buildingObject.has("building_code") && !(buildingObject.get("building_code") instanceof JsonNull)) {
                        code = buildingObject.getAsJsonPrimitive("building_code").getAsString();
                    }
                    if (buildingObject.has("title") && !(buildingObject.get("title") instanceof JsonNull)) {
                        name = buildingObject.getAsJsonPrimitive("title").getAsString();
                    }
                    if (buildingObject.has("campus_name") && !(buildingObject.get("campus_name") instanceof JsonNull)) {
                        campusName = buildingObject.getAsJsonPrimitive("campus_name").getAsString();

                        switch (campusName) {
                            case "Busch":
                                campusCode = "BUS";
                                break;
                            case "College Avenue":
                                campusCode = "CAC";
                                break;
                            case "Newark":
                                campusCode = "N";
                                break;
                            case "Livingston":
                                campusCode = "LIV";
                                break;
                            case "Cook":
                                campusCode = "D/C";
                                break;
                            case "Douglass":
                                campusCode = "D/C";
                                break;
                            case "Camden":
                                campusCode = "CM";
                                break;
                            case "Health Sciences at Newark":
                                campusCode = "N";
                                break;
                            default:
                                campusCode = "CAC";
                                break;
                        }
                    }

                    Building newBuilding = new Building(code, name, campusCode, false);
                    locations.getBuildings().add(newBuilding);
                }
            }
        }
        else if (json.isJsonArray()) {
            JsonArray responseJsonArray = json.getAsJsonArray();

            Iterator<JsonElement> courseIterator = responseJsonArray.iterator();
            while (courseIterator.hasNext()) {
                JsonObject courseObject = courseIterator.next().getAsJsonObject();
                JsonArray sectionsArray = courseObject.getAsJsonArray("sections");

                Iterator<JsonElement> sectionIterator = sectionsArray.iterator();
                while (sectionIterator.hasNext()) {
                    JsonObject sectionObject = sectionIterator.next().getAsJsonObject();
                    JsonArray meetingsArray = sectionObject.getAsJsonArray("meetingTimes");

                    Iterator<JsonElement> meetingIterator = meetingsArray.iterator();
                    while (meetingIterator.hasNext()) {
                        JsonObject meetingObject = meetingIterator.next().getAsJsonObject();

                        String roomNumber = null;
                        String campusAbbrev = null;
                        String buildingCode = null;
                        String campusCode = null;

                        if (meetingObject.has("buildingCode") && !meetingObject.getAsJsonPrimitive("buildingCode").isJsonNull()) {
                            buildingCode = meetingObject.getAsJsonPrimitive("buildingCode").getAsString();

                            if (meetingObject.has("roomNumber") && !meetingObject.getAsJsonPrimitive("roomNumber").isJsonNull()) {
                                roomNumber = meetingObject.getAsJsonPrimitive("roomNumber").getAsString();
                            }
                            if (meetingObject.has("campusAbbrev") && !meetingObject.getAsJsonPrimitive("campusAbbrev").isJsonNull()) {
                                campusAbbrev = meetingObject.getAsJsonPrimitive("campusAbbrev").getAsString();
                            }
                            switch (campusAbbrev) {
                                case "LIV":
                                    campusCode = "LIV";
                                    break;
                                case "BUS":
                                    campusCode = "BUS";
                                    break;
                                case "CAC":
                                    campusCode = "CAC";
                                    break;
                                case "D/C":
                                    campusCode = "D/C";
                                    break;
                                case "**":
                                    campusCode = "N";
                                    break;
                                case "CAM":
                                    campusCode = "CM";
                                    break;
                            }

                            if (buildingCode != null && roomNumber != null) {
                                Building newBuilding = new Building(buildingCode, null, campusCode, false);
                                locations.getBuildings().add(newBuilding);

                                Room newRoom = new Room(buildingCode, roomNumber);
                                locations.getRooms().add(newRoom);
                            }
                        }
                    }
                }
            }
        }

        return locations;
    }
}
