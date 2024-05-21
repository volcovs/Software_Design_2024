package viewmodel.commands;

import model.User;
import model.repository.UserRepository;

import view.PlantPage;
import viewmodel.EmployeeVM;

public class CommandLogIn implements ICommands {
    private String dbName = "gradina_botanica";
    private EmployeeVM employeeVM;

    public CommandLogIn(EmployeeVM employeeVM) {
        this.employeeVM = employeeVM;
    }

    @Override
    public boolean execute() {
        String username = employeeVM.getUsername();
        String password = employeeVM.getPassword();

        UserRepository userRepository = new UserRepository(dbName);
        User u = userRepository.searchUserLogIn(username, password);

        if (u != null) {
            if (u.getAdminStatus().equals("yes")) {
                //successful login - admin role
                PlantPage plantPage = new PlantPage("administrator");
                return true;
            }
            else {
                //successful login - just an employee
                PlantPage plantPage = new PlantPage("employee");
                return true;
            }
        }
        else {
            System.out.println("Error");
            return false;
        }
    }
}

