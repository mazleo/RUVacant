package blog.mazleo.ruvacant.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Locations;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Room;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.processor.DataProcessor;
import blog.mazleo.ruvacant.repository.LocationsRepository;
import blog.mazleo.ruvacant.utils.OptionsUtil;

public class LocationsViewModel {
    private DataProcessor dataProcessor;
    private List<Building> buildings;
    private List<Room> rooms;
    private HashSet<String> existingBuildings;
    private HashSet<String> existingRooms;
    private LocationsRepository locationsRepository;

    public LocationsViewModel(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
        this.buildings = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.existingBuildings = new HashSet<>();
        this.existingRooms = new HashSet<>();
    }

    public void downloadLocations() {
        locationsRepository = new LocationsRepository(this);
        downloadLocationsFromRutgersPlaces();
    }

    private void downloadLocationsFromRutgersPlaces() {
        locationsRepository.initiateLocationsFromRutgersPlacesDownload();
    }

    public void onLocationsFromRutgersPlacesRetrieved(Locations locations) {
        appendLocations(locations);
        locationsRepository = null;
        startDownloadLocationsFromRutgersCourses();
    }

    private void startDownloadLocationsFromRutgersCourses() {
        locationsRepository = new LocationsRepository(this);
        downloadSubjects(dataProcessor.getSelectedOptions());
    }

    private void downloadSubjects(Option option) {
        locationsRepository.initiateSubjectsServiceDownload(option);
    }

    public void onSubjectsRetrieved(List<Subject> subjects) {
        locationsRepository = null;
        Option downloadOption = OptionsUtil.getNearestFullSemesterOption(dataProcessor.getSelectedOptions());
        downloadLocationsFromRutgersCourses(subjects, downloadOption);
    }

    private void downloadLocationsFromRutgersCourses(List<Subject> subjects, Option option) {
        locationsRepository = new LocationsRepository(this);
        locationsRepository.initiateLocationsFromRutgersCoursesDownload(subjects, option);
    }

    public void onLocationsFromRutgersCoursesRetrieved(Locations locations) {
        appendLocations(locations);
        locationsRepository = null;
        onLocationsRetrievalComplete();
    }

    private void onLocationsRetrievalComplete() {
        // TODO
    }

    public void onError(Throwable e) {
        // TODO
    }

    public void appendLocations(Locations locations) {
        appendBuildings(locations.getBuildings());
        appendRooms(locations.getRooms());
    }

    public void appendBuildings(List<Building> buildings) {
        for (Building building : buildings) {
            if (!containsBuilding(building)) {
                addBuilding(building);
            }
        }
    }

    public void appendRooms(List<Room> rooms) {
        for (Room room : rooms) {
            if (!containsRoom(room)) {
                addRoom(room);
            }
        }
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        this.existingBuildings.add(building.getCode());
    }

    public boolean containsBuilding(Building building) {
        return this.existingBuildings.contains(building.getCode());
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        this.existingRooms.add(room.getBuildingCode() + "-" + room.getNumber());
    }

    public boolean containsRoom(Room room) {
        return this.existingRooms.contains(room.getBuildingCode() + "-" + room.getNumber());
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
