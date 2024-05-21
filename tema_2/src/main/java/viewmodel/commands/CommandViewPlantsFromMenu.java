package viewmodel.commands;

import view.PlantPage;
import viewmodel.EmployeeVM;

public class CommandViewPlantsFromMenu implements ICommands {
    private EmployeeVM userVM;
    private String dbName = "gradina_botanica";

    public CommandViewPlantsFromMenu(EmployeeVM employeeVM) {
        this.userVM = employeeVM;
    }

    @Override
    public boolean execute() {
        String role = userVM.getRole();

        PlantPage plantPage = new PlantPage(role);
        return true;
    }
}
