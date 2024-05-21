package viewmodel.commands;

import model.Plant;
import model.repository.PlantRepository;
import viewmodel.PlantVM;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommandViewPlants implements ICommands {
    private PlantVM plantVM;
    private String dbName = "gradina_botanica";

    public CommandViewPlants(PlantVM plantVM) {
        this.plantVM = plantVM;
    }

    @Override
    public boolean execute() {
        PlantRepository plantRepository = new PlantRepository(dbName);
        String filterBy = plantVM.getFilter();
        String sortBy = plantVM.getSort();
        String search = plantVM.getSearchInfo();
        List<Plant> list = plantRepository.plantListFiltered(filterBy, search);

        if (sortBy.equals("Tip")) {
            Collections.sort(list, new Comparator<Plant>() {
                @Override
                public int compare(Plant o1, Plant o2) {
                    return o1.getPlantType().compareTo(o2.getPlantType());
                }
            });
        }
        else if (sortBy.equals("Specie")) {
            Collections.sort(list, new Comparator<Plant>() {
                @Override
                public int compare(Plant o1, Plant o2) {
                    return o1.getSpecies().compareTo(o2.getSpecies());
                }
            });
        }

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

        DefaultTableModel table = new DefaultTableModel(tableData, columnNames.toArray());
        this.plantVM.setTableContent(table);
        return true;
    }
}
