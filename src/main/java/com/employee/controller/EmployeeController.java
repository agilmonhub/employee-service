package com.employee.controller;

import com.employee.data.entity.Employee;
import com.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registers Employee", response = HttpStatus.class)
    public ResponseEntity registerEmployee(@RequestBody Employee employee) {

        boolean registered = employeeService.registerEmployee(employee);

        if (registered)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns all employees", response = HttpStatus.class)
    public ResponseEntity findEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns employee by id", response = HttpStatus.class)
    public ResponseEntity<Employee> findEmployeeById(@PathVariable String id) {
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }
}
