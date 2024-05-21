package view;

import presenter.UserPresenter;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewUser extends JFrame implements IUserGUI {
    private JPanel mainPanel;
    private JButton saveButton;
    private JButton cancelButton;
    private JTextArea textFieldFirst;
    private JTextArea textFieldLast;
    private JTextArea textFieldDOB;
    private JTextArea textFieldAddress;
    private JTextArea textFieldPhone;
    private JTextArea textFieldEmail;
    private JTextField textFieldUsername;
    private JTextField textFieldPassword;
    private JTextArea textFieldPerson;
    private JRadioButton statusButton;
    private String idToUpdate;
    private String operation;
    private UserPresenter userPresenter;
    private String role;

    public UpdateNewUser(String idToUpdate, String operation, String role) {
        this.role = role;
        setTitle("User administration page");

        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        this.idToUpdate = idToUpdate;
        this.operation = operation;
        this.userPresenter = new UserPresenter(this);

        userPresenter.initializeFields(idToUpdate);

        if (operation.equals("update")) {
            this.textFieldFirst.setEditable(false);
            this.textFieldLast.setEditable(false);
            this.textFieldAddress.setEditable(false);
            this.textFieldPerson.setEditable(false);
            this.textFieldDOB.setEditable(false);
            this.textFieldPhone.setEditable(false);
            this.textFieldEmail.setEditable(false);
        }

        userPresenter.addCancelListener(role);
        userPresenter.addSaveListener(operation, role);
        userPresenter.createMenuBar(role);
        setVisible(true);
    }

    @Override
    public void setMessage(String message) {
        showMessageDialog(mainPanel, message);
    }

    @Override
    public String getUserID() {
        if (operation.equals("update")) {
            return this.idToUpdate;
        }
        else {
            return "1";
        }
    }

    @Override
    public void setUserID(String id) {
        this.textFieldPerson.setText(id);
    }

    @Override
    public String getPerson() {
        return this.textFieldPerson.getText();
    }

    @Override
    public void setPerson(String personID) {
        this.textFieldPerson.setText(personID);
    }

    @Override
    public String getFirstName() {
        return this.textFieldFirst.getText();
    }

    @Override
    public void setFirstName(String name) {
        this.textFieldFirst.setText(name);
    }

    @Override
    public String getLastName() {
        return this.textFieldLast.getText();
    }

    @Override
    public void setLastName(String lastName) {
        this.textFieldLast.setText(lastName);
    }

    @Override
    public void setStatus(String status) {
        this.statusButton.setSelected(status.equals("yes"));
    }

    @Override
    public String getStatus() {
        if (statusButton.isSelected()) {
            return "yes";
        } else {
            return "no";
        }
    }

    @Override
    public String getDOB() {
        return this.textFieldDOB.getText();
    }

    @Override
    public void setDOB(String dateOfBirth) {
        this.textFieldDOB.setText(dateOfBirth);
    }

    @Override
    public String getAdress() {
        return this.textFieldAddress.getText();
    }

    @Override
    public void setAddress(String address) {
        this.textFieldAddress.setText(address);
    }

    @Override
    public String getPhoneNumber() {
        return this.textFieldPhone.getText();
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.textFieldPhone.setText(phoneNumber);
    }

    @Override
    public String getEmail() {
        return this.textFieldEmail.getText();
    }

    @Override
    public void setEmail(String email) {
        this.textFieldEmail.setText(email);
    }

    @Override
    public String getUsername() {
        return this.textFieldUsername.getText();
    }

    @Override
    public void setUsername(String username) {
        this.textFieldUsername.setText(username);
    }

    @Override
    public String getPassword() {
        return this.textFieldPassword.getText();
    }

    @Override
    public void setPassword(String password) {
        this.textFieldPassword.setText(password);
    }

    @Override
    public Integer getSelectedRow() {
        return Integer.parseInt(textFieldPerson.getText());
    }

    @Override
    public void setSelectedRow(MouseEvent e) {
        e.getPoint();
    }

    @Override
    public String getSelectedID() {
        return idToUpdate;
    }

    @Override
    public void setTableContent(JTable table) {
        this.textFieldUsername.setText("Some error occured");
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public void setTableListener(MouseAdapter adapter) {
        saveButton.addActionListener((ActionListener) adapter);
    }

    @Override
    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    @Override
    public void setCancelListener(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }

    @Override
    public void setSaveListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    @Override
    public void setEditListener(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
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
