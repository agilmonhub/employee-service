package com.employee.controller;

import com.employee.data.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

  @ModelAttribute("employee")
  public Employee employee() {
    return new Employee();
  }

  @GetMapping("/")
  public String root() {
    return "index";
  }

  @GetMapping("/registration")
  public String registration() {
    return "registration";
  }

  @GetMapping("/employee")
  public String employeeList() {
    return "employee";
  }
}
