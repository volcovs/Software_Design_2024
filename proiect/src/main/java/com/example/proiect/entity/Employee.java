package com.example.proiect.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="employee")
public class Employee {
    @Id
    @Column(name="person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name="address")
    private String address;
    @Column(name="phone_number")
    private String phoneNumber;
    @Column(name="email")
    private String email;

    public Employee(Integer id, String first, String last, LocalDate dob, String address, String phone, String email) {
        this.id = id;
        this.firstName = first;
        this.lastName = last;
        this.dateOfBirth = dob;
        this.address = address;
        this.phoneNumber = phone;
        this.email = email;
    }

    public Employee() {

    }

    public Integer getId() {
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
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String first) {
        this.firstName = first;
    }

    public void setLastName(String last) {
        this.lastName = last;
    }

    public void setDateOfBirth(LocalDate dob) {
        this.dateOfBirth = dob;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String number){ this.phoneNumber = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        Employee p = (Employee) obj;

        return ((this.firstName.equals(p.firstName)) && (this.lastName.equals(p.lastName)) && (this.dateOfBirth.equals(p.dateOfBirth)));
    }
}