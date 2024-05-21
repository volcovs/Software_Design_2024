package model.repository;

import model.Plant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlantRepository {

    private Repository repository;
    private String dbName = "gradina_botanica";


    public PlantRepository(String dbName) {
        this.dbName = dbName;
        this.repository = new Repository(dbName);
    }

    public boolean addPlant(Plant plant) {
        String command = "INSERT INTO Plant(plant_name, species, plant_type, carnivorous, endangered, location) Values('";
        command += plant.getPlantName() + "', '" + plant.getSpecies() + "', '";
        command += plant.getPlantType() + "', '" + plant.getCarnivorous() + "', '";
        command += plant.getEndangered() + "', '" + plant.getLocation() + "');";

        return this.repository.executeCommand(command);
    }

    public boolean updatePlant(String id, Plant plant) {
        String command = "UPDATE Plant SET";
        command += " plant_name = '" + plant.getPlantName() + "',";
        command += " species = '" + plant.getSpecies() + "', ";
        command += " plant_type = '" + plant.getPlantType() + "', ";
        command += " carnivorous = '" + plant.getCarnivorous() + "', ";
        command += " endangered = '" + plant.getEndangered() + "', ";
        command += " location = '" + plant.getLocation() + "' ";

        command += "WHERE plant_id = " + id + ";";

        return this.repository.executeCommand(command);
    }

    public boolean deletePlant(String id) {
        String command = "DELETE FROM Plant WHERE plant_id = " + id + ";";

        return this.repository.executeCommand(command);
    }

    public Plant searchPlant(String id) {
        String search = "SELECT * FROM Plant WHERE plant_id = " + id + ";";
        ResultSet rs = this.repository.getTable(search);
        Plant p = null;

        try {
            rs.next();

            String plant_id = String.valueOf(rs.getInt("plant_id"));
            String name = rs.getString("plant_name");
            String species = rs.getString("species");
            String type = rs.getString("plant_type");
            String carnivorous = rs.getString("carnivorous");
            String endangered = rs.getString("endangered");
            String location = rs.getString("location");

            p = new Plant(plant_id, name, species, type, carnivorous, endangered, location);
            this.repository.close(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;
    }

    public List<Plant> plantList() {
        List<Plant> plants = new ArrayList<>();

        String getPlants = "SELECT * FROM Plant";
        ResultSet rs = this.repository.getTable(getPlants);

        try {
            while(rs.next()) {
                Plant p;

                String plant_id = String.valueOf(rs.getInt("plant_id"));
                String name = rs.getString("plant_name");
                String species = rs.getString("species");
                String type = rs.getString("plant_type");
                String carnivorous = rs.getString("carnivorous");
                String endangered = rs.getString("endangered");
                String location = rs.getString("location");

                p = new Plant(plant_id, name, species, type, carnivorous, endangered, location);
                plants.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.repository.close(rs);
        return plants;
    }

    public List<Plant> plantListFiltered(String filterBy, String search) {
        List<Plant> plants = new ArrayList<>();
        ResultSet rs = null;

        if (filterBy == null && search == null) {
            return this.plantList();
        }
        if (filterBy.equals("Tip")) {
            String getPlants = "SELECT * FROM Plant where plant_type = '" + search + "';";
            rs = this.repository.getTable(getPlants);
        }
        else if (filterBy.equals("Specie")) {
            String getPlants = "SELECT * FROM Plant where species = '" + search + "';";
            rs = this.repository.getTable(getPlants);
        }
        else if (filterBy.equals("Carnivore")) {
            String getPlants = "SELECT * FROM Plant WHERE carnivorous = 'yes';";
            rs = this.repository.getTable(getPlants);
        }
        else if (filterBy.equals("In pericol de disparitie")) {
            String getPlants = "SELECT * FROM Plant WHERE endangered = 'yes';";
            rs = this.repository.getTable(getPlants);
        }
        else if (filterBy.equals("Locatie")) {
            String getPlants = "SELECT * FROM Plant WHERE location = '" + search + "';";
            rs = this.repository.getTable(getPlants);
        }
        else if (filterBy.equals("Nume")) {
            String getPlants = "SELECT * FROM Plant WHERE plant_name = '" + search + "';";
            rs = this.repository.getTable(getPlants);
        }
        else {
            //varianta invalida sau "-"
            return this.plantList();
        }

        try {
            while(rs.next()) {
                Plant p;

                String plant_id = String.valueOf(rs.getInt("plant_id"));
                String name = rs.getString("plant_name");
                String species = rs.getString("species");
                String type = rs.getString("plant_type");
                String carnivorous = rs.getString("carnivorous");
                String endangered = rs.getString("endangered");
                String location = rs.getString("location");

                p = new Plant(plant_id, name, species, type, carnivorous, endangered, location);
                plants.add(p);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        this.repository.close(rs);
        return plants;
    }
}
