package viewmodel.commands;

import view.UpdateNewUser;
import viewmodel.EmployeeVM;
import viewmodel.PlantVM;

public class CommandNewUserFromMenu implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandNewUserFromMenu(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        EmployeeVM userVM = new EmployeeVM();
        String role = plantVM.getRole();
        String operation = plantVM.getOperation();

        UpdateNewUser updateNewUser = new UpdateNewUser(userVM, "1", operation, role);
        return true;
    }
}
