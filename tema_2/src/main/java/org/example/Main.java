package org.example;

import javafx.stage.Stage;
import model.repository.CreateDatabase;
import view.LogInPage;
import view.PlantPage;

public class Main {
    public static void main(String[] args) {
        CreateDatabase db = new CreateDatabase("gradina_botanica");

        db.createTablePlant();
        db.createTableUser();
        db.createTableEmployee();
        db.addInitialData();

        PlantPage page = new PlantPage("visitor");
    }
}