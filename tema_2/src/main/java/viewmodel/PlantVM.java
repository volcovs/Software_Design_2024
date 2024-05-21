package viewmodel;

import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;
import viewmodel.commands.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PlantVM {
    private Property<String> filter;
    private Property<String> sort;
    private Property<String> search;
    private Property<String> name;
    private Property<String> species;
    private Property<String> type;
    private Property<String> carnivorous;
    private Property<String> endangered;
    private Property<String> location;
    private Property<String> id;
    private Property<String> operation;
    private Property<String> role;
    private Property<String> format;
    private Property<DefaultTableModel> table;
    public ICommands commandViewPlants;
    public ICommands commandSearchPlant;
    public ICommands commandAddPlant;
    public ICommands commandEditPlant;
    public ICommands commandDeletePlant;
    public ICommands commandNewPage;
    public ICommands commandSaveToFile;
    public ICommands commandNewLogIn;
    public ICommands commandViewUsersFromMenu;
    public ICommands commandNewUserFromMenu;

    public PlantVM() {
        this.commandViewPlants = new CommandViewPlants(this);
        this.commandSearchPlant = new CommandSearchPlant(this);
        this.commandAddPlant = new CommandAddPlant(this);
        this.commandEditPlant = new CommandEditPlant(this);
        this.commandDeletePlant = new CommandDeletePlant(this);
        this.commandNewPage = new CommandNewPage(this);
        this.commandSaveToFile = new CommandSaveToFile(this);
        this.commandNewLogIn = new CommandNewLogIn(this);
        this.commandNewUserFromMenu = new CommandNewUserFromMenu(this);
        this.commandViewUsersFromMenu = new CommandViewUsersFromMenu(this);
        search = PropertyFactory.createProperty("search", this, String.class);
        filter = PropertyFactory.createProperty("filter", this, String.class);
        sort = PropertyFactory.createProperty("sort", this, String.class);
        name = PropertyFactory.createProperty("name", this, String.class);
        species = PropertyFactory.createProperty("species", this, String.class);
        type = PropertyFactory.createProperty("type", this, String.class);
        carnivorous = PropertyFactory.createProperty("carnivorous", this, String.class);
        endangered = PropertyFactory.createProperty("endangered", this, String.class);
        location = PropertyFactory.createProperty("location", this, String.class);
        id = PropertyFactory.createProperty("id", this, String.class);
        role = PropertyFactory.createProperty("role", this, String.class);
        operation = PropertyFactory.createProperty("operation", this, String.class);
        format = PropertyFactory.createProperty("format", this, String.class);
        this.table = PropertyFactory.createProperty("table", this, new javax.swing.table.DefaultTableModel());
    }

    public void setTableContent(DefaultTableModel table) {
        this.table.set(table);
    }

    public String getPlantID() {
        return this.id.get();
    }

    public String getFilter() {
        return this.filter.get();
    }

    public String getSort() {
        return this.sort.get();
    }

    public String getSearchInfo() {
        return this.search.get();
    }

    public void setPlantName(String name) {
        this.name.set(name);
    }

    public void setSpecies(String species) {
        this.species.set(species);
    }

    public void setPlantType(String type) {
        this.type.set(type);
    }

    public void setCarnivorous(String carnivorous) {
        this.carnivorous.set(carnivorous);
    }

    public void setEndangered(String endangered) {
        this.endangered.set(endangered);
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getPlantName() {
        return this.name.get();
    }

    public String getSpecies() {
        return this.species.get();
    }

    public String getPlantType() {
        return this.type.get();
    }

    public String getCarnivorous() {
        return this.carnivorous.get();
    }

    public String getEndangered() {
        return this.endangered.get();
    }

    public String getLocation() {
        return this.location.get();
    }

    public void setFilter(String filter) {
        this.filter.set(filter);
    }

    public void setSort(String sort) {
        this.sort.set(sort);
    }

    public void setSearch(String search) {
        this.search.set(search);
    }

    public void setPlantID(String id) {
        this.id.set(id);
    }

    public String getRole() {
        return this.role.get();
    }

    public String getOperation() {
        return this.operation.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public void setOperation(String operation) {
        this.operation.set(operation);
    }

    public void setFormat(String format) {
        this.format.set(format);
    }

    public String getFormat() {
        return this.format.get();
    }

}
