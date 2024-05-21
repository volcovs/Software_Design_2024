package com.example.proiect.service;

import com.example.proiect.entity.Plant;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.*;
import java.util.List;

public class DOCWriter implements DocumentWriter {
    @Override
    public void writeFile(List<Plant> list) {
        XWPFDocument document = new XWPFDocument();
        FileOutputStream out = null;

        try {
            out = new FileOutputStream("plantFile.docx");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        //create table
        XWPFTable table = document.createTable();

        //create header
        XWPFTableRow tableRowOne = table.getRow(0);
        tableRowOne.getCell(0).setText("ID");
        tableRowOne.addNewTableCell().setText("Plant name");
        tableRowOne.addNewTableCell().setText("Species");
        tableRowOne.addNewTableCell().setText("Type");
        tableRowOne.addNewTableCell().setText("Carnivorous");
        tableRowOne.addNewTableCell().setText("Endangered");
        tableRowOne.addNewTableCell().setText("Location");
        tableRowOne.addNewTableCell().setText("Image");

        //file content
        for (Plant plant: list) {

            try {
                XWPFTableRow tableRow = table.createRow();
                tableRow.getCell(0).setText(plant.getId().toString());
                tableRow.getCell(1).setText(plant.getPlantName());
                tableRow.getCell(2).setText(plant.getSpecies());
                tableRow.getCell(3).setText(plant.getPlantType());
                tableRow.getCell(4).setText(plant.getCarnivorous());
                tableRow.getCell(5).setText(plant.getEndangered());
                tableRow.getCell(6).setText(plant.getLocation());

                XWPFParagraph p = tableRow.getCell(7).addParagraph();
                XWPFRun run = p.createRun();

                int width = 75;
                int height = 75;

                run.addPicture(new ByteArrayInputStream(plant.getImg()), XWPFDocument.PICTURE_TYPE_JPEG, "plant", Units.toEMU(width),
                        Units.toEMU(height));
            } catch (Exception e) {
                System.out.println("Error writing DOCX file: " + e.getMessage());
            }
        }

        try {
            document.write(out);
            out.close();
            System.out.println(".DOCX file created successully");
        } catch(IOException exception) {
            System.out.println("Error writing DOCX file: " + exception.getMessage());
        }
    }

    @Override
    public void openFile() {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(new File("plantFile.docx"));
        } catch (IOException ex) {
            System.out.println("Error opening file");
        }
    }
}
