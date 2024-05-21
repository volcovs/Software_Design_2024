package model;

import java.time.LocalDate;

public class Person {
    private String id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;

    public Person(String id, String first, String last, LocalDate dob, String address, String phone, String email) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.dateOfBirth = dob;
        this.address = address;
        this.phoneNumber = phone;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(Object obj) {
        Person p = (Person) obj;

        return ((this.firstName.equals(p.firstName)) && (this.lastName.equals(p.lastName)) && (this.dateOfBirth.equals(p.dateOfBirth)));
    }
}
