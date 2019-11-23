package com.employee.service;

import com.employee.data.entity.Employee;
import com.employee.data.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean registerEmployee(Employee employee) {
        return Optional.ofNullable(employeeRepository.save(employee)).isPresent();
    }

    public List<Employee> findAllEmployees() {

        Iterable<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        if (Optional.ofNullable(allEmployees).isPresent()) {
            allEmployees.forEach(employees::add);
        }
        return employees;
    }

    public Employee findEmployeeById(String id) {

        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        }
        return null;
    }
}
