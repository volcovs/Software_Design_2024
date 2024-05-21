package com.example.proiect.controller;


import com.example.proiect.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.proiect.service.EmployeeService;

import java.util.List;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/list")
    @ResponseBody
    public List<Employee> employeeList() {
        return this.employeeService.employeeList();
    }

    @GetMapping("/findById")
    @ResponseBody
    public Employee findById(@RequestParam Integer id) {
        return this.employeeService.findById(id);
    }

    @GetMapping("/findByUser")
    @ResponseBody
    public Employee findByUser(@RequestParam Integer id) {
        return this.employeeService.findByUser(id);
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<Employee> filterUsers(@RequestParam String status) {
        return this.employeeService.filterByStatus(status);
    }

    @PostMapping("/add")
    @ResponseBody
    public Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeService.addEmployee(employee);
    }

    @PutMapping("/update")
    @ResponseBody
    public Employee updateEmployee(@RequestBody Employee employee) {
        return this.employeeService.updateEmployee(employee);
    }

    @DeleteMapping("/deleteById")
    @ResponseBody
    public String deleteById(@RequestParam Integer id) {
        return this.employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/deleteByUser")
    @ResponseBody
    public String deleteByUser(@RequestParam Integer id) {
        return this.employeeService.deleteEmployeeByUser(id);
    }
}
