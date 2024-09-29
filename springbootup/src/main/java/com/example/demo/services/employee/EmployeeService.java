package com.example.demo.services.employee;

import java.util.List;

import com.example.demo.entity.Employee;

public interface EmployeeService {
  List<Employee> findAll();

  Employee findById(int theId);

  void save(Employee theEmployee);

  void deleteById(int theId);
}
