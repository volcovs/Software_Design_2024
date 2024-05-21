import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTest {
    private static int numberOfTests = 0;

    private static int numberOfTestsPassed = 0;

    private static UserRepository repo;

    // Call before class to be initialized
    @BeforeClass
    public static void init() {
        System.out.println("Initialize tests");
        repo = new UserRepository("gradina_botanica_test");
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
    public void addUserTest() {
        User u = new User("1", "1", "firstUser", "seCurePassWord");

        boolean flag1 = repo.addUser(u);
        assertTrue(flag1);
        numberOfTestsPassed++;
    }

    @Test
    public void updateUserTest() {
        User u = new User("1", "1", "firstUser", "seCurePassWord");
        repo.addUser(u);
        User u2 = new User("1", "1", "updatedUser", "EvenBetterPassword");

        boolean flag = repo.updateUser("1", u2);
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void deleteUserTest() {
        User u1 = new User("1", "1", "firstUser", "seCurePassWord");
        repo.addUser(u1);
        boolean flag = repo.deleteUser("1");
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void searchUserTest() {
        User u = new User("1", "1", "firstUser", "seCurePassWord");

        repo.addUser(u);
        User found = repo.searchUser("3");
        assertEquals(u, found);
        numberOfTestsPassed++;
    }

    @Test
    public void allUsersTest() {
        User u = new User("1", "1", "firstUser", "seCurePassWord");
        User u2 = new User("2", "5", "secondUser", "thisPasswordCannotBeCracked1234");
        repo.addUser(u);
        repo.addUser(u2);

        List<User> expected = new ArrayList<>();
        expected.add(u);
        expected.add(u2);

        List<User> rs = repo.userList();
        assertNotNull(rs);
        assertEquals(4, rs.size());
        assertEquals(u.getPerson(), rs.get(0).getPerson());
        numberOfTestsPassed++;
    }
}
