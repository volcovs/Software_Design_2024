package com.example.proiect.service;

import com.example.proiect.entity.Employee;
import com.example.proiect.entity.User;
import com.example.proiect.repository.EmployeeRepository;
import com.example.proiect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private EmployeeRepository employeeRepository;
    private WhatsappService whatsappService;

    public UserService() {
        this.whatsappService = new WhatsappService();
    }

    public List<User> userList() {
        return (List<User>) this.userRepository.findAll();
    }

    public User addUser(User User) {
        return userRepository.save(User);
    }

    public User updateUser(User user) {
        Optional<User> existing = userRepository.findById(user.getUserID());
        if (existing.isPresent()) {
            existing.get().setUsername(user.getUsername());
            existing.get().setPassword(user.getPassword());
            existing.get().setPerson(user.getPerson());
            existing.get().setAdminStatus(user.getAdminStatus());
        }

        Optional<Employee> e = employeeRepository.findById(user.getPerson());
        String subject = "Login information changed";
        String message = "Dear " + e.get().getLastName() + " " + e.get().getFirstName() +
                ", \n" + "Your new login information is:\n"+ "Username: " + user.getUsername() +
                "\n" + "Password: " + user.getPassword();
        String message2 = "Your new login information is: "+ "username = " + user.getUsername() + " and password = " + user.getPassword();


        //send email about updated data
        emailService.sendEmail(e.get().getEmail(), subject, message);
        //send Whatsapp message about updated data
        whatsappService.sendMessage(e.get().getPhoneNumber(), message2);

        return userRepository.save(user);
    }

    public String deleteUser(Integer id) {
        userRepository.deleteById(id);

        if (userRepository.findById(id).isEmpty()) {
            return "Successfully deleted entry!";
        }
        else {
            return "Error!";
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> filterByStatus(String value) {
        List<User> userList = userList();

        userList.removeIf(u -> !u.getAdminStatus().equals(value));
        return userList;
    }

    public User findByUsernameAndPassword(String username, String password) {
        List<User> userList = userList();

        for(User u: userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }

        return null;
    }


}
