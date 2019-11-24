package com.employee.controller;

import com.employee.data.entity.Employee;
import com.employee.service.EmployeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * This method will register the employee.
     *
     * @param employee
     * @return
     */
    @PostMapping(path = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Registers Employee", response = Employee.class)
    @ApiResponses({
        @ApiResponse(code = 201, message = "Employee created successfully"),
        @ApiResponse(code = 500, message = "Registration failed due to internal server error")})

    public ResponseEntity registerEmployee(@RequestBody Employee employee) {

        boolean registered = employeeService.registerEmployee(employee);

        if (registered)
            return ResponseEntity.status(HttpStatus.CREATED).build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    /**
     * This method will update the employee.
     *
     * @param employee
     * @return
     */
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Update Employee", response = Employee.class)
    @ApiResponses({
        @ApiResponse(code = 202, message = "Employee updated successfully"),
        @ApiResponse(code = 304, message = "Employee not modified")})
    public ResponseEntity updateEmployee(@RequestBody Employee employee) {

        Optional<Employee> updateEmployee = employeeService.updateEmployee(employee);

        if (updateEmployee.isPresent())
            return ResponseEntity.accepted().body(updateEmployee.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    /**
     * @return Returns all employees.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns all employees", response = Employee.class, responseContainer = "List")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Get all employees")})
    public ResponseEntity findEmployees() {
        return ResponseEntity.ok().body(employeeService.findAllEmployees());
    }

    /**
     * This method will get the employee by providing id.
     *
     * @param id
     * @return Employee
     */
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns employee by id", response = Employee.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "Gets employees"),
        @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Employee> findEmployeeById(@PathVariable String id) {

        Optional<Employee> employee = employeeService.findEmployeeById(id);

        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * This method will get the employee by providing id.
     *
     * @param id
     * @return Employee
     */
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "returns employee by id", response = Employee.class)
    @ApiResponses({
        @ApiResponse(code = 202, message = "Deleted successfully"),
        @ApiResponse(code = 422, message = "Un processed entity")})
    public ResponseEntity deleteEmployeeById(@PathVariable String id) {

        boolean isDeleted = employeeService.deleteEmployee(id);

        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }
}
