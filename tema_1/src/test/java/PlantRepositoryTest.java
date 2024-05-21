import model.Plant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.repository.PlantRepository;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlantRepositoryTest {
    private static int numberOfTests = 0;

    private static int numberOfTestsPassed = 0;

    private static PlantRepository repo;

    // Call before class to be initialized
    @BeforeClass
    public static void init() {
        System.out.println("Initialize tests");
        repo = new PlantRepository("gradina_botanica_test");
    }

    // Call after the class is destroyed
    @AfterClass
    public static void finalMethod() {
        repo = null;
        System.out.println(numberOfTests + " tests have been executed and only " + numberOfTestsPassed + " have passed");
    }

    // Call before each test
    @Before
    public void increment() {
        numberOfTests++;
    }

    // Call after each test
    @After
    public void afterEachTest() {
        System.out.println("Test Finished");
    }

    @Test
    public void addPlantTest() {
        Plant p = new Plant("", "castan rosu", "Aesculus carnea Hayne", "Angiospermae", "no", "no", "main entrance");

        boolean flag1 = repo.addPlant(p);
        assertTrue(flag1);
        numberOfTestsPassed++;
    }

    @Test
    public void updatePlantTest() {
        Plant p1 = new Plant("", "castan rosu", "Aesculus carnea Hayne", "Angiospermae", "no", "no", "main entrance");
        repo.addPlant(p1);
        Plant p = new Plant("", "castan rosu", "Aesculus carnea Hayne", "angiospermae", "no", "no", "center");

        boolean flag = repo.updatePlant("1", p);
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void deletePlantTest() {
        Plant p1 = new Plant("", "castan rosu", "Aesculus carnea Hayne", "Angiospermae", "no", "no", "main entrance");
        repo.addPlant(p1);
        boolean flag = repo.deletePlant("1");
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void searchPlantTest() {
        Plant p = new Plant("", "castan rosu", "Aesculus carnea Hayne", "angiospermae", "no", "no", "center");

        repo.addPlant(p);
        Plant found = repo.searchPlant("3");
        assertEquals(p, found);
        numberOfTestsPassed++;
    }

    @Test
    public void allPlantsTest() {
        Plant p = new Plant("", "castan rosu", "Aesculus carnea Hayne", "angiospermae", "no", "no", "center");
        Plant p2 = new Plant("", "artar de argint", "Acer saccharinum", "angiospermae", "no", "no", "center");
        repo.addPlant(p);
        repo.addPlant(p2);

        List<Plant> expected = new ArrayList<>();
        expected.add(p);
        expected.add(p2);

        List<Plant> rs = repo.plantList();
        assertNotNull(rs);
        assertEquals(4, rs.size());
        assertEquals(p.getPlantName(), rs.get(0).getPlantName());
        numberOfTestsPassed++;
    }
}
