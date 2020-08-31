package blog.mazleo.ruvacant.model;

import java.util.List;

public class Locations<T> {
    private List<Building> buildings;
    private List<Room> rooms;
    private Class<T> clazz;

    public Locations(List<Building> buildings, List<Room> rooms, Class<T> clazz) {
        this.buildings = buildings;
        this.rooms = rooms;
        this.clazz = clazz;
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
