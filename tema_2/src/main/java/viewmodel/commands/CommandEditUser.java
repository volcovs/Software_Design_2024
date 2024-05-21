package viewmodel.commands;

import model.Employee;
import model.User;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import viewmodel.EmployeeVM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandEditUser implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandEditUser(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        UserRepository userRepository = new UserRepository(dbName);
        EmployeeRepository employeeRepository = new EmployeeRepository(dbName);

        String id = userVM.getUserID();
        List<Object> newUser = this.validInformation();
        if (newUser != null) {
            boolean flag1 = userRepository.updateUser(id, (User) newUser.get(0));
            boolean flag2 = employeeRepository.updateEmployee(id, (Employee) newUser.get(1));
            return flag1 && flag2;
        }

        return false;
    }

    private List<Object> validInformation() {
        List<Object> user = new ArrayList<>();

        String userId = userVM.getUserID();
        if (userId == null || userId.length() == 0) {
            return null;
        }

        String person = userVM.getPerson();
        if (person == null || person.length() == 0) {
            return null;
        }

        String first = userVM.getFirstName();
        if (first == null || first.length() == 0) {
            return null;
        }

        String last = userVM.getLastName();
        if(last == null || last.length() == 0) {
            return null;
        }

        String date = userVM.getDOB();
        if (date == null || date.length() == 0 || LocalDate.parse(date) == null) {
            return null;
        }

        String address = userVM.getAddress();
        if (address == null || address.length() == 0) {
            return null;
        }

        String phone = userVM.getPhoneNumber();
        if (phone == null || phone.length() == 0) {
            phone = "";
        }

        String email = userVM.getEmail();
        if (email == null || email.length() == 0) {
            email = "";
        }

        String username = userVM.getUsername();
        if (username == null || username.length() == 0) {
            return null;
        }

        String pass = userVM.getPassword();
        if (pass == null || pass.length() == 0) {
            return null;
        }

        String status = userVM.getStatus();

        user.add(new User(userId, person, username, pass, status));
        user.add(new Employee(person, first, last, LocalDate.parse(date), address, phone, email));

        return user;
    }
}
