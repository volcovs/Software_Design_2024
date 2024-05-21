package com.example.proiect.service;

import com.example.proiect.entity.Plant;
import org.apache.commons.codec.binary.Base64;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JSONWriter implements DocumentWriter {
    @Override
    public void writeFile(List<Plant> list) {
        String filePath = "plantFile.json";

        try (FileWriter writer = new FileWriter(filePath)) {
            // Start the JSON array
            writer.write("[\n");

            // JSON representation for each object in the list
            for (int i = 0; i < list.size(); i++) {
                Plant plant = list.get(i);
                writer.write(" { \"id\": \"" + plant.getId() + "\", " +
                        "\"name\": \"" + plant.getPlantName() + "\", " +
                        "\"species\": \"" + plant.getSpecies() + "\", " +
                        "\"type\": \"" + plant.getPlantType() + "\", " +
                        "\"carnivorous\": \"" + plant.getCarnivorous() + "\", " +
                        "\"endangered\": \"" + plant.getEndangered() + "\", " +
                        "\"location\": \"" + plant.getLocation() + "\", " +
                        "\"image\": \"" + Base64.encodeBase64String(plant.getImg()) + "\" }");
                if (i != list.size()-1) {
                    writer.write(",\n");
                }
            }

            // End the JSON array
            writer.write("\n]");
            writer.close();
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("plantFile.json"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
