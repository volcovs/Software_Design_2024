package presenter;

import model.User;
import model.repository.UserRepository;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class LogInPresenter {
    private ILogInGUI ILogInGUI;
    private UserRepository userRepository;

    public LogInPresenter(ILogInGUI ILogInGUI) {
        this.ILogInGUI = ILogInGUI;
        this.userRepository = new UserRepository("gradina_botanica");
    }

    public void createMenuBar(String role) {
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
                ILogInGUI.disposePage();
                LogInPage logInPage = new LogInPage(role);
            }
        });

        addPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ILogInGUI.disposePage();
                UpdateNewPlant updateNewPlant = new UpdateNewPlant("", "create", role);
            }
        });

        userManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ILogInGUI.disposePage();
                UserPage users = new UserPage(role);
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ILogInGUI.disposePage();
                PlantPage plantPage = new PlantPage(role);
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ILogInGUI.disposePage();
                UpdateNewUser updateNewUser = new UpdateNewUser("", "create", role);
            }
        });

        menu.add(logIn);
        menu.add(plants);
        if (role.equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        }
        else if (role.equals("employee")) {
            menu.add(addPlant);
        }

        this.ILogInGUI.setMenuBar(menuBar);
    }

    public void addCancelListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ILogInGUI.disposePage();
                PlantPage plantPage = new PlantPage("visitor");
            }
        };

        this.ILogInGUI.setCancelListener(actionListener);
    }

    public void addLogInListener() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = ILogInGUI.accessUsername();
                String password = ILogInGUI.accessPassword();

                User u = userRepository.searchUserLogIn(username, password);
                if (u != null) {
                    if (u.getAdminStatus().equals("yes")) {
                        //successful login - admin role
                        ILogInGUI.disposePage();
                        PlantPage plantPage = new PlantPage("administrator");
                    }
                    else {
                        //successful login
                        ILogInGUI.disposePage();
                        PlantPage plantPage = new PlantPage("employee");
                    }
                }
                else {
                    ILogInGUI.setMessage("Error while during log in! Try different credentials");
                }
            }
        };

        this.ILogInGUI.setLogInListener(actionListener);
    }
}

