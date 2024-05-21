package controller;

import model.*;
import model.repository.UserRepository;
import view.LogInPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class LogInPageController {
    private LogInPage logInPage;
    private String role;
    private String lang;
    private Language language;

    public LogInPageController(String role, String lang) {
        this.logInPage = new LogInPage();
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

        logInPage.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInPage.getUsernameField().setText("");
                logInPage.getPasswordField().setText("");

                logInPage.dispose();
                PlantPageController plantPageController = new PlantPageController(role, lang);
                plantPageController.getView().setVisible(true);
            }
        });

        logInPage.getLogInButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processLogIn();
            }
        });

        logInPage.getLogInButton().setText(languageConfig.get("Log in"));
        logInPage.getCancelButton().setText(languageConfig.get("Cancel"));
        logInPage.getUserLabel().setText(languageConfig.get("Username") + ":");
        logInPage.getPassLabel().setText(languageConfig.get("Password") + ":");
        logInPage.setTitle(languageConfig.get("Log in page"));
    }


    public LogInPage getView() {
        this.logInPage.setContentPane(logInPage.getMainPanel());

        this.logInPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.logInPage.setSize(800, 600);

        return this.logInPage;
    }

    private void processLogIn() {
        HashMap<String, String> languageConfig = this.language.getLanguageConfig();
        UserRepository userRepository = new UserRepository("gradina_botanica", null);

        String username = this.logInPage.getUsernameField().getText();
        String password = this.logInPage.getPasswordField().getText();

        User u = userRepository.searchUserLogIn(username, password);
        if (u != null) {
            if (u.getAdminStatus().equals("yes")) {
                //successful login - admin role
                logInPage.disposePage();
                PlantPageController plantPageController = new PlantPageController("administrator", lang);
                plantPageController.getView().setVisible(true);
            }
            else {
                //successful login
                logInPage.disposePage();
                PlantPageController plantPageController = new PlantPageController("employee", lang);
                plantPageController.getView().setVisible(true);
            }
        }
        else {
            logInPage.setMessage(languageConfig.get("Login error"));
        }
    }

}
