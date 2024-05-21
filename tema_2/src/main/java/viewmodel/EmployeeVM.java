package viewmodel;

import viewmodel.commands.*;

import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeVM {
    private Property<String> userField;
    private Property<String> passField;
    private Property<String> userID;
    private Property<String> person;
    private Property<String> firstName;
    private Property<String> lastName;
    private Property<String> dob;
    private Property<String> address;
    private Property<String> phone;
    private Property<String> email;
    private Property<String> operation;
    private Property<String> role;
    private Property<String> status;
    public ICommands logIn;
    public ICommands commandViewUsers;
    public ICommands commandAddUser;
    public ICommands commandEditUser;
    public ICommands commandDeleteUser;
    public ICommands commandNewUserPage;
    public ICommands commandViewPlantsFromMenu;
    public ICommands commandLogInFromUser;
    private Property<DefaultTableModel> table;

    public EmployeeVM() {
        this.logIn = new CommandLogIn(this);
        this.commandViewUsers = new CommandViewUsers(this);
        this.commandAddUser = new CommandAddUser(this);
        this.commandEditUser = new CommandEditUser(this);
        this.commandDeleteUser = new CommandDeleteUser(this);
        this.commandNewUserPage = new CommandNewUserPage(this);
        this.commandViewPlantsFromMenu = new CommandViewPlantsFromMenu(this);
        this.commandLogInFromUser = new CommandLogInFromUser(this);
        userField = PropertyFactory.createProperty("username", this, String.class);
        passField = PropertyFactory.createProperty("Input", this, String.class);
        userID = PropertyFactory.createProperty("userID", this, String.class);
        person = PropertyFactory.createProperty("person", this, String.class);
        firstName = PropertyFactory.createProperty("firstName", this, String.class);
        lastName = PropertyFactory.createProperty("lastName", this, String.class);
        dob = PropertyFactory.createProperty("dob", this, String.class);
        address = PropertyFactory.createProperty("address", this, String.class);
        phone = PropertyFactory.createProperty("phone", this, String.class);
        email = PropertyFactory.createProperty("email", this, String.class);
        operation = PropertyFactory.createProperty("operation", this, String.class);
        role = PropertyFactory.createProperty("role", this, String.class);
        status = PropertyFactory.createProperty("status", this, String.class);
        this.table = PropertyFactory.createProperty("table", this, new javax.swing.table.DefaultTableModel());
    }

    public String getUsername() {
        return this.userField.get();
    }

    public String getPassword() {
        return this.passField.get();
    }

    public void setUserField(String userField) {
        this.userField.set(userField);
    }

    public void setPassField(String passField) {
        this.passField.set(passField);
    }

    public void setUserID(String id) {
        this.userID.set(id);
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPerson(String person) {
        this.person.set(person);
    }

    public void setDob(String dob) {
        this.dob.set(dob);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getUserID() {
        return this.userID.get();
    }

    public String getPerson() {
        return this.person.get();
    }

    public String getFirstName() {
        return this.firstName.get();
    }

    public String getLastName() {
        return this.lastName.get();
    }

    public String getDOB() {
        return this.dob.get();
    }

    public String getAddress() {
        return this.address.get();
    }

    public String getPhoneNumber() {
        return this.phone.get();
    }

    public String getEmail() {
        return this.email.get();
    }

    public String getStatus() {
        return this.status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setTableContent(DefaultTableModel table) {
        this.table.set(table);
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
}
