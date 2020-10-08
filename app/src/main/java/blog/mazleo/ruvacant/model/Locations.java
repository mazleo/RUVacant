package blog.mazleo.ruvacant.model;

import java.util.ArrayList;
import java.util.List;

public class Locations {
    private List<Building> buildings;
    private List<Room> rooms;

    public Locations() {
        this.buildings = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
