package viewmodel.commands;

import view.LogInPage;
import viewmodel.EmployeeVM;

public class CommandLogInFromUser implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandLogInFromUser(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        String role = userVM.getRole();

        LogInPage logInPage = new LogInPage(role);
        return true;
    }
}
