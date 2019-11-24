package com.employee.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.employee.data.entity.Employee;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerTest {

  @MockBean
  private EmployeeService employeeService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private ObjectMapper mapper;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    mapper = new ObjectMapper();
  }

  @Test
  public void test_register_employee() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    String employeeString = mapper
        .writeValueAsString(employee);

    when(employeeService.registerEmployee(any())).thenReturn(true);
    //when
    MockHttpServletResponse response = mockMvc
        .perform(post("/api/employee/register").content(employeeString)
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    verify(employeeService, times(1)).registerEmployee(any());
  }

  @Test
  public void test_register_employee_Exception() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    String employeeString = mapper
        .writeValueAsString(employee);

    when(employeeService.registerEmployee(any())).thenReturn(false);
    //when
    MockHttpServletResponse response = mockMvc
        .perform(post("/api/employee/register").content(employeeString)
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    verify(employeeService, times(1)).registerEmployee(any());
  }

  @Test
  public void test_update_employee() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    String employeeString = mapper
        .writeValueAsString(employee);

    when(employeeService.updateEmployee(any())).thenReturn(Optional.of(employee));
    //when
    MockHttpServletResponse response = mockMvc
        .perform(put("/api/employee/update").content(employeeString)
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    assertThat(response.getContentAsString()).isNotBlank();
    verify(employeeService, times(1)).updateEmployee(any());
  }

  @Test
  public void test_update_employee_Exception() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    String employeeString = mapper
        .writeValueAsString(employee);

    when(employeeService.updateEmployee(any())).thenReturn(Optional.empty());
    //when
    MockHttpServletResponse response = mockMvc
        .perform(put("/api/employee/update").content(employeeString)
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();

    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_MODIFIED.value());
    assertThat(response.getContentAsString()).isBlank();
    verify(employeeService, times(1)).updateEmployee(any());
  }

  @Test
  public void test_findEmployees() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given

    when(employeeService.findAllEmployees()).thenReturn(Arrays.asList(employee));
    //when
    MockHttpServletResponse response = mockMvc
        .perform(get("/api/employee")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isNotBlank();
  }

  @Test
  public void test_findEmployees_No_result() throws Exception {

    //given

    when(employeeService.findAllEmployees()).thenReturn(null);
    //when
    MockHttpServletResponse response = mockMvc
        .perform(get("/api/employee")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isBlank();
  }

  @Test
  public void test_findEmployeeById() throws Exception {
    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given

    when(employeeService.findEmployeeById("1")).thenReturn(Optional.of(employee));
    //when
    MockHttpServletResponse response = mockMvc
        .perform(get("/api/employee/1")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentAsString()).isNotBlank();
  }

  @Test
  public void test_findEmployeeById_not_found() throws Exception {
    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given

    when(employeeService.findEmployeeById("1")).thenReturn(Optional.empty());
    //when
    MockHttpServletResponse response = mockMvc
        .perform(get("/api/employee/1")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    assertThat(response.getContentAsString()).isBlank();
  }

  @Test
  public void test_deleteEmployeeById() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given

    when(employeeService.deleteEmployee("1")).thenReturn(true);
    //when
    MockHttpServletResponse response = mockMvc
        .perform(delete("/api/employee/1")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
  }

  @Test
  public void test_deleteEmployeeById_not_deleted() throws Exception {

    Employee employee = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given

    when(employeeService.deleteEmployee("1")).thenReturn(false);
    //when
    MockHttpServletResponse response = mockMvc
        .perform(delete("/api/employee/1")
            .accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn()
        .getResponse();
    // then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
  }
}