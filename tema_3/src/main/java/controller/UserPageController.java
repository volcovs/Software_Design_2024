package controller;

import model.*;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import view.UserPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.util.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class UserPageController implements Observer {
    private UserPage userPage;
    private String role;
    private String rowToEdit;

    private UserPageController aux;
    private String lang;
    private Language language;

    public UserPageController(String role, String lang) {
        this.role = role;
        this.userPage = new UserPage();
        this.aux = this;
        this.getList();
        this.lang = lang;


        switch (lang) {
            case "romanian" -> { this.language = new Romanian();  }
            case "english" -> { this.language = new English();  }
            case "french" -> { this.language = new French();  }
            case "russian" -> { this.language = new Russian(); }
            default -> { System.out.println("Not a valid language choice");}
        }

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        this.createMenu();
        this.userPage.getEditButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateNewUserController updateNewUserController = new UpdateNewUserController(role, "update", rowToEdit, aux, lang);
                updateNewUserController.getView().setVisible(true);
                userPage.dispose();
            }
        });

        this.userPage.getTableUsers().setDefaultEditor(Object.class, null);
        this.userPage.getTableUsers().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                rowToEdit = userPage.getTableUsers().getValueAt(userPage.getTableUsers().getSelectedRow(), 0).toString();

                if (e.getClickCount() == 2 && role.equals("administrator")) {
                    //dublu-click = trebuie stearsa intrarea selectata din tabel
                    showMessageDialog(userPage.getMainPanel(), "Deleting selected user...");
                    UserRepository userRepository = new UserRepository("gradina_botanica", aux);

                    if (userRepository.deleteUser(rowToEdit)) {
                        showMessageDialog(userPage.getMainPanel(), languageConfig.get("Success msg"));
                    } else {
                        showMessageDialog(userPage.getMainPanel(), languageConfig.get("Fail msg"));
                    }

                    getList();
                }

            }
        });

        this.userPage.getDoarAdministratoriRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userPage.getDoarAdministratoriRadioButton().isSelected()) {
                    UserRepository userRepository = new UserRepository("gradina_botanica", aux);
                    List<Object> people = userRepository.onlyAdminList();

                    listToTable(people);
                }
                else {
                    getList();
                }
            }
        });

        this.userPage.getDoarAngajatiRadioButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userPage.getDoarAngajatiRadioButton().isSelected()) {
                    UserRepository userRepository = new UserRepository("gradina_botanica", aux);
                    List<Object> people = userRepository.onlyEmployeeList();

                    listToTable(people);
                }
                else {
                    getList();
                }
            }
        });

        userPage.getDoarAngajatiRadioButton().setText(languageConfig.get("Only regular"));
        userPage.getDoarAdministratoriRadioButton().setText(languageConfig.get("Only admin"));
        userPage.getEditButton().setText(languageConfig.get("Edit"));
        userPage.setTitle(languageConfig.get("Manage users"));
    }


    public UserPage getView() {
        this.userPage.setContentPane(this.userPage.getMainPanel());
        this.userPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.userPage.setSize(800, 600);

        return this.userPage;
    }

    @Override
    public void update(Observable o, Object arg) {
        UserRepository userRepository = (UserRepository) o;
        EmployeeRepository employeeRepository = new EmployeeRepository("gradina_botanica");

        List<User> users = userRepository.userList();
        List<Employee> employees = employeeRepository.employeeList();

        if (users != null && users.size() != 0 && employees != null && employees.size() != 0) {
            List<Object> people = new ArrayList<>();
            people.addAll(users);
            people.addAll(employees);
            listToTable(people);
        }
        else {
            List<Object> people = new ArrayList<>();
            people.add(new User("", "", "", ""));
            people.add(new Employee("", "", "", null, "", "", ""));
            listToTable(people);
        }
    }

    private void getList() {
        UserRepository userRepository = new UserRepository("gradina_botanica", aux);
        EmployeeRepository employeeRepository = new EmployeeRepository("gradina_botanica");

        List<User> users = userRepository.userList();
        List<Employee> employees = employeeRepository.employeeList();

        if (users != null && users.size() != 0 && employees != null && employees.size() != 0) {
            List<Object> people = new ArrayList<>();
            people.addAll(users);
            people.addAll(employees);
            listToTable(people);
        }
        else {
            List<Object> people = new ArrayList<>();
            people.add(new User("", "", "", ""));
            people.add(new Employee("", "", "", null, "", "", ""));
            listToTable(people);
        }
    }

    private void listToTable(List<Object> list) {
        List<User> users = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();

        for (int k = 0; k < list.size(); k++) {
            if (k < (list.size()/2)) {
                users.add((User) list.get(k));
            }
            else {
                employees.add((Employee) list.get(k));
            }
        }

        List<String> columnNames = new ArrayList<String>();
        int iter = 0;

        for (Field field : users.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        for (Field field : employees.get(0).getClass().getSuperclass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                columnNames.add(field.getName());
                iter++;

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        Object[][] tableData = new Object[users.size()*2][iter*2];
        int i = 0;
        int numOfFields = 0;
        for (User obj : users) {
            int j = 0;
            for (Field field : obj.getClass().getDeclaredFields()) {
                numOfFields++;
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        i = 0;
        for (Employee obj : employees) {
            int j = 5;
            for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    tableData[i][j++] = field.get(obj);
                } catch (IllegalAccessException exc) {
                    System.out.println("Access error");
                }
            }
            i++;
        }

        DefaultTableModel table = new DefaultTableModel(tableData, columnNames.toArray());
        this.userPage.getTableUsers().setModel(table);
    }


    private void createMenu() {
        JMenuItem addPlant;
        JMenuItem logIn;
        JMenuItem userManagement;
        JMenuItem plants;
        JMenuItem addUser;
        JMenu menu;
        JMenu langM;
        JMenuItem romana;
        JMenuItem english;
        JMenuItem francais;
        JMenuItem russian;

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu(languageConfig.get("Menu"));
        langM = new JMenu(languageConfig.get("Language"));
        menu.setMnemonic(KeyEvent.VK_A);
        langM.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
        menuBar.add(langM);

        logIn = new JMenuItem(languageConfig.get("Log in"));
        logIn.setMnemonic(KeyEvent.VK_D);
        addPlant = new JMenuItem(languageConfig.get("Add plant"));
        addPlant.setMnemonic(KeyEvent.VK_D);
        userManagement = new JMenuItem(languageConfig.get("Manage users"));
        userManagement.setMnemonic(KeyEvent.VK_D);
        plants = new JMenuItem(languageConfig.get("View plants"));
        plants.setMnemonic(KeyEvent.VK_D);
        addUser = new JMenuItem(languageConfig.get("Add user"));
        addUser.setMnemonic(KeyEvent.VK_D);
        romana = new JMenuItem("Română");
        romana.setMnemonic(KeyEvent.VK_D);
        english = new JMenuItem("English");
        english.setMnemonic(KeyEvent.VK_D);
        francais = new JMenuItem("Francais");
        francais.setMnemonic(KeyEvent.VK_D);
        russian = new JMenuItem("Русский");
        russian.setMnemonic(KeyEvent.VK_D);

        logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                LogInPageController logInPageController = new LogInPageController(role, lang);
                logInPageController.getView().setVisible(true);
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                PlantPageController plantPageController = new PlantPageController(role, lang);
                plantPageController.getView().setVisible(true);
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                UpdateNewUserController updateNewUserController = new UpdateNewUserController(role, "create", rowToEdit, aux, lang);
                updateNewUserController.getView().setVisible(true);
            }
        });

        romana.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                UserPageController pageController = new UserPageController(role, "romanian");
                pageController.getView().setVisible(true);
            }
        });

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                UserPageController pageController = new UserPageController(role, "english");
                pageController.getView().setVisible(true);
            }
        });

        francais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                UserPageController pageController = new UserPageController(role, "french");
                pageController.getView().setVisible(true);
            }
        });

        russian.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userPage.dispose();
                UserPageController pageController = new UserPageController(role, "russian");
                pageController.getView().setVisible(true);
            }
        });


        menu.add(logIn);
        menu.add(plants);

        if (role.equals("administrator")) {
            menu.add(userManagement);
            menu.add(addUser);
        } else if (role.equals("employee")) {
            menu.add(addPlant);
        }

        langM.add(romana);
        langM.add(english);
        langM.add(francais);
        langM.add(russian);

        this.userPage.setJMenuBar(menuBar);
    }

}
