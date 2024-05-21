package model;

import java.util.Objects;

public class User {
    private String userID;
    private String person;
    private String password;
    private String username;
    private String adminStatus;

    public User(String userID, String person, String username, String password) {
        this.userID = userID;
        this.person = person;
        this.password = password;
        this.username = username;
    }

    public User(String userID, String person, String username, String password, String adminStatus) {
        this.userID = userID;
        this.person = person;
        this.password = password;
        this.username = username;
        this.adminStatus = adminStatus;
    }

    public User() {

    }

    public String getPerson() {
        return this.person;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() { return this.username; }

    public String getAdminStatus() {
        return this.adminStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(person, user.person) && Objects.equals(password, user.password) && Objects.equals(username, user.username);
    }
}
