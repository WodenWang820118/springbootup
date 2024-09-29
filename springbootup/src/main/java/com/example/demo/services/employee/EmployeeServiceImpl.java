package com.example.demo.services.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  private final EmployeeRepository employeeRepository;

  @Autowired
  public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  @Override
  public List<Employee> findAll() {
    return employeeRepository.findAll();
  }

  @Override
  public Employee findById(int theId) {
    return employeeRepository.findById(theId);
  }

  @Override
  public void save(Employee theEmployee) {
    employeeRepository.save(theEmployee);
  }

  @Override
  public void deleteById(int theId) {
    employeeRepository.deleteById(theId);
  }

}
