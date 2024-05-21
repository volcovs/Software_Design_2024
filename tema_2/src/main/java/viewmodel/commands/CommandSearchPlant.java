package viewmodel.commands;

import model.Plant;
import model.repository.PlantRepository;
import viewmodel.PlantVM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandSearchPlant implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandSearchPlant(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);
        String search = this.plantVM.getSearchInfo();
        String filterBy = "Nume";

        List<Plant> plants = plantRepository.plantListFiltered(filterBy, search);

        if (plants != null && plants.size() != 0) {
            listToTable(plants);
        }
        else {
            filterBy = "Specie";
            plants = plantRepository.plantListFiltered(filterBy, search);

            if (plants != null && plants.size() != 0) {
                listToTable(plants);
            }
            else {
                plants = new ArrayList<Plant>();
                plants.add(new Plant("", "", "", "", "", "", ""));
                listToTable(plants);
                return false;
            }
        }

        return true;
    }

    private boolean listToTable(List<Plant> list) {
        List<String> columnNames = new ArrayList<String>();
        int iter = 0;

        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }

        Object[][] tableData = new Object[list.size()][iter];
        int i = 0;
        for (Plant obj : list) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                    return false;
                }
            }
            i++;
        }

        //JTable table = new JTable(tableData, columnNames.toArray());
        DefaultTableModel table = new DefaultTableModel(tableData, columnNames.toArray());
        this.plantVM.setTableContent(table);
        return false;
    }

}
