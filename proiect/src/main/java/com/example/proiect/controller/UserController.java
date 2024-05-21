package com.example.proiect.controller;

import com.example.proiect.entity.User;
import com.example.proiect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/list")
    @ResponseBody
    public List<User> employeeList() {
        return this.userService.userList();
    }

    @GetMapping("/findById")
    @ResponseBody
    public User findById(@RequestParam Integer id) {
        return this.userService.findById(id);
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<User> filterUsers(@RequestParam String status) {
        return this.userService.filterByStatus(status);
    }

    @GetMapping("/login")
    @ResponseBody
    public User findUser(@RequestParam String username, String password) {
        return this.userService.findByUsernameAndPassword(username, password);
    }

    @PostMapping("/add")
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }

    @PutMapping("/update")
    @ResponseBody
    public User updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteById(@RequestParam Integer id) {
        return this.userService.deleteUser(id);
    }
}
