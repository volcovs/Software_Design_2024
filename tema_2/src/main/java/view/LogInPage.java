package view;
import net.sds.mvvm.bindings.Bind;
import viewmodel.EmployeeVM;

import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;

public class LogInPage extends JFrame {
    private JPanel mainPanel;
    @Bind(value = "text", target = "userField.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField usernameField;
    @Bind(value = "text", target = "passField.value", type = BindingType.BI_DIRECTIONAL)
    private JPasswordField passwordField;
    private JButton cancelButton;
    private JButton logInButton;
    @Bind(value = "text", target = "role.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField role;
    private EmployeeVM employeeVM;

    public LogInPage(String role) {
        this.role = new JTextField();
        this.role.setText(role);
        setTitle("Login page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!employeeVM.logIn.execute()) {
                    setMessage("Incorrect credentials!");
                }
                else {
                    disposePage();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");

                dispose();
                employeeVM.commandViewPlantsFromMenu.execute();
            }
        });

        employeeVM = new EmployeeVM();
        try {
            Binder.bind(this, employeeVM);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void setMessage(String message) {
        showMessageDialog(this, message);
    }
    public void disposePage() {
        this.dispose();
    }
}
