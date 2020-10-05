package blog.mazleo.ruvacant.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Room;
import blog.mazleo.ruvacant.processor.DataProcessor;

public class LocationsViewModel {
    private List<Building> buildings;
    private List<Room> rooms;
    private HashSet<String> existingBuildings;
    private HashSet<String> existingRooms;

    public LocationsViewModel(DataProcessor dataProcessor) {
        this.buildings = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.existingBuildings = new HashSet<>();
        this.existingRooms = new HashSet<>();
    }

    public void addBuilding(Building building) {
        this.buildings.add(building);
        this.existingBuildings.add(building.getCode());
    }

    public boolean containsBuilding(Building building) {
        return this.existingBuildings.contains(building.getCode());
    }
}
