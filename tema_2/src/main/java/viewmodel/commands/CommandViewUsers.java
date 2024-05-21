package viewmodel.commands;

import model.Employee;
import model.User;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import viewmodel.EmployeeVM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandViewUsers implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandViewUsers(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        UserRepository userRepository = new UserRepository(dbName);
        EmployeeRepository employeeRepository = new EmployeeRepository(dbName);

        List<User> users = userRepository.userList();
        List<Employee> employees = employeeRepository.employeeList();

        if (users != null && users.size() != 0 && employees != null && employees.size() != 0) {
            List<Object> people = new ArrayList<>();
            people.addAll(users);
            people.addAll(employees);

            listToTable(people);
            return true;
        }
        else {
            List<Object> people = new ArrayList<>();
            people.add(new User("", "", "", ""));
            people.add(new Employee("", "", "", null, "", "", ""));
            listToTable(people);
            return false;
        }
    }

    private void listToTable(List<Object> list) {
        List<User> users = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        for (int k = 0; k < list.size(); k++) {
            if (k < (list.size()/2)) {
                users.add((User) list.get(k));
            }
            else {
                employees.add((Employee) list.get(k));
            }
        }

        List<String> columnNames = new ArrayList<String>();
        int iter = 0;

        for (Field field : users.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        for (Field field : employees.get(0).getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        Object[][] tableData = new Object[users.size()*2][iter*2];
        int i = 0;
        int numOfFields = 0;
        for (User obj : users) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                numOfFields++;
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        i = 0;
        for (Employee obj : employees) {
            int j = 5;
            for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        DefaultTableModel table = new DefaultTableModel(tableData, columnNames.toArray());
        this.userVM.setTableContent(table);
    }
}
