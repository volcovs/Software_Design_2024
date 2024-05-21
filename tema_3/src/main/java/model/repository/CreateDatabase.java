package model.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/";
    private static final String TIMEZONE = "?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "password";

    private String dbName;
    private static CreateDatabase singleInstance;

    public CreateDatabase() {

    }

    public CreateDatabase(String dbName) {
        this.dbName = dbName;

        try {
            Class.forName(DRIVER);
            Connection connection = null;

            try {
                connection = DriverManager.getConnection(DBURL+TIMEZONE, USER, PASS);

                Statement s = connection.createStatement();
                String query = "CREATE DATABASE " + dbName;
                s.executeUpdate(query);
                System.out.println("Database " + dbName + " created successfully!");

                singleInstance = this;
                if (!connection.isClosed()) {
                    close(connection);
                }

            } catch (SQLException e) {
                System.out.println("Error creating new database");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Couldn't close the connection");
            }
        }
    }

    public void createTablePlant() {
        Repository repo = new Repository(dbName);
        Connection conn  = repo.getConnection();

        try {
            Statement s = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Plant (plant_id INT NOT NULL AUTO_INCREMENT primary key, " +
                    "plant_name VARCHAR(50), species VARCHAR(100), plant_type VARCHAR(50), carnivorous VARCHAR(3), " +
                    "endangered VARCHAR(3), location VARCHAR(50), image BLOB);";
            s.executeUpdate(query);

            System.out.println("Table Plant created successfully!");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableEmployee() {
        Repository repo = new Repository(dbName);
        Connection conn  = repo.getConnection();

        try {
            Statement s = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Employee (person_id INT NOT NULL AUTO_INCREMENT primary key, " +
                    "first_name VARCHAR(50), last_name VARCHAR(50), date_of_birth DATE, address VARCHAR(50), " +
                    "phone_number VARCHAR(13), email VARCHAR(50));";
            s.executeUpdate(query);

            System.out.println("Table Employee created successfully!");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTableUser() {
        Repository repo = new Repository(dbName);
        Connection conn  = repo.getConnection();

        try {
            Statement s = conn.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS Users (user_id INT NOT NULL AUTO_INCREMENT primary key, " +
                    "person_id INT NOT NULL, username VARCHAR(50), password VARCHAR(50), admin_status VARCHAR(3));";
            s.executeUpdate(query);

            System.out.println("Table Users created successfully!");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void addInitialData() {
        Connection conn  = Repository.getConnection();

        try {
            Statement s = conn.createStatement();
            String query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('lalea', 'tulipa gesneriana', 'magnoliophyta', 'no', 'no', 'right entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/lalea.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('trandafir', 'rosa chinensis', 'magnoliophyta', 'no', 'no', 'left entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/trandafir.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('capcana lui Venus', 'dionaea muscipula', 'magnoliophyta', 'yes', 'no', 'left entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/venus.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('bujor', 'paeonia pregrina', 'magnoliophyta', 'no', 'yes', 'center', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/bujor.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('garofita', 'dianthus callizonus', 'magnoliophyta', 'no', 'yes', 'right entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/garofita.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('ginkgo biloba', 'ginkgo biloba', 'gymnospermae', 'no', 'no', 'back area', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/gingko.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('pin', 'pinus pinea', 'gymnospermae', 'no', 'no', 'left entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/pin.jpg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('stejar', 'quercus alba', 'angiospermae', 'no', 'yes', 'right entrance', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/stejar.jpeg'));";
            s.executeUpdate(query);
            query = "INSERT INTO plant(plant_name, species, plant_type, carnivorous, endangered, location, image) VALUES ('salcie', 'salix triandra', 'angiospermae', 'no', 'no', 'back area', LOAD_FILE('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/images/salcie.jpg'));";
            s.executeUpdate(query);


            query = "INSERT INTO employee(first_name, last_name, date_of_birth, address, phone_number, email) VALUES ('D', 'V', '2000-03-15', 'str. Unu, 5', '0733222555', 'd@yahoo.com');";
            s.executeUpdate(query);
            query = "INSERT INTO employee(first_name, last_name, date_of_birth, address, phone_number, email) VALUES ('F', 'R', '1998-06-03', 'str. Ciresului, 32', '076444222', 'flor@gmail.com');";
            s.executeUpdate(query);
            query = "INSERT INTO employee(first_name, last_name, date_of_birth, address, phone_number, email) VALUES ('A', 'M', '2001-11-19', 'str. Avg, 10', '07999333', 'a.m@yahoo.com');";
            s.executeUpdate(query);
            query = "INSERT INTO employee(first_name, last_name, date_of_birth, address, phone_number, email) VALUES ('L', 'K', '1999-01-14', 'str. Doi, 76', '071111222', 'lk@gmail.com');";
            s.executeUpdate(query);


            query = "INSERT INTO users(person_id, username, password, admin_status) VALUES (1, 'dv_one', 'pass', 'no');";
            s.executeUpdate(query);
            query = "INSERT INTO users(person_id, username, password, admin_status) VALUES (2, 'flor', '1234', 'no');";
            s.executeUpdate(query);
            query = "INSERT INTO users(person_id, username, password, admin_status) VALUES (3, 'admin', 'admin', 'yes');";
            s.executeUpdate(query);
            query = "INSERT INTO users(person_id, username, password, admin_status) VALUES (4, 'user_lk', 'SeCuRe', 'no');";
            s.executeUpdate(query);

            System.out.println("Initial data was added to the database successfully!");

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
