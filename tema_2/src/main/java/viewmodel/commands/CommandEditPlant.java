package viewmodel.commands;


import model.Plant;
import model.repository.PlantRepository;
import viewmodel.PlantVM;

public class CommandEditPlant implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandEditPlant(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);

        String id = this.plantVM.getPlantID();
        Plant newPlant = this.validInformation();
        if (newPlant != null) {
            boolean flag = plantRepository.updatePlant(id, newPlant);
            if (flag) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    private Plant validInformation() {
        String plantId = this.plantVM.getPlantID();
        if (plantId == null || plantId.length() == 0) {
            return null;
        }

        String plantName = this.plantVM.getPlantName();
        if (plantName == null || plantName.length() == 0) {
            return null;
        }

        String species = this.plantVM.getSpecies();
        if (species == null || species.length() == 0) {
            return null;
        }

        String type = this.plantVM.getPlantType();
        if (type == null || type.length() == 0) {
            return null;
        }

        String carnivorous = this.plantVM.getCarnivorous();
        if (carnivorous == null || carnivorous.length() == 0) {
            carnivorous = "no";
        }

        String endangered = this.plantVM.getEndangered();
        if (endangered == null || endangered.length() == 0) {
            endangered = "no";
        }

        String location = this.plantVM.getLocation();
        if (location == null || location.length() == 0) {
            return null;
        }

        return new Plant(plantId, plantName, species, type, carnivorous, endangered, location);
    }
}
