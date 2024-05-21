package model.repository;

import model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class EmployeeRepository {

    private Repository repository;
    private String dbName = "gradina_botanica";


    public EmployeeRepository(String dbName) {
        this.dbName = dbName;
        this.repository = new Repository(dbName);
    }

    public boolean addEmployee(Employee employee) {
        String command = "INSERT INTO Employee(first_name, last_name, date_of_birth, address, phone_number, email) Values('";
        command += employee.getFirstName() + "', '" + employee.getLastName() + "', '";
        command += employee.getDateOfBirth() + "', '" + employee.getAddress() + "', '";
        command += employee.getPhoneNumber() + "', '" + employee.getEmail() + "');";

        boolean flag = this.repository.executeCommand(command);
        return flag;
    }

    public boolean updateEmployee(String id, Employee employee) {
        String command = "UPDATE Employee SET";
        command += " first_name = '" + employee.getFirstName() + "',";
        command += " last_name = '" + employee.getLastName() + "', ";
        command += " date_of_birth = '" + employee.getDateOfBirth() + "', ";
        command += " address = '" + employee.getAddress() + "', ";
        command += " phone_number = '" + employee.getPhoneNumber() + "', ";
        command += " email = '" + employee.getEmail() + "' ";

        command += "WHERE person_id = " + id + ";";

        boolean flag = this.repository.executeCommand(command);
        return flag;
    }

    public boolean deleteEmployee(String id) {
        String command = "DELETE FROM Employee WHERE person_id = " + id + ";";

        boolean flag = this.repository.executeCommand(command);
        return flag;
    }

    public Employee searchEmployee(String id) {
        String search = "SELECT * FROM Employee WHERE person_id = " + id + ";";
        ResultSet rs = this.repository.getTable(search);
        Employee e = null;

        try {
            rs.next();

            String employeeId = String.valueOf(rs.getInt("person_id"));
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            LocalDate dateOfBirth = LocalDate.parse(rs.getString("date_of_birth"));
            String address = rs.getString("address");
            String phoneNumber = rs.getString("phone_number");
            String email = rs.getString("email");

            e = new Employee(employeeId, firstName, lastName, dateOfBirth, address, phoneNumber, email);
            this.repository.close(rs);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return e;
    }

    public List<Employee> employeeList() {
        List<Employee> employees = new ArrayList<>();

        String getPlants = "SELECT * FROM Employee";
        ResultSet rs = this.repository.getTable(getPlants);

        try {
            while(rs.next()) {
                Employee employee;

                String employeeId = String.valueOf(rs.getInt("person_id"));
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("date_of_birth"));
                String address = rs.getString("address");
                String phoneNumber = rs.getString("phone_number");
                String email = rs.getString("email");

                employee = new Employee(employeeId, firstName, lastName, dateOfBirth, address, phoneNumber, email);
                employees.add(employee);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.repository.close(rs);
        return employees;
    }
}
