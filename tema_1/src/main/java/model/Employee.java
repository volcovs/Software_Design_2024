package model;

import java.time.LocalDate;
import java.util.Date;

public class Employee extends Person {


    public Employee(String id, String first, String last, LocalDate dob, String address, String phone, String email) {
        super(id, first, last, dob, address, phone, email);
    }

    public String getId() {
        return super.getId();
    }
    public String getFirstName() {
        return super.getFirstName();
    }

    public String getLastName() {
        return super.getLastName();
    }

    public LocalDate getDateOfBirth() {
        return super.getDateOfBirth();
    }

    public String getAddress() {
        return super.getAddress();    }

    public String getPhoneNumber() {
        return super.getPhoneNumber();
    }

    public String getEmail() {
        return super.getEmail();
    }
}
