package org.example;
import controller.PlantPageController;
import model.repository.CreateDatabase;

public class Main {
    public static void main(String[] args) {
        CreateDatabase db = new CreateDatabase("gradina_botanica");

        db.createTablePlant();
        db.createTableUser();
        db.createTableEmployee();
        db.addInitialData();


        PlantPageController plantPageController = new PlantPageController("visitor", "romanian");
        plantPageController.getView().setVisible(true);
    }
}