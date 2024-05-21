package com.example.proiect.entity;

import com.example.proiect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
public class UserProxy extends AbstractUser {
    private User realSubject;
    private String username;
    private String password;
    @Autowired
    private UserService userService;


    public UserProxy(String username, String password) {
        this.password = password;
        this.username = username;
    }

    @Override
    public Integer getUserID() {
        if (realSubject == null) {
            realSubject = userService.findByUsernameAndPassword(username, password);
        }

        return realSubject.getUserID();
    }

    @Override
    public Integer getPerson() {
        if (realSubject == null) {
            realSubject = userService.findByUsernameAndPassword(username, password);
        }

        return realSubject.getPerson();
    }

    @Override
    public String getAdminStatus() {
        if (realSubject == null) {
            realSubject = userService.findByUsernameAndPassword(username, password);
        }

        return realSubject.getAdminStatus();
    }
}

