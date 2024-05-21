package presenter;

import model.Employee;
import model.User;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import view.*;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showConfirmDialog;

public class UserPresenter {
    private IUserGUI IUserGUI;
    private UserRepository userRepository;
    private EmployeeRepository employeeRepository;

    public UserPresenter(IGUI userGUI) {
        this.IUserGUI = (IUserGUI) userGUI;
        this.userRepository = new UserRepository("gradina_botanica");
        this.employeeRepository = new EmployeeRepository("gradina_botanica");
    }


    public void addUser() {
        List<Object> user = this.validInformation();
        if (user != null) {
            boolean flag1 = this.userRepository.addUser((User) user.get(0));
            boolean flag2 = this.employeeRepository.addEmployee((Employee) user.get(1));
            if (flag1 && flag2) {
                this.IUserGUI.setMessage("Success!");
                this.resetFields();
            }
            else {
                this.IUserGUI.setMessage("Failure!");
            }
        }
    }

    public void updateUser() {
        if (this.IUserGUI.getSelectedRow() != null) {
            String id = this.IUserGUI.getSelectedID();
            List<Object> newUser = this.validInformation();
            if (newUser != null) {
                boolean flag1 = this.userRepository.updateUser(id, (User) newUser.get(0));
                boolean flag2 = this.employeeRepository.updateEmployee(id, (Employee) newUser.get(1));

                if (flag1 && flag2) {
                    this.resetFields();
                    this.IUserGUI.setMessage("Success!");
                } else {
                    this.IUserGUI.setMessage("Failure!");
                }
            }
        }
    }

    public void deleteUser() {
        if (this.IUserGUI.getSelectedRow() != null) {
            String id = this.IUserGUI.getSelectedID();
            boolean flag1 = this.userRepository.deleteUser(id);
            boolean flag2 = this.employeeRepository.deleteEmployee(this.IUserGUI.getPerson());

            if (flag1 && flag2) {
                this.resetFields();
                this.IUserGUI.setMessage("Success!");
            }
            else {
                this.IUserGUI.setMessage("Failure!");
            }
        }
    }

    public List<Object> getUser() {
        List<Object> user = new ArrayList<>();

        String id = this.IUserGUI.getUserID();
        User u = this.userRepository.searchUser(id);
        String person = u.getPerson();
        Employee e = this.employeeRepository.searchEmployee(person);

        user.add(u);
        user.add(e);
        return user;
    }

    public void userList() {
        List<User> users = this.userRepository.userList();
        List<Employee> employees = this.employeeRepository.employeeList();

        if (users != null && users.size() != 0 && employees != null && employees.size() != 0) {
            List<Object> people = new ArrayList<>();
            people.addAll(users);
            people.addAll(employees);
            this.setTableList(people);
        }
        else {
            List<Object> people = new ArrayList<>();
            people.add(new User("", "", "", ""));
            people.add(new Employee("", "", "", null, "", "", ""));
            this.setTableList(people);
            this.resetFields();
            this.IUserGUI.setMessage("Empty list!");
        }
    }

    private void setTableList(List<Object> list) {
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

        JTable table = new JTable(tableData, columnNames.toArray());
        this.IUserGUI.setTableContent(table);
    }

    private void resetFields() {
        this.IUserGUI.setUserID("");
        this.IUserGUI.setPerson("");
        this.IUserGUI.setDOB("");
        this.IUserGUI.setAddress("");
        this.IUserGUI.setFirstName("");
        this.IUserGUI.setLastName("");
        this.IUserGUI.setEmail("");
        this.IUserGUI.setPhoneNumber("");
        this.IUserGUI.setUsername("");
        this.IUserGUI.setPassword("");
    }

    private List<Object> validInformation() {
        List<Object> user = new ArrayList<>();

        String userId = this.IUserGUI.getUserID();
        if (userId == null || userId.length() == 0) {
            return null;
        }

        String person = this.IUserGUI.getPerson();
        if (person == null || person.length() == 0) {
            return null;
        }

        String first = this.IUserGUI.getFirstName();
        if (first == null || first.length() == 0) {
            return null;
        }

        String last = this.IUserGUI.getLastName();
        if(last == null || last.length() == 0) {
            return null;
        }

        String date = this.IUserGUI.getDOB();
        if (date == null || date.length() == 0 || LocalDate.parse(date) == null) {
            return null;
        }

        String address = this.IUserGUI.getAdress();
        if (address == null || address.length() == 0) {
            return null;
        }

        String phone = this.IUserGUI.getPhoneNumber();
        if (phone == null || phone.length() == 0) {
            phone = "";
        }

        String email = this.IUserGUI.getEmail();
        if (email == null || email.length() == 0) {
            email = "";
        }

        String username = this.IUserGUI.getUsername();
        if (username == null || username.length() == 0) {
            return null;
        }

        String pass = this.IUserGUI.getPassword();
        if (pass == null || pass.length() == 0) {
            return null;
        }

        String status = this.IUserGUI.getStatus();

        user.add(new User(userId, person, username, pass, status));
        user.add(new Employee(person, first, last, LocalDate.parse(date), address, phone, email));

        return user;
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
                IUserGUI.disposePage();
                LogInPage logInPage = new LogInPage(role);
            }
        });

        addPlant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
                UpdateNewPlant updateNewPlant = new UpdateNewPlant("", "create", role);
            }
        });

        userManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
                UserPage users = new UserPage(role);
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
                PlantPage plantPage = new PlantPage(role);
            }
        });

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
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

        this.IUserGUI.setMenuBar(menuBar);
    }

    public void addCancelListener(String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
                UserPage userPage = new UserPage(role);
            }
        };

        IUserGUI.setCancelListener(actionListener);
    }

    public void addSaveListener(String operation, String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (operation.equals("update")) {
                    updateUser();
                }
                else {
                    addUser();
                }
                IUserGUI.disposePage();
                UserPage userPage = new UserPage(role);
            }
        };

        IUserGUI.setSaveListener(actionListener);
    }

    public void addEditListener(String role) {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IUserGUI.disposePage();
                UpdateNewUser updateNewUser = new UpdateNewUser(IUserGUI.getSelectedID(), "update", role);
            }
        };

        IUserGUI.setEditListener(actionListener);
    }

    public void addTableListener() {
        MouseAdapter actionListener =
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        IUserGUI.setSelectedRow(e);

                        if (e.getClickCount() == 2) {
                            //dublu-click = trebuie stearsa intrarea selectata din tabel
                            int option = showConfirmDialog(IUserGUI.getMainPanel(), "Confirmati stergerea intrarii?");
                            if (option == JOptionPane.YES_OPTION) {
                                deleteUser();
                                userList();
                            }
                        }
                    }
                };

        IUserGUI.setTableListener(actionListener);
    }

    public void initializeFields(String idToUpdate) {
        List<Object> user = getUser();
        Employee employee = (Employee) user.get(1);
        User user1 = (User) user.get(0);

        if (idToUpdate != null && !idToUpdate.equals("")) {
            IUserGUI.setFirstName(employee.getFirstName());
            IUserGUI.setLastName(employee.getLastName());
            IUserGUI.setDOB(employee.getDateOfBirth().toString());
            IUserGUI.setAddress(employee.getAddress());
            IUserGUI.setPhoneNumber(employee.getPhoneNumber());
            IUserGUI.setEmail(employee.getEmail());
            IUserGUI.setUsername(user1.getUsername());
            IUserGUI.setPassword(user1.getPassword());
            IUserGUI.setPerson(user1.getPerson());
            IUserGUI.setStatus(user1.getAdminStatus());
        }
    }

}
