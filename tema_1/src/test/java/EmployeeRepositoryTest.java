import model.Employee;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import model.repository.EmployeeRepository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeRepositoryTest {
    private static int numberOfTests = 0;

    private static int numberOfTestsPassed = 0;

    private static EmployeeRepository repo;

    // Call before class to be initialized
    @BeforeClass
    public static void init() {
        System.out.println("Initialize tests");
        repo = new EmployeeRepository("gradina_botanica_test");
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
    public void addEmployeeTest() {
        Employee e = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");

        boolean flag1 = repo.addEmployee(e);
        assertTrue(flag1);
        numberOfTestsPassed++;
    }

    @Test
    public void updateEmployeeTest() {
        Employee e = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");
        repo.addEmployee(e);
        Employee e1 = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");

        boolean flag = repo.updateEmployee("1", e1);
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void deleteEmployeeTest() {
        Employee e = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");
        repo.addEmployee(e);
        boolean flag = repo.deleteEmployee("1");
        assertTrue(flag);
        numberOfTestsPassed++;
    }

    @Test
    public void searchEmployeeTest() {
        Employee e = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");
        repo.addEmployee(e);

        Employee found = repo.searchEmployee("3");
        assertEquals(e, found);
        numberOfTestsPassed++;
    }

    @Test
    public void allEmployeesTest() {
        Employee e = new Employee("1", "Vlad", "Popescu", LocalDate.parse("1998-05-23"), "str. Ciresilor 13", "0733333333", "popescu@yahoo.com");
        Employee e2 = new Employee("2", "Valentina", "Maia", LocalDate.parse("1989-12-15"), "str. Independentei 5", "0644444444", "val.maia@gmail.com");
        repo.addEmployee(e);
        repo.addEmployee(e2);

        List<Employee> expected = new ArrayList<>();
        expected.add(e);
        expected.add(e2);

        List<Employee> rs = repo.employeeList();
        assertNotNull(rs);
        assertEquals(4, rs.size());
        assertEquals(e.getLastName(), rs.get(0).getLastName());
        numberOfTestsPassed++;
    }
}
