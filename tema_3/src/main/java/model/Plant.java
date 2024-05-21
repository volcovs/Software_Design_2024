package model;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Plant {
    private String id;
    private String plantName;
    private String species;
    private String plantType;
    private String carnivorous;
    private String endangered;
    private String location;
    private ImageIcon img;

    public Plant(String id, String name, String species, String type, String c, String e, String location, byte[] image) {
        this.id  = id;
        this.plantName = name;
        this.species = species;
        this.plantType = type;
        this.carnivorous = c;
        this.endangered = e;
        this.location = location;

        ImageIcon im = new ImageIcon(image);
        Image aux = im.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        this.img = new ImageIcon(aux);
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

    public ImageIcon getImg() { return this.img; }

    @Override
    public boolean equals(Object obj) {
        Plant p = (Plant)obj;

        return (this.plantName.equals(p.plantName)) && (this.species.equals(p.species)) && (this.plantType.equals(p.plantType));
    }
}
