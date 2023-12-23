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
        Locations locations = new Locations();


        if (json.isJsonObject()) {
            populateLocationsFromRutgersPlaces(json, locations);
        }
        else if (json.isJsonArray()) {
            populateLocationsFromRutgersCourses(json, locations);
        }

        return locations;
    }

    private void populateLocationsFromRutgersCourses(JsonElement json, Locations locations) {
        JsonArray responseJsonArray = json.getAsJsonArray();

        Iterator<JsonElement> courseIterator = responseJsonArray.iterator();
        while (courseIterator.hasNext()) {
            JsonObject courseObject = courseIterator.next().getAsJsonObject();
            addNewLocationsFromCoursesSections(locations, courseObject);
        }
    }

    private void addNewLocationsFromCoursesSections(Locations locations, JsonObject courseObject) {
        JsonArray sectionsArray = courseObject.getAsJsonArray("sections");

        Iterator<JsonElement> sectionIterator = sectionsArray.iterator();
        while (sectionIterator.hasNext()) {
            JsonObject sectionObject = sectionIterator.next().getAsJsonObject();
            addNewLocationsFromSectionsMeetings(locations, sectionObject);
        }
    }

    private void addNewLocationsFromSectionsMeetings(Locations locations, JsonObject sectionObject) {
        JsonArray meetingsArray = sectionObject.getAsJsonArray("meetingTimes");

        Iterator<JsonElement> meetingIterator = meetingsArray.iterator();
        while (meetingIterator.hasNext()) {
            JsonObject meetingObject = meetingIterator.next().getAsJsonObject();

            String roomNumber = null;
            String campusAbbrev = null;
            String buildingCode = null;
            String campusCode = null;

            addNewBuildingAndRoomIfBuildingCodeExists(locations, meetingObject, roomNumber, campusAbbrev, campusCode);
        }
    }

    private void addNewBuildingAndRoomIfBuildingCodeExists(Locations locations, JsonObject meetingObject, String roomNumber, String campusAbbrev, String campusCode) {
        String buildingCode;
        if (meetingObject.has("buildingCode") && !(meetingObject.get("buildingCode") instanceof JsonNull)) {
            buildingCode = meetingObject.getAsJsonPrimitive("buildingCode").getAsString();

            roomNumber = getRoomNumberIfNotNull(meetingObject, roomNumber);
            campusAbbrev = getCampusAbbrevIfNotNull(meetingObject, campusAbbrev);
            campusCode = getCampusCodeByCampusAbbrev(campusAbbrev, campusCode);

            if (buildingCode != null && roomNumber != null) {
                Building newBuilding = new Building(buildingCode, null, campusCode, false);
                addNewBuildingIfNotExists(locations, newBuilding);

                Room newRoom = new Room(buildingCode, roomNumber);
                addNewRoomIfNotExists(locations, newRoom);
            }
        }
    }

    private void addNewRoomIfNotExists(Locations locations, Room newRoom) {
        if (!locations.getRooms().contains(newRoom)) {
            locations.getRooms().add(newRoom);
        }
    }

    private String getCampusAbbrevIfNotNull(JsonObject meetingObject, String campusAbbrev) {
        if (meetingObject.has("campusAbbrev") && !(meetingObject.get("campusAbbrev") instanceof JsonNull)) {
            campusAbbrev = meetingObject.getAsJsonPrimitive("campusAbbrev").getAsString();
        }
        return campusAbbrev;
    }

    private String getRoomNumberIfNotNull(JsonObject meetingObject, String roomNumber) {
        if (meetingObject.has("roomNumber") && !(meetingObject.get("roomNumber") instanceof JsonNull)) {
            roomNumber = meetingObject.getAsJsonPrimitive("roomNumber").getAsString();
        }
        return roomNumber;
    }

    private String getCampusCodeByCampusAbbrev(String campusAbbrev, String campusCode) {
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
        return campusCode;
    }

    private void populateLocationsFromRutgersPlaces(JsonElement json, Locations locations) {
        JsonObject responseJsonObject = json.getAsJsonObject();

        JsonObject all = responseJsonObject.getAsJsonObject("all");

        Set keys = all.keySet();
        Iterator<String> keysIterator = keys.iterator();
        while (keysIterator.hasNext()) {
            String keyString = keysIterator.next();
            JsonObject buildingObject = all.getAsJsonObject(keyString);

            addNewBuildingIfBuildingCodeExistsInRutgersPlaces(locations, buildingObject);
        }
    }

    private void addNewBuildingIfBuildingCodeExistsInRutgersPlaces(Locations locations, JsonObject buildingObject) {
        if (buildingObject.has("building_code")) {
            String code = null;
            String name = null;
            String campusName = null;
            String campusCode = null;

            code = getBuildingCodeIfNotNull(buildingObject, code);
            name = getBuildingNameIfNotNull(buildingObject, name);
            campusCode = getCampusCodeIfNotNull(buildingObject, campusCode);

            Building newBuilding = new Building(code, name, campusCode, false);
            addNewBuildingIfNotExists(locations, newBuilding);
        }
    }

    private void addNewBuildingIfNotExists(Locations locations, Building newBuilding) {
        if (!locations.getBuildings().contains(newBuilding)) {
            locations.getBuildings().add(newBuilding);
        }
    }

    private String getCampusCodeIfNotNull(JsonObject buildingObject, String campusCode) {
        String campusName;
        if (buildingObject.has("campus_name") && !(buildingObject.get("campus_name") instanceof JsonNull)) {
            campusName = buildingObject.getAsJsonPrimitive("campus_name").getAsString();

            campusCode = initializeCampusCodeByCampusName(campusName);
        }
        return campusCode;
    }

    private String getBuildingNameIfNotNull(JsonObject buildingObject, String name) {
        if (buildingObject.has("title") && !(buildingObject.get("title") instanceof JsonNull)) {
            name = buildingObject.getAsJsonPrimitive("title").getAsString();
        }
        return name;
    }

    private String getBuildingCodeIfNotNull(JsonObject buildingObject, String code) {
        if (buildingObject.has("building_code") && !(buildingObject.get("building_code") instanceof JsonNull)) {
            code = buildingObject.getAsJsonPrimitive("building_code").getAsString();
        }
        return code;
    }

    private String initializeCampusCodeByCampusName(String campusName) {
        String campusCode;
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
        return campusCode;
    }
}
