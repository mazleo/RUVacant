package blog.mazleo.ruvacant.viewmodel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import blog.mazleo.ruvacant.model.Building;
import blog.mazleo.ruvacant.model.Room;

@RunWith(AndroidJUnit4.class)
public class LocationsViewModelTest {
    @Test
    public void addAndContainsBuildingTest() {
        LocationsViewModel locationsViewModel = new LocationsViewModel(null);
        Building building1 = new Building("HLL", "Hill Center", "BUS", false);
        Building building2 = new Building("AB", "Academic Building", "CAC", false);

        locationsViewModel.addBuilding(building1);

        boolean building1Exists = locationsViewModel.containsBuilding(building1);
        boolean building2Exists = locationsViewModel.containsBuilding(building2);

        Assert.assertTrue(building1Exists);
        Assert.assertFalse(building2Exists);
    }
    @Test
    public void addAndContainsRoomTest() {
        LocationsViewModel locationsViewModel = new LocationsViewModel(null);

        Room room1 = new Room("HLL", "101");
        Room room2 = new Room("HLL", "102");
        Room room3 = new Room("AB", "1000");
        Room room4 = new Room("AB", "2000");

        locationsViewModel.addRoom(room1);
        locationsViewModel.addRoom(room3);

        Assert.assertTrue(locationsViewModel.containsRoom(room1));
        Assert.assertFalse(locationsViewModel.containsRoom(room2));
        Assert.assertTrue(locationsViewModel.containsRoom(room3));
        Assert.assertFalse(locationsViewModel.containsRoom(room4));
    }
}
