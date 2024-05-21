package viewmodel.commands;

import model.repository.PlantRepository;
import viewmodel.PlantVM;

public class CommandDeletePlant implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandDeletePlant(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);

        String id = this.plantVM.getPlantID();
        boolean flag = plantRepository.deletePlant(id);

        if (flag) {
            return true;
        }
        else {
            return false;
        }
    }
}
