import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.repository.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RepositoryTest {
    private static int numberOfTests = 0;

    private static int numberOfTestsPassed = 0;

    private static Repository repo;

    // Call before class to be initialized
    @BeforeClass
    public static void init() {
        System.out.println("Initialize tests");
        repo = new Repository("gradina_botanica");
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
    public void openConnectionTest() {
        Connection connection = Repository.getConnection();
        try {
            assertFalse(connection.isClosed());
            numberOfTestsPassed++;
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void closeConnectionTest() {
        Connection connection = Repository.getConnection();
        try {
            assertFalse(connection.isClosed());
            Repository.close(connection);
            assertTrue(connection.isClosed());
            numberOfTestsPassed++;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void commandSQLTest() {
        String sqlCommand1 = "INSERT INTO Plant(plant_name, species, plant_type, carnivorous, endangered, location) VALUES ('artar de argint', 'Acer saccharinum', 'angiospermae', 'no', 'no', 'center');";
        String sqlCommand2 = "DELETE FROM Plant WHERE plant_id = 1;";
        String sqlCommand3 = "SELECT * FROM Nonexistent WHERE entry_id = 5;";
        String sqlCommand4 = "INSERT INTO Nonexistent VALUES('this', 'should', 'return', 'an', 'error');";

        boolean flag = repo.executeCommand(sqlCommand1);
        boolean flag2 = repo.executeCommand(sqlCommand2);
        boolean flag3 = repo.executeCommand(sqlCommand3);
        boolean flag4 = repo.executeCommand(sqlCommand4);

        assertTrue(flag);
        assertTrue(flag2);
        assertFalse(flag3);
        assertFalse(flag4);
        numberOfTestsPassed++;
    }

    @Test
    public void getTableTest() {
        String sqlCommand1 = "SELECT * FROM Plant;";
        ResultSet rs = repo.getTable(sqlCommand1);
        assertNotNull(rs);

        int num = 0;
        try {
            while(rs.next()) {
                num++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(1, num);
        numberOfTestsPassed++;
    }


}
