package viewmodel.commands;

import model.*;
import model.repository.PlantRepository;
import viewmodel.PlantVM;
import java.util.List;

public class CommandSaveToFile implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";
    private DocumentWriter writer;

    public CommandSaveToFile(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);
        String format = plantVM.getFormat();

        List<Plant> plants = plantRepository.plantList();

        switch (format) {
            case ".csv" -> { writer = new CSVWriter();  }
            case ".json" -> { writer = new JSONWriter();  }
            case ".xml" -> { writer = new XMLWriter();  }
            case ".doc" -> { writer = new DOCWriter(); }
            default -> { System.out.println("Not a valid format choice"); return false; }
        }

        writer.writeFile(plants);
        return true;
    }

}
