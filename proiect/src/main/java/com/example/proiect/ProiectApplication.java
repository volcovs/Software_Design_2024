package com.example.proiect;

import com.example.proiect.repository.CreateDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProiectApplication {

    public static void main(String[] args) {
        CreateDatabase db = new CreateDatabase("gradina_botanica");
        db.createTableUser();
        db.createTablePlant();
        db.createTableEmployee();
        db.addInitialData();


        SpringApplication.run(ProiectApplication.class, args);
    }

}
