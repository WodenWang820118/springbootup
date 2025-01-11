package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class DepartmentControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() throws Exception {
    // Clear database before each test
    mockMvc.perform(delete("/departments/deleteAll"))
        .andExpect(status().isOk());
  }

  @Test
  void testCreateAndGetDepartment() throws Exception {
    // Given
    Department newDepartment = new Department();
    newDepartment.setDepartmentCode("IT01");
    newDepartment.setDepartmentName("Information Technology");
    newDepartment.setLocation("Building A");
    newDepartment.setActive(true);

    // When - create the Department
    MvcResult result = mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newDepartment)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.departmentCode").value("IT01"))
        .andExpect(jsonPath("$.departmentName").value("Information Technology"))
        .andExpect(jsonPath("$.location").value("Building A"))
        .andExpect(jsonPath("$.active").value(true))
        .andReturn();

    // Get the generated ID
    String content = result.getResponse().getContentAsString();
    Department savedDepartment = objectMapper.readValue(content, Department.class);
    int generatedId = savedDepartment.getId();

    // Then - retrieve the department
    mockMvc.perform(get("/departments/get/" + generatedId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.departmentCode").value("IT01"))
        .andExpect(jsonPath("$.departmentName").value("Information Technology"))
        .andExpect(jsonPath("$.location").value("Building A"))
        .andExpect(jsonPath("$.active").value(true));
  }

  @Test
  void testCreateDepartmentWithManager() throws Exception {
    // First create an employee to be manager
    Employee manager = new Employee();
    manager.setFirstName("John");
    manager.setLastName("Manager");
    manager.setEmail("john.manager@example.com");

    MvcResult managerResult = mockMvc.perform(post("/employees/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(manager)))
        .andExpect(status().isOk())
        .andReturn();

    Employee savedManager = objectMapper.readValue(
        managerResult.getResponse().getContentAsString(),
        Employee.class);

    // Create department with manager
    Department department = new Department();
    department.setDepartmentCode("HR01");
    department.setDepartmentName("Human Resources");
    department.setLocation("Building B");
    department.setManager(savedManager);
    department.setActive(true);

    mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(department)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.departmentCode").value("HR01"))
        .andExpect(jsonPath("$.departmentName").value("Human Resources"))
        .andExpect(jsonPath("$.manager.id").value(savedManager.getId()));
  }

  @Test
  void testDeleteDepartment() throws Exception {
    // Given: Create a Department
    Department department = new Department();
    department.setDepartmentCode("FIN01");
    department.setDepartmentName("Finance");
    department.setLocation("Building C");
    department.setActive(true);

    MvcResult result = mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(department)))
        .andExpect(status().isOk())
        .andReturn();

    Department savedDepartment = objectMapper.readValue(
        result.getResponse().getContentAsString(),
        Department.class);

    // When: Delete the Department
    mockMvc.perform(delete("/departments/delete/" + savedDepartment.getId()))
        .andExpect(status().isOk());

    // Then: Verify Department is deleted
    mockMvc.perform(get("/departments/get/" + savedDepartment.getId()))
        .andExpect(status().isNotFound());
  }

  @Test
  void testCreateDepartmentWithInvalidCode() throws Exception {
    Department department = new Department();
    department.setDepartmentCode("TOOLONGGGGG"); // More than 10 characters
    department.setDepartmentName("Test Department");
    department.setActive(true);

    mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(department)))
        .andExpect(status().isBadRequest());
  }
}
