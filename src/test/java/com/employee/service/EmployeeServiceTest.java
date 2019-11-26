package com.employee.service;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.employee.data.entity.Employee;
import com.employee.data.repository.EmployeeRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

  @InjectMocks
  private EmployeeService employeeService;

  @Mock
  private EmployeeRepository employeeRepository;

  @Test
  public void test_findAllEmployees() {
    Employee employee1 = Employee.builder().firstName("C").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    Employee employee2 = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    List<Employee> expected = new ArrayList<>();
    expected.add(employee1);
    expected.add(employee2);

    //given
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
    //when

    List<Employee> actual = employeeService.findAllEmployees();
    //

    assertThat(actual.size()).isEqualTo(2);
    verify(employeeRepository, times(1)).findAll();

  }

  @Test
  public void test_findAllEmployees_ordering_first_name() {
    Employee employee1 = Employee.builder().firstName("C").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    Employee employee2 = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    List<Employee> expected = new ArrayList<>();
    expected.add(employee2);
    expected.add(employee1);

    //given
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
    //when

    List<Employee> actual = employeeService.findAllEmployees();
    //

    assertThat(expected.size()).isEqualTo(2);
    verify(employeeRepository, times(1)).findAll();
    assertThat(actual).containsSequence(expected);

  }

  @Test
  public void test_findAllEmployees_not_ordered_by_first_name() {
    Employee employee1 = Employee.builder().firstName("C").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    Employee employee2 = Employee.builder().firstName("A").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    List<Employee> expected = new ArrayList<>();
    //Adding as un ordered list.
    expected.add(employee1);
    expected.add(employee2);

    //given
    when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1, employee2));
    //when

    List<Employee> actual = employeeService.findAllEmployees();
    //

    assertThat(expected.size()).isEqualTo(2);
    verify(employeeRepository, times(1)).findAll();
    assertThat(actual).doesNotContainSequence(expected);

  }

  @Test
  public void test_register_employee() {

    Employee employee1 = Employee.builder().firstName("C").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    when(employeeRepository.save(employee1)).thenReturn(employee1);
    //when
    boolean registered = employeeService.registerEmployee(employee1);
    //
    assertTrue(registered);
    verify(employeeRepository, times(1)).save(employee1);
  }

  @Test
  public void test_register_employee_failed() {

    Employee employee1 = Employee.builder().firstName("C").lastName("B")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();
    //given
    when(employeeRepository.save(employee1)).thenReturn(null);
    //when
    boolean registered = employeeService.registerEmployee(employee1);
    //
    assertFalse(registered);
    verify(employeeRepository, times(1)).save(employee1);
  }

  @Test
  public void test_find_by_id() {
    Employee employee1 = Employee.builder().firstName("C").lastName("B").id("1")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    //given
    when(employeeRepository.findById("1")).thenReturn(Optional.of(employee1));
    //when
    Optional<Employee> actual = employeeService.findEmployeeById("1");
    //then
    assertThat(actual.get()).isEqualTo(employee1);
    verify(employeeRepository, times(1)).findById("1");

  }

  @Test
  public void test_find_by_id_not_found() {

    //given
    when(employeeRepository.findById("1")).thenReturn(Optional.empty());
    //when
    Optional<Employee> actual = employeeService.findEmployeeById("1");
    //then
    assertThat(actual).isEmpty();
    verify(employeeRepository, times(1)).findById("1");

  }

  @Test
  public void test_update_employee() {

    Employee employee1 = Employee.builder().firstName("C").lastName("B").id("1")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    Employee updatedemployee1 = Employee.builder().firstName("A").lastName("C").id("1")
        .dateOfBirth(LocalDate.of(2017, 10, 10)).department("IT").build();

    //given
    when(employeeRepository.save(updatedemployee1)).thenReturn(updatedemployee1);
    when(employeeRepository.findById(updatedemployee1.getId())).thenReturn(Optional.of(employee1));
    //when
    Optional<Employee> actual = employeeService.updateEmployee(updatedemployee1);
    //then
    assertThat(actual.get()).isEqualTo(updatedemployee1);
    verify(employeeRepository, times(1)).save(updatedemployee1);

  }

  @Test
  public void test_delete_employee() {

    //given
    when(employeeRepository.existsById("1")).thenReturn(false);
    //when
    boolean deleteEmployee = employeeService.deleteEmployee("1");
    //then
    assertTrue(deleteEmployee);
    verify(employeeRepository, times(1)).deleteById("1");

  }

  public void test_delete_employee_failed() {

    //given
    when(employeeRepository.existsById("1")).thenReturn(true);
    //when
    boolean deleteEmployee = employeeService.deleteEmployee("1");
    //then
    assertFalse(deleteEmployee);
    verify(employeeRepository, times(1)).deleteById("1");

  }
}