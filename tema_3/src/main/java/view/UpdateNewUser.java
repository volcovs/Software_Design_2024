package view;


import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UpdateNewUser extends JFrame {
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
    private JLabel fNameLabel;
    private JLabel lNameLabel;
    private JLabel idLabel;
    private JLabel dobLabel;
    private JLabel addressLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel statusLabel;

    public UpdateNewUser() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JRadioButton getStatusButton() {
        return this.statusButton;
    }

    public JButton getSaveButton() {
        return this.saveButton;
    }

    public JButton getCancelButton() {
        return this.cancelButton;
    }

    public JTextField getTextFieldUsername() {
        return this.textFieldUsername;
    }

    public JTextField getTextFieldPassword() {
        return this.textFieldPassword;
    }

    public JTextArea getTextFieldFirst() {
        return this.textFieldFirst;
    }

    public JTextArea getTextFieldLast() {
        return this.textFieldLast;
    }

    public JTextArea getTextFieldDOB() {
        return this.textFieldDOB;
    }

    public JTextArea getTextFieldAddress() {
        return this.textFieldAddress;
    }

    public JTextArea getTextFieldPhone() {
        return this.textFieldPhone;
    }

    public JTextArea getTextFieldEmail() {
        return this.textFieldEmail;
    }

    public JTextArea getTextFieldPerson() {
        return this.textFieldPerson;
    }

    public JLabel getfNameLabel() {
        return this.fNameLabel;
    }

    public JLabel getlNameLabel() {
        return this.lNameLabel;
    }

    public JLabel getUserLabel() {
        return this.userLabel;
    }

    public JLabel getIdLabel() {
        return this.idLabel;
    }

    public JLabel getPassLabel() {
        return this.passLabel;
    }

    public JLabel getDobLabel() {
        return this.dobLabel;
    }

    public JLabel getAddressLabel() {
        return this.addressLabel;
    }

    public JLabel getPhoneLabel() {
        return this.phoneLabel;
    }

    public JLabel getEmailLabel() {
        return this.emailLabel;
    }

    public JLabel getStatusLabel() {
        return this.statusLabel;
    }

    public void setMessage(String s) {
        showMessageDialog(this, s);
    }
}
