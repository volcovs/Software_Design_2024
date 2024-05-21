package com.example.proiect.service;

import com.example.proiect.entity.Plant;
import org.apache.commons.codec.binary.Base64;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XMLWriter implements DocumentWriter {



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
                        "<location>" + plant.getLocation() + "</location>\n    " +
                        "<image>" + Base64.encodeBase64String(plant.getImg()) + "</image\n " + "</object>\n");
            }

            writer.write("</data>");
            writer.close();
            System.out.println("XML file created successfully.");
        } catch (IOException e) {
            System.err.println("Error writing XML file: " + e.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("plantFile.xml"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
