package viewmodel.commands;

import model.Plant;
import model.repository.PlantRepository;
import view.UpdateNewPlant;
import viewmodel.PlantVM;

public class CommandNewPage implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandNewPage(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);

        String idToUpdate = plantVM.getPlantID();
        String operation = plantVM.getOperation();
        String role = plantVM.getRole();

        Plant toUpdate = plantRepository.searchPlant(idToUpdate);
        if (toUpdate != null && operation.equals("update")) {
            plantVM.setPlantName(toUpdate.getPlantName());
            plantVM.setSpecies(toUpdate.getSpecies());
            plantVM.setPlantType(toUpdate.getPlantType());
            plantVM.setCarnivorous(toUpdate.getCarnivorous());
            plantVM.setEndangered(toUpdate.getEndangered());
            plantVM.setLocation(toUpdate.getLocation());
        }
        else {
            plantVM.setPlantName("");
            plantVM.setSpecies("");
            plantVM.setPlantType("");
            plantVM.setCarnivorous("");
            plantVM.setEndangered("");
            plantVM.setLocation("");
        }

        UpdateNewPlant updateNewPlant = new UpdateNewPlant(plantVM, idToUpdate, operation, role, null);
        return true;
    }
}
