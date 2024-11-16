# NxSpringbootCollection

## Table of Contents

- [Overview](#overview)
- [Setup](#setup)
- [Springbootup](#springbootup)
  - [Spring Boot](#spring-boot)
  - [Development server](#development-server)
  - [Endpoints](#endpoints)
  - [Aspects Oriented Programming](#aspects-oriented-programming)
  - [H2 Database](#h2-database)

## Overview

The project is a collection of Spring Boot projects. The projects are created to demonstrate the features of Nx workspace. It could be used as a reference for creating microservices using Nx workspace. Using Nx workspace, we can configure Spring Boot projects with ease.

## Setup

```bash
npm install -g pnpm
```

```bash
pnpm install
```

## Springbootup

### Spring Boot

The Spring Boot uses `pom.xml` for the dependencies management.

### Development server

```bash
pnpm run start
```

### Endpoints

After running the above command, you can access the following features via `http://localhost:8080/`:

- Health
  - `http://localhost:8080/health` - Whether the application is up and running
- Employee
  - GET `http://localhost:8080/employee/list` - Get all employees
  - GET `http://localhost:8080/employee/get/{id}` - Get employee by id
  - POST `http://localhost:8080/employee/save` - Save employee with request body
  - DELETE `http://localhost:8080/employee/delete/{id}` - Update employee by id

### Aspects Oriented Programming

The `aspect` directory contains the implementation of Aspect Oriented Programming in Spring Boot.

- Use `@Around`, `@Before`, `@After`, `@AfterReturning`, `@AfterThrowing` annotations to implement the aspects
- `AopExpression.java` - Contains the pointcut expressions
- `EmployeeLoggingAspect.java`, `MyApiAnalyticsAspect.java`, `MyCloudLogAsyncAspect.java`, `MyDemoLoggingAspect.java` - Contains the implementation of the aspects for `services`

### H2 Database

H2 database is SQL database written in Java. The configuration:

```java
// DatabaseConfig.java
package com.example.demo;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DatabaseConfig {
  @Bean
  public JdbcTemplate jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }
}
```

It is for CRUD operations for the `Employee` entity.
