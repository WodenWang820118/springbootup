package com.example.demo.repositories;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Employee;

@Repository
public class EmployeeRepository {
  private final JdbcTemplate jdbcTemplate;

  public EmployeeRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Employee> findAll() {
    return jdbcTemplate.query("SELECT * FROM employee", new BeanPropertyRowMapper<>(Employee.class));
  }

  public Employee findById(int id) {
    return jdbcTemplate.query("SELECT * FROM employee WHERE id = ?", new BeanPropertyRowMapper<>(Employee.class), id)
        .stream().findAny().orElse(null);
  }

  public void save(Employee employee) {
    jdbcTemplate.update("INSERT INTO employee (name) VALUES (?)", employee.getId());
  }

  public void deleteById(int id) {
    jdbcTemplate.update("DELETE FROM employee WHERE id = ?", id);
  }

}
