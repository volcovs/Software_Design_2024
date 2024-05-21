package com.example.proiect.service;

import com.example.proiect.controller.PlantController;
import com.example.proiect.entity.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import com.example.proiect.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class PlantService implements Observer{
    public static final String PHOTO_DIRECTORY = "C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/";
    @Autowired
    private PlantRepository plantRepository;

    public List<Plant> plantList() {
        return (List<Plant>) this.plantRepository.findAll();
    }

    public List<Plant> plantListSorted(String sortBy) {
        List<Plant> aux = plantList();

        if (sortBy.equals("Tip")) {
            Collections.sort(aux, new Comparator<Plant>() {
                @Override
                public int compare(Plant o1, Plant o2) {
                    return o1.getPlantType().compareTo(o2.getPlantType());
                }
            });
        }
        else if (sortBy.equals("Specie")) {
                Collections.sort(aux, new Comparator<Plant>() {
                    @Override
                    public int compare(Plant o1, Plant o2) {
                        return o1.getSpecies().compareTo(o2.getSpecies());
                    }
                });
        }

        return aux;
    }

    public Plant findByNameOrSpecies(String search) {
        List<Plant> plants = plantList();

        for(Plant p: plants) {
            if (p.getPlantName().equals(search) || p.getSpecies().equals(search)) {
                return p;
            }
        }

        return null;
    }

    public List<Plant> filterList(String criterion, Optional<String> value) {
        List<Plant> plants = plantList();

        if (criterion.equals("Tip")) {
            plants.removeIf(p -> !p.getPlantType().equals(value.get()));
        }
        else if (criterion.equals("Specie")) {
            plants.removeIf(p -> !p.getSpecies().equals(value.get()));
        }
        else if (criterion.equals("Carnivore")) {
            plants.removeIf(p -> p.getCarnivorous().equals("no"));
        }
        else if (criterion.equals("In pericol de disparitie")) {
            plants.removeIf(p -> p.getEndangered().equals("no"));
        }
        else if (criterion.equals("Locatie")) {
            plants.removeIf(p -> !p.getLocation().equals(value.get()));
        }
        else if (criterion.equals("Nume")) {
            plants.removeIf(p -> !p.getPlantName().equals(value.get()));
        }
       
        return plants;
    }


    public List<Plant> saveToFile(String format) {
        DocumentWriter writer;
        List<Plant> plants = plantList();

        DocumentFactory factory = null;
        switch (format) {
            case "csv" -> { factory = new CSVFactory();  }
            case "json" -> { factory = new JSONFactory();  }
            case "xml" -> { factory = new XMLFactory();  }
            case "docx" -> { factory = new DOCFactory(); }
            default -> { System.out.println("Not a valid format choice");}
        }

        if (factory != null) {
            writer = factory.factoryMethod();
            writer.writeFile(plants);
            //writer.openFile(); -> not working anymore with Spring, but the controller will trigger
            // a download from the browser
        }
        else {
            //Error
        }

        return plants;
    }

    public Plant addPlant(Plant plant) {
        return plantRepository.save(plant);
    }

    public Plant updatePlant(Plant plant) {
        Optional<Plant> existing = plantRepository.findById(plant.getId());
        if (existing.isPresent()) {
            existing.get().setPlantName(plant.getPlantName());
            existing.get().setSpecies(plant.getSpecies());
            existing.get().setPlantType(plant.getPlantType());
            existing.get().setCarnivorous(plant.getCarnivorous());
            existing.get().setEndangered(plant.getEndangered());
            existing.get().setLocation(plant.getLocation());
        }

        return plantRepository.save(plant);
    }

    public String deletePlant(Integer id) {
        plantRepository.deleteById(id);

        if (plantRepository.findById(id).isEmpty()) {
            return "Successfully deleted entry!";
        }
        else {
            return "Error!";
        }
    }

    public Plant findById(Integer id) {
        return plantRepository.findById(id).orElseThrow(() -> new RuntimeException("Plant not found"));
    }

    public String uploadPhoto(Integer id, String image) {
        Plant plant = findById(id);
        byte[] photo = Base64.decodeBase64(image);
        plant.setImg(photo);
        plantRepository.save(plant);
        return "Image changed";
    }

    public List<String> pieChart() {
        List<Plant> plants = plantList();
        List<String> types = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Plant p: plants) {
            if (!types.contains(p.getPlantType())) {
                types.add(p.getPlantType());
            }
        }

        for (String t: types) {
            int num = 0;
            for (Plant p: plants) {
                if (p.getPlantType().equals(t)) {
                    num++;
                }
            }

            values.add(String.valueOf(num));
            values.add(t);
        }

        return values;
    }


    public List<String> firstBarChart() {
        List<Plant> plants = plantList();
        List<String> values = new ArrayList<>();

        int num1 = 0;
        int num2 = 0;

        for (Plant p: plants) {
            if (p.getCarnivorous().equals("yes")) {
                num2++;
            }
            else {
                num1++;
            }
        }

        values.add(String.valueOf(num1));
        values.add(String.valueOf(num2));
        return values;
    }

    public List<String> secondBarChart() {
        List<Plant> plants = plantList();
        List<String> places = new ArrayList<>();
        List<String> values = new ArrayList<>();

        for (Plant p : plants) {
            if (!places.contains(p.getLocation())) {
                places.add(p.getLocation());
            }
        }

        for (String t : places) {
            int num = 0;
            for (Plant p : plants) {
                if (p.getLocation().equals(t)) {
                    num++;
                }
            }

            values.add(String.valueOf(num));
            values.add(t);
        }

        return values;
    }

    @Override
    public void update(Observable o, Object arg) {
        PlantController controller = (PlantController) o;
        saveToFile(controller.getFormat());
    }
}
