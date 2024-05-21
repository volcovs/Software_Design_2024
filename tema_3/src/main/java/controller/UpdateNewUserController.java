package controller;

import model.*;
import model.repository.EmployeeRepository;
import model.repository.UserRepository;
import view.UpdateNewUser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateNewUserController {
    private UpdateNewUser updateNewUser;
    private String role;
    private String status;
    private String lang;
    private Language language;

    public UpdateNewUserController(String role, String operation, String id, UserPageController toUpdate, String lang) {
        this.updateNewUser = new UpdateNewUser();
        this.role = role;
        this.lang = lang;

        switch (lang) {
            case "romanian" -> { this.language = new Romanian();  }
            case "english" -> { this.language = new English();  }
            case "french" -> { this.language = new French();  }
            case "russian" -> { this.language = new Russian(); }
            default -> { System.out.println("Not a valid language choice");}
        }

        HashMap<String, String> languageConfig = this.language.getLanguageConfig();

        if (operation.equals("update")) {
            this.updateNewUser.getTextFieldFirst().setEditable(false);
            this.updateNewUser.getTextFieldLast().setEditable(false);
            this.updateNewUser.getTextFieldAddress().setEditable(false);
            this.updateNewUser.getTextFieldPerson().setEditable(false);
            this.updateNewUser.getTextFieldDOB().setEditable(false);
            this.updateNewUser.getTextFieldPhone().setEditable(false);
            this.updateNewUser.getTextFieldEmail().setEditable(false);

            UserRepository userRepository = new UserRepository("gradina_botanica", toUpdate);
            EmployeeRepository employeeRepository = new EmployeeRepository("gradina_botanica");

            User u = userRepository.searchUser(id);
            Employee e = employeeRepository.searchEmployee(u.getPerson());

            this.updateNewUser.getTextFieldFirst().setText(e.getFirstName());
            this.updateNewUser.getTextFieldLast().setText(e.getLastName());
            this.updateNewUser.getTextFieldAddress().setText(e.getAddress());
            this.updateNewUser.getTextFieldPerson().setText(u.getPerson());
            this.updateNewUser.getTextFieldDOB().setText(e.getDateOfBirth().toString());
            this.updateNewUser.getTextFieldPhone().setText(e.getPhoneNumber());
            this.updateNewUser.getTextFieldEmail().setText(e.getEmail());
            this.updateNewUser.getTextFieldUsername().setText(u.getUsername());
            this.updateNewUser.getTextFieldPassword().setText(u.getPassword());
            this.updateNewUser.getStatusButton().setSelected(u.getAdminStatus().equals("yes"));
        }

        this.updateNewUser.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNewUser.dispose();
                UserPageController userPageController = new UserPageController(role, lang);
                userPageController.getView().setVisible(true);
            }
        });

        this.updateNewUser.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserRepository userRepository = new UserRepository("gradina_botanica", toUpdate);
                EmployeeRepository employeeRepository = new EmployeeRepository("gradina_botanica");

                if (updateNewUser.getStatusButton().isSelected()) {
                    status = "yes";
                } else {
                    status = "no";
                }

                if (operation.equals("update")) {
                        List<Object> newUser = validInformation();
                        if (newUser != null) {
                            boolean flag1 = userRepository.updateUser(id, (User) newUser.get(0));
                            boolean flag2 = employeeRepository.updateEmployee(id, (Employee) newUser.get(1));

                            if (flag1 && flag2) {
                                updateNewUser.setMessage(languageConfig.get("Success msg"));
                            } else {
                                updateNewUser.setMessage(languageConfig.get("Fail msg"));
                            }
                        }
                }
                else {
                    List<Object> user = validInformation();
                    if (user != null) {
                        boolean flag1 = userRepository.addUser((User) user.get(0));
                        boolean flag2 = employeeRepository.addEmployee((Employee) user.get(1));
                        if (flag1 && flag2) {
                            updateNewUser.setMessage(languageConfig.get("Success msg"));
                        }
                        else {
                            updateNewUser.setMessage(languageConfig.get("Success msg"));
                        }
                    }
                }

                updateNewUser.dispose();
                UserPageController userPageController = new UserPageController(role, lang);
                userPageController.getView().setVisible(true);
            }
        });

        updateNewUser.getfNameLabel().setText(languageConfig.get("First name") + ":");
        updateNewUser.getlNameLabel().setText(languageConfig.get("Last name") + ":");
        updateNewUser.getDobLabel().setText(languageConfig.get("DOB") + ":");
        updateNewUser.getAddressLabel().setText(languageConfig.get("Address") + ":");
        updateNewUser.getEmailLabel().setText(languageConfig.get("Email") + ":");
        updateNewUser.getPhoneLabel().setText(languageConfig.get("Phone") + ":");
        updateNewUser.getIdLabel().setText(languageConfig.get("Id") + ":");
        updateNewUser.getUserLabel().setText(languageConfig.get("Username") + ":");
        updateNewUser.getPassLabel().setText(languageConfig.get("Password") + ":");
        updateNewUser.getStatusLabel().setText(languageConfig.get("Status") + ":");
        updateNewUser.getSaveButton().setText(languageConfig.get("Save"));
        updateNewUser.getCancelButton().setText(languageConfig.get("Cancel"));
        updateNewUser.setTitle(languageConfig.get("Manage users"));
    }

    public UpdateNewUser getView() {
        this.updateNewUser.setContentPane(updateNewUser.getMainPanel());
        this.updateNewUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.updateNewUser.setSize(800, 600);
        return this.updateNewUser;
    }

    private List<Object> validInformation() {
        List<Object> user = new ArrayList<>();

        String person = this.updateNewUser.getTextFieldPerson().getText();
        if (person == null || person.length() == 0) {
            return null;
        }

        String first = this.updateNewUser.getTextFieldFirst().getText();
        if (first == null || first.length() == 0) {
            return null;
        }

        String last = this.updateNewUser.getTextFieldLast().getText();
        if(last == null || last.length() == 0) {
            return null;
        }

        String date = this.updateNewUser.getTextFieldDOB().getText();
        if (date == null || date.length() == 0 || LocalDate.parse(date) == null) {
            return null;
        }

        String address = this.updateNewUser.getTextFieldAddress().getText();
        if (address == null || address.length() == 0) {
            return null;
        }

        String phone = this.updateNewUser.getTextFieldPhone().getText();
        if (phone == null || phone.length() == 0) {
            phone = "";
        }

        String email = this.updateNewUser.getTextFieldEmail().getText();
        if (email == null || email.length() == 0) {
            email = "";
        }

        String username = this.updateNewUser.getTextFieldUsername().getText();
        if (username == null || username.length() == 0) {
            return null;
        }

        String pass = this.updateNewUser.getTextFieldPassword().getText();
        if (pass == null || pass.length() == 0) {
            return null;
        }

        user.add(new User("1", person, username, pass, this.status));
        user.add(new Employee(person, first, last, LocalDate.parse(date), address, phone, email));

        return user;
    }

}
