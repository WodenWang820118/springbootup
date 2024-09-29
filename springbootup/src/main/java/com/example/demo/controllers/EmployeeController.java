package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.services.employee.EmployeeService;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  public EmployeeController(EmployeeService theEmployeeService) {
    employeeService = theEmployeeService;
  }

  @GetMapping("/list")
  public List<Employee> listEmployees() {
    return employeeService.findAll();
  }

  @GetMapping("/get/{id}")
  public Employee getEmployee(@PathVariable("id") int theId) {
    return employeeService.findById(theId);
  }

  @PostMapping("/save")
  public void saveEmployee(@RequestBody Employee theEmployee) {
    employeeService.save(theEmployee);
  }

  @GetMapping("/delete/{id}")
  public void delete(@PathVariable("id") int theId) {
    employeeService.deleteById(theId);
  }
}
