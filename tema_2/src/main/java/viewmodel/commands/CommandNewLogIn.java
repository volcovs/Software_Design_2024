package viewmodel.commands;

import view.LogInPage;
import viewmodel.PlantVM;

public class CommandNewLogIn implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandNewLogIn(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        String role = plantVM.getRole();

        LogInPage logInPage = new LogInPage(role);
        return true;
    }
}
