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

- `http://localhost:8080/` for the root endpoint

- `http://localhost:8080/swagger-ui/index.html` for Swagger UI. Please refer it to know more about the endpoints.

### Aspects Oriented Programming

The `aspect` directory contains the implementation of Aspect Oriented Programming in Spring Boot.
