package model;

import java.util.Objects;

public class Plant {
    private String id;
    private String plantName;
    private String species;
    private String plantType;
    private String carnivorous;
    private String endangered;
    private String location;

    public Plant(String id, String name, String species, String type, String c, String e, String location) {
        this.id  = id;
        this.plantName = name;
        this.species = species;
        this.plantType = type;
        this.carnivorous = c;
        this.endangered = e;
        this.location = location;
    }

    public Plant() {

    }

    public String getId() { return this.id; }
    public String getPlantName() {
        return this.plantName;
    }

    public String getSpecies() {
        return this.species;
    }

    public String getPlantType() {
        return this.plantType;
    }

    public String getCarnivorous() {
        return this.carnivorous;
    }

    public String getEndangered() {
        return this.endangered;
    }

    public String getLocation() {
        return this.location;
    }

    @Override
    public boolean equals(Object obj) {
        Plant p = (Plant)obj;

        return (this.plantName.equals(p.plantName)) && (this.species.equals(p.species)) && (this.plantType.equals(p.plantType));
    }
}
