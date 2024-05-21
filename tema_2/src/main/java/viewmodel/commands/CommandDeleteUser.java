package viewmodel.commands;

import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import viewmodel.EmployeeVM;

public class CommandDeleteUser implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandDeleteUser(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        UserRepository userRepository = new UserRepository(dbName);
        EmployeeRepository employeeRepository = new EmployeeRepository(dbName);

        String id = userVM.getUserID();
        boolean flag1 = userRepository.deleteUser(id);
        boolean flag2 = employeeRepository.deleteEmployee(userVM.getPerson());

        return flag1 && flag2;
    }
}
