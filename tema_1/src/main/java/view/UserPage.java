package view;

import presenter.UserPresenter;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class UserPage extends JFrame implements IUserGUI {
    private JPanel mainPanel;
    private JTable tableUsers;
    private JButton editButton;
    private UserPresenter presenter;
    private int row;
    private String role;

    public UserPage(String role) {
        this.role = role;
        presenter = new UserPresenter(this);
        setTitle("User administration page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        this.presenter.userList();
        this.presenter.createMenuBar(this.role);
        this.presenter.addEditListener(this.role);
        this.presenter.addTableListener();

        setVisible(true);
    }

    @Override
    public String getUserID() {
        return (String) tableUsers.getValueAt(row, 0);
    }

    @Override
    public void setUserID(String id) {
        tableUsers.editCellAt(row, 0);
    }

    @Override
    public String getPerson() {
        //return the id of the selected person
        return (String) tableUsers.getValueAt(row, 1);
    }

    @Override
    public void setPerson(String personID) {
        tableUsers.editCellAt(row, 1);
    }

    @Override
    public String getFirstName() {
        return (String) tableUsers.getValueAt(row, 2);
    }

    @Override
    public void setFirstName(String name) {
        tableUsers.editCellAt(row, 2);
    }

    @Override
    public String getLastName() {
        return (String) tableUsers.getValueAt(row, 3);
    }

    @Override
    public void setLastName(String lastName) {
        tableUsers.editCellAt(row, 3);
    }

    @Override
    public void setStatus(String status) {
        tableUsers.editCellAt(row, 4);
    }

    @Override
    public String getStatus() {
        return (String) tableUsers.getValueAt(row, 4);
    }

    @Override
    public String getDOB() {
        return (String) tableUsers.getValueAt(row, 5);
    }

    @Override
    public void setDOB(String dateOfBirth) {
        tableUsers.editCellAt(row, 5);
    }

    @Override
    public String getAdress() {
        return (String) tableUsers.getValueAt(row, 6);
    }

    @Override
    public void setAddress(String address) {
        tableUsers.editCellAt(row, 6);
    }

    @Override
    public String getPhoneNumber() {
        return (String) tableUsers.getValueAt(row, 7);
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        tableUsers.editCellAt(row, 7);
    }

    @Override
    public String getEmail() {
        return (String) tableUsers.getValueAt(row, 8);
    }

    @Override
    public void setEmail(String email) {
        tableUsers.editCellAt(row, 8);
    }

    @Override
    public String getUsername() {
        return (String) tableUsers.getValueAt(row, 9);
    }

    @Override
    public void setUsername(String username) {
        tableUsers.editCellAt(row, 9);
    }

    @Override
    public String getPassword() {
        return (String) tableUsers.getValueAt(row, 10);
    }

    @Override
    public void setPassword(String password) {
        tableUsers.editCellAt(row, 10);
    }

    @Override
    public Integer getSelectedRow() {
        return row;
    }

    @Override
    public void setSelectedRow(MouseEvent e) {
        row = tableUsers.rowAtPoint(e.getPoint());
    }

    @Override
    public String getSelectedID() {
        return tableUsers.getValueAt(row, 0).toString();
    }

    @Override
    public void setTableContent(JTable table) {
        tableUsers.setModel(table.getModel());
        tableUsers.setDefaultEditor(Object.class, null);
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setTableListener(MouseAdapter adapter) {
        tableUsers.addMouseListener(adapter);
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void setCancelListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    @Override
    public void setSaveListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    @Override
    public void setEditListener(ActionListener actionListener) {
        editButton.addActionListener(actionListener);
    }

    @Override
    public void setMessage(String message) {
        showMessageDialog(mainPanel, message);
    }

    @Override
    public void setMenuBar(JMenuBar menuBar) {
        this.setJMenuBar(menuBar);
    }

    @Override
    public void disposePage() {
        this.dispose();
    }
}
