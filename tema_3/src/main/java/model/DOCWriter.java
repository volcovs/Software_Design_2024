package model;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

        //file content
        for (Plant plant: list) {
            XWPFTableRow tableRow = table.createRow();
            tableRow.getCell(0).setText(plant.getId());
            tableRow.getCell(1).setText(plant.getPlantName());
            tableRow.getCell(2).setText(plant.getSpecies());
            tableRow.getCell(3).setText(plant.getPlantType());
            tableRow.getCell(4).setText(plant.getCarnivorous());
            tableRow.getCell(5).setText(plant.getEndangered());
            tableRow.getCell(6).setText(plant.getLocation());
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
