package view;

import presenter.LogInPresenter;

import javax.swing.*;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class LogInPage extends JFrame implements ILogInGUI {
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton logInButton;
    private String roleNow;

    private LogInPresenter logInPresenter;


    public LogInPage(String role) {
        this.roleNow = role;
        this.logInPresenter = new LogInPresenter(this);
        setTitle("Login page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        this.logInPresenter.createMenuBar(roleNow);
        setVisible(true);

        this.logInPresenter.addCancelListener();
        this.logInPresenter.addLogInListener();
    }

    @Override
    public String accessUsername() {
        return this.usernameField.getText();
    }

    @Override
    public String accessPassword() {
        return this.passwordField.getText();
    }

    @Override
    public void setMessage(String message) {
        showMessageDialog(this, message);
    }

    @Override







































































































































































































    public void setMenuBar(JMenuBar menuBar) {
        this.setJMenuBar(menuBar);
    }

    @Override
    public void disposePage() {
        this.dispose();
    }

    @Override
    public void setCancelListener(ActionListener actionListener) {
        cancelButton.addActionListener(actionListener);
    }

    @Override
    public void setLogInListener(ActionListener actionListener) {
        logInButton.addActionListener(actionListener);
    }
}
