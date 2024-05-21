package view;

import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingType;
import viewmodel.EmployeeVM;

import javax.swing.*;
import java.awt.event.*;

import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class UserPage extends JFrame {
    @Bind(value = "text", target = "role.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField roleField;
    @Bind(value = "text", target = "operation.value", type = BindingType.BI_DIRECTIONAL)
    private JTextField operationField;
    private JPanel mainPanel;
    @Bind(value = "model", target = "table.value", type = BindingType.BI_DIRECTIONAL)
    private JTable tableUsers;
    private JButton editButton;
    private EmployeeVM userVM;

    public UserPage(String role) {
        this.roleField = new JTextField();
        this.operationField = new JTextField();
        this.roleField.setText(role);
        setTitle("User administration page");

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        this.createMenu();

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationField.setText("update");
                roleField.setText(role);
                userVM.commandNewUserPage.execute();
            }
        });

        tableUsers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                userVM.setUserID(tableUsers.getValueAt(tableUsers.getSelectedRow(), 0).toString());

                if (e.getClickCount() == 2 && role.equals("administrator")) {
                    //dublu-click = trebuie stearsa intrarea selectata din tabel
                    showMessageDialog(mainPanel, "Deleting selected user...");
                    if (userVM.commandDeleteUser.execute()) {
                        showMessageDialog(mainPanel, "User deleted successfully!");
                    }
                    else {
                        showMessageDialog(mainPanel, "Error!");
                    }
                    userVM.commandViewUsers.execute();
                }
            }
        });

        setVisible(true);

        userVM = new EmployeeVM();
        try {
            Binder.bind(this, userVM);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        userVM.commandViewUsers.execute();
    }

    private void createMenu() {
        JMenuItem addPlant;
        JMenuItem logIn;
        JMenuItem userManagement;
        JMenuItem plants;
        JMenuItem addUser;
        JMenu menu;

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        logIn = new JMenuItem("Autentificare");
        logIn.setMnemonic(KeyEvent.VK_D);
        addPlant = new JMenuItem("Adaugare planta");
        addPlant.setMnemonic(KeyEvent.VK_D);
        userManagement = new JMenuItem("Administrare utilizatori");
        userManagement.setMnemonic(KeyEvent.VK_D);
        plants = new JMenuItem("Vizualizare plante");
        plants.setMnemonic(KeyEvent.VK_D);
        addUser = new JMenuItem("Adaugare utilizator");
        addUser.setMnemonic(KeyEvent.VK_D);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userVM.commandLogInFromUser.execute();
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                userVM.commandViewPlantsFromMenu.execute();
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                operationField.setText("create");
                userVM.commandNewUserPage.execute();
            }
        });

        menu.add(logIn);
        menu.add(plants);

        if (roleField.getText().equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        }
        else if (roleField.getText().equals("employee")) {
            menu.add(addPlant);
        }

        this.setJMenuBar(menuBar);
    }


}
