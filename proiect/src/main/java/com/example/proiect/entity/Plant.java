package com.example.proiect.entity;

import jakarta.persistence.*;

import javax.swing.*;
import java.awt.*;

@Entity
@Table(name="plant")
public class Plant {
    @Id
    @Column(name="plant_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="plant_name")
    private String plantName;
    @Column(name="species")
    private String species;
    @Column(name="plant_type")
    private String plantType;
    @Column(name="carnivorous")
    private String carnivorous;
    @Column(name="endangered")
    private String endangered;
    @Column(name="location")
    private String location;
    @Column(name="image", columnDefinition="blob")
    @Lob
    private byte[] img;

    public Plant(Integer id, String name, String species, String type, String c, String e, String location, byte[] image) {
        this.id  = id;
        this.plantName = name;
        this.species = species;
        this.plantType = type;
        this.carnivorous = c;
        this.endangered = e;
        this.location = location;

        ImageIcon im = new ImageIcon(image);
        Image aux = im.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        //this.img = new ImageIcon(aux);
        this.img = image;
    }

    public Plant() {

    }

    public Integer getId() { return this.id; }
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

    public byte[] getImg() { return this.img; }

    public void setId(Integer newID) { this.id = newID; }
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setPlantType(String type) {
        this.plantType = type;
    }

    public void setCarnivorous(String carnivorous) {
       this.carnivorous = carnivorous;
    }

    public void setEndangered(String endangered) {
        this.endangered = endangered;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImg(byte[] icon) { this.img = icon; }

    @Override
    public boolean equals(Object obj) {
        Plant p = (Plant)obj;

        return (this.plantName.equals(p.plantName)) && (this.species.equals(p.species)) && (this.plantType.equals(p.plantType));
    }
}
