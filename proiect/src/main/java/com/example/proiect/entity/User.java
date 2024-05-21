package com.example.proiect.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="users")
public class User extends AbstractUser {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    @Column(name="person_id")
    private Integer person;
    @Column(name="password")
    private String password;
    @Column(name="username")
    private String username;
    @Column(name="admin_status")
    private String adminStatus;

    public User(Integer userID, Integer person, String username, String password) {
        this.userID = userID;
        this.person = person;
        this.password = password;
        this.username = username;
    }

    public User(Integer userID, Integer person, String username, String password, String adminStatus) {
        this.userID = userID;
        this.person = person;
        this.password = password;
        this.username = username;
        this.adminStatus = adminStatus;
    }

    public User() {

    }

    public Integer getUserID() { return this.userID;}

    public Integer getPerson() {
        return this.person;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() { return this.username; }

    public String getAdminStatus() {
        return this.adminStatus;
    }

    public void setUserID(Integer userID) { this.userID = userID;}

    public void setPerson(Integer person) {
        this.person = person;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

    public void setUsername(String user) { this.username = user; }

    public void setAdminStatus(String status) {
        this.adminStatus = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(person, user.person) && Objects.equals(password, user.password) && Objects.equals(username, user.username);
    }
}
