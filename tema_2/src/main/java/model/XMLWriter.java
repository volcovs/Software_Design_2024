package model;

import model.DocumentWriter;
import model.Plant;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XMLWriter extends DocumentWriter {



    @Override
    public void writeFile(List<Plant> list) {
        String filePath = "plantFile.xml";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write XML declaration
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<data>\n");

            // .xml content -> representation for each object in the list
            for (Plant plant: list) {
                writer.write("  <object>\n    <id>" + plant.getId() + "</id>\n    " +
                        "<name>" + plant.getPlantName() + "</name>\n    " +
                        "<species>" + plant.getSpecies() + "</species>\n    " +
                        "<type>" + plant.getPlantType() + "</type>\n    " +
                        "<carnivorous>" + plant.getCarnivorous() + "</carnivorous>\n    " +
                        "<endangered>" + plant.getEndangered() + "</endangered>\n    " +
                        "<location>" + plant.getLocation() + "</location>\n  </object>\n");
            }

            writer.write("</data>");
            writer.close();
            System.out.println("XML file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing XML file: " + e.getMessage());
        }
    }
}
