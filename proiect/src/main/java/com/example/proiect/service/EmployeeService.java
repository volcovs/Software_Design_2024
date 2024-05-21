package com.example.proiect.service;

import com.example.proiect.entity.Employee;
import com.example.proiect.entity.User;
import com.example.proiect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.proiect.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Employee> employeeList() {
        return (List<Employee>) this.employeeRepository.findAll();
    }

    public Employee addEmployee(Employee Employee) {
        return employeeRepository.save(Employee);
    }

    public Employee updateEmployee(Employee employee) {
        Optional<Employee> existing = employeeRepository.findById(employee.getId());
        if (existing.isPresent()) {
            existing.get().setFirstName(employee.getFirstName());
            existing.get().setLastName(employee.getLastName());
            existing.get().setDateOfBirth(employee.getDateOfBirth());
            existing.get().setAddress(employee.getAddress());
            existing.get().setEmail(employee.getEmail());
            existing.get().setPhoneNumber(employee.getPhoneNumber());
        }

        return employeeRepository.save(employee);
    }

    public String deleteEmployee(Integer id) {
        employeeRepository.deleteById(id);

        if (employeeRepository.findById(id).isEmpty()) {
            return "Successfully deleted entry!";
        }
        else {
            return "Error!";
        }
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public Employee findByUser(Integer id) {
        User u = userRepository.findById(id).get();
        return employeeRepository.findById(u.getPerson()).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public String deleteEmployeeByUser(Integer id) {
        User u = userRepository.findById(id).get();
        employeeRepository.deleteById(u.getPerson());
        if (employeeRepository.findById(id).isEmpty()) {
            return "Successfully deleted entry!";
        } else {
            return "Error!";
        }
    }

    public List<Employee> filterByStatus(String status) {
        List<User> userList = (List<User>) userRepository.findAll();
        userList.removeIf(u -> !u.getAdminStatus().equals(status));
        List<Integer> people = new ArrayList<>();
        for (User u: userList) {
            people.add(u.getPerson());
        }

        List<Employee> employeeList = employeeList();
        employeeList.removeIf(e -> !people.contains(e.getId()));
        return employeeList;
    }
}
