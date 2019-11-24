package com.employee.service;

import com.employee.data.entity.Employee;
import com.employee.data.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    employees.sort(Comparator.comparing(Employee::getFirstName));
    return employees;
  }

  public Optional<Employee> findEmployeeById(String id) {
    return employeeRepository.findById(id);
  }

  public Optional<Employee> updateEmployee(Employee employee) {
    Optional<Employee> employee1 = employeeRepository.findById(employee.getId());
    if (employee1.isPresent()) {
      return Optional.ofNullable(employeeRepository.save(employee));
    }
    return Optional.empty();
  }

  public boolean deleteEmployee(String id) {

    employeeRepository.deleteById(id);

    return !employeeRepository.existsById(id);
  }
}
