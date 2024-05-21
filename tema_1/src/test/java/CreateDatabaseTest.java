import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.repository.CreateDatabase;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class CreateDatabaseTest {
    private static int numberOfTests = 0;

    private static int numberOfTestsPassed = 0;

    private static CreateDatabase testedUnit;

    // Call before class to be initialized
    @BeforeClass
    public static void init() {
        System.out.println("Initialize tests");

        assertFalse(Files.exists(Path.of("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\gradina_botanica_test")));
        testedUnit = new CreateDatabase("gradina_botanica_test");
        assertTrue(Files.exists(Path.of("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\gradina_botanica_test")));
    }

    // Call after the class is destroyed
    @AfterClass
    public static void finalMethod() {
        testedUnit = null;
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
    public void plantTableTest() {
        File f = new File ("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\gradina_botanica_test\\plant.ibd");
        assertFalse(f.exists());

        testedUnit.createTablePlant();
        assertTrue(f.exists());
        numberOfTestsPassed++;
    }

    @Test
    public void employeeTableTest() {
        File f = new File ("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\gradina_botanica_test\\employee.ibd");
        assertFalse(f.exists());

        testedUnit.createTableEmployee();
        assertTrue(f.exists());
        numberOfTestsPassed++;
    }

    @Test
    public void userTableTest() {
        File f = new File ("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\gradina_botanica_test\\users.ibd");
        assertFalse(f.exists());

        testedUnit.createTableUser();
        assertTrue(f.exists());
        numberOfTestsPassed++;
    }

}
