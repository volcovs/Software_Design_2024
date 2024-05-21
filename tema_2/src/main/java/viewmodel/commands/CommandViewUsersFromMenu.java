package viewmodel.commands;

import view.UserPage;
import viewmodel.PlantVM;

public class CommandViewUsersFromMenu implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandViewUsersFromMenu(PlantVM plantVM) {
        this.plantVM = plantVM;
    }


    @Override
    public boolean execute() {
        String role = plantVM.getRole();

        UserPage userPage = new UserPage(role);
        return true;
    }
}
