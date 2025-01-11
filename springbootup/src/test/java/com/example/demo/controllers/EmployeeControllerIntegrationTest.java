package com.example.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
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
class EmployeeControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    // Clear or reset database before each test if needed
  }

  @Test
  void testCreateAndGetEmployee() throws Exception {
    // -------------------------
    // 1. Create a DepartmentDto
    // -------------------------
    DepartmentDto departmentDto = new DepartmentDto();
    departmentDto.setDepartmentCode("IT");
    departmentDto.setDepartmentName("Information Technology");
    departmentDto.setLocation("New York");
    departmentDto.setActive(true);

    String departmentJson = objectMapper.writeValueAsString(departmentDto);

    // -------------------------
    // 2. Save Department (DTO)
    // -------------------------
    MvcResult deptResult = mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(departmentJson))
        .andExpect(status().isOk())
        .andReturn();

    DepartmentDto savedDepartmentDto = objectMapper.readValue(
        deptResult.getResponse().getContentAsString(),
        DepartmentDto.class);

    // -------------------------
    // 3. Create an EmployeeDto
    // -------------------------
    EmployeeDto newEmployeeDto = new EmployeeDto();
    newEmployeeDto.setFirstName("John");
    newEmployeeDto.setLastName("Doe");
    newEmployeeDto.setEmail("john.doe@example.com");
    // Important: set the departmentId from the saved DTO
    newEmployeeDto.setDepartment(savedDepartmentDto);

    String employeeJson = objectMapper.writeValueAsString(newEmployeeDto);

    // -------------------------
    // 4. Save Employee (DTO)
    // -------------------------
    MvcResult empResult = mockMvc.perform(post("/employees/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"))
        .andExpect(jsonPath("$.email").value("john.doe@example.com"))
        .andReturn();

    EmployeeDto savedEmployeeDto = objectMapper.readValue(
        empResult.getResponse().getContentAsString(),
        EmployeeDto.class);

    // ----------------------------------
    // 5. Retrieve Employee by ID (DTO)
    // ----------------------------------
    mockMvc.perform(get("/employees/get/" + savedEmployeeDto.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName").value("John"))
        .andExpect(jsonPath("$.lastName").value("Doe"))
        .andExpect(jsonPath("$.email").value("john.doe@example.com"))
        .andExpect(jsonPath("$.department.departmentCode").value("IT"));
  }

  @Test
  void testDeleteEmployee() throws Exception {
    // -------------------------
    // 1. Create a DepartmentDto
    // -------------------------
    DepartmentDto departmentDto = new DepartmentDto();
    departmentDto.setDepartmentCode("HR");
    departmentDto.setDepartmentName("Human Resources");
    departmentDto.setLocation("Boston");
    departmentDto.setActive(true);

    String departmentJson = objectMapper.writeValueAsString(departmentDto);

    // -------------------------
    // 2. Save Department (DTO)
    // -------------------------
    MvcResult deptResult = mockMvc.perform(post("/departments/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(departmentJson))
        .andExpect(status().isOk())
        .andReturn();

    DepartmentDto savedDepartmentDto = objectMapper.readValue(
        deptResult.getResponse().getContentAsString(),
        DepartmentDto.class);

    // -------------------------
    // 3. Create an EmployeeDto
    // -------------------------
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("John");
    employeeDto.setLastName("Doe");
    employeeDto.setEmail("john.doe@example.com");
    employeeDto.setDepartment(savedDepartmentDto);

    String employeeJson = objectMapper.writeValueAsString(employeeDto);

    // -------------------------
    // 4. Save Employee (DTO)
    // -------------------------
    MvcResult saveResult = mockMvc.perform(post("/employees/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeJson))
        .andExpect(status().isOk())
        .andReturn();

    EmployeeDto savedEmployeeDto = objectMapper.readValue(
        saveResult.getResponse().getContentAsString(),
        EmployeeDto.class);

    // --------------------------------------
    // 5. Delete the Employee by ID (DTO)
    // --------------------------------------
    mockMvc.perform(get("/employees/delete/" + savedEmployeeDto.getId()))
        .andExpect(status().isOk());

    // ---------------------------------------
    // 6. Verify the Employee is deleted
    // ---------------------------------------
    mockMvc.perform(get("/employees/get/" + savedEmployeeDto.getId()))
        .andExpect(status().isNotFound());
  }
}
