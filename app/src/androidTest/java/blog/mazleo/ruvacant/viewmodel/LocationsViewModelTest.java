package blog.mazleo.ruvacant.viewmodel;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import blog.mazleo.ruvacant.model.Building;

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
}
