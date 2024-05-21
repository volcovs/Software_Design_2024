package viewmodel.commands;

import model.Employee;
import model.User;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import view.UpdateNewUser;
import viewmodel.EmployeeVM;

public class CommandNewUserPage implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandNewUserPage(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        UserRepository userRepository = new UserRepository(dbName);
        EmployeeRepository employeeRepository = new EmployeeRepository(dbName);

        String idToUpdate = userVM.getUserID();
        String operation = userVM.getOperation();
        String role = userVM.getRole();

        User toUpdate = userRepository.searchUser(idToUpdate);
        Employee personalData = null;
        if (toUpdate != null) {
            personalData = employeeRepository.searchEmployee(toUpdate.getPerson());
        }

        if (personalData != null && operation.equals("update")) {
            userVM.setPerson(toUpdate.getPerson());
            userVM.setFirstName(personalData.getFirstName());
            userVM.setLastName(personalData.getLastName());
            userVM.setDob(personalData.getDateOfBirth().toString());
            userVM.setAddress(personalData.getAddress());
            userVM.setPhone(personalData.getPhoneNumber());
            userVM.setEmail(personalData.getEmail());
            userVM.setUserField(toUpdate.getUsername());
            userVM.setPassField(toUpdate.getPassword());
            userVM.setStatus(toUpdate.getAdminStatus());
        }
        else {
            userVM.setPerson("");
            userVM.setFirstName("");
            userVM.setLastName("");
            userVM.setDob("");
            userVM.setAddress("");
            userVM.setPhone("");
            userVM.setEmail("");
            userVM.setUserField("");
            userVM.setPassField("");
            userVM.setStatus("");
        }

        UpdateNewUser updateNewUser = new UpdateNewUser(userVM, idToUpdate, operation, role);
        return true;
    }
}
