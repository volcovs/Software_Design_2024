package view;

import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class LogInPage extends JFrame {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton logInButton;
    private JLabel userLabel;
    private JLabel passLabel;

    public void setMessage(String message) {
        showMessageDialog(this, message);
    }
    public void disposePage() {
        this.dispose();
    }

    public JTextField getUsernameField() {
        return this.usernameField;
    }

    public JPasswordField getPasswordField() {
        return this.passwordField;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public JButton getCancelButton() {
        return this.cancelButton;
    }

    public JButton getLogInButton() {
        return this.logInButton;
    }

    public JLabel getUserLabel() {
        return this.userLabel;
    }

    public JLabel getPassLabel() {
        return this.passLabel;
    }

}
