package model;

import javax.print.Doc;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter implements DocumentWriter {
    @Override
    public void writeFile(List<Plant> list) {
        String filePath = "plantFile.csv";

        try (FileWriter writer = new FileWriter(filePath)) {
            //csv header
            writer.write("ID,Name,Species,Type,Carnivorous,Endangered,Location\n");

            //csv content
            for (Plant plant : list) {
                writer.write(plant.getId() + ",");
                writer.write(plant.getPlantName() + ",");
                writer.write(plant.getSpecies() + ",");
                writer.write(plant.getPlantType() + ",");
                writer.write(plant.getCarnivorous() + ",");
                writer.write(plant.getEndangered() + ",");
                writer.write(plant.getLocation() + "\n");
            }

            writer.close();
            System.out.println("CSV file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("plantFile.csv"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
