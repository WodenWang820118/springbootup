package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeLoggingAspect {

  @Around("execution(* com.example.demo.services.employee.*.findAll())")
  public Object aroundFindAllAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @Around on method: " + method);

    long begin = System.currentTimeMillis();

    Object result = null;

    try {
      result = joinPoint.proceed();
      // Log the returned value
      System.out.println("\n=====>>> Result is: " + result);
    } catch (Exception e) {
      System.out.println("\n=====>>> The exception is: " + e.getMessage());
      // rethrow exception
      throw e;
    }

    long end = System.currentTimeMillis();

    long duration = end - begin;
    System.out.println("\n=====>>> Duration: " + duration / 1000.0 + " seconds");

    return result;

  }

  @Around("execution(* com.example.demo.services.employee.*.findById(..))")
  public Object aroundFindByIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @Around on method: " + method);

    long begin = System.currentTimeMillis();

    Object result = null;

    try {
      result = joinPoint.proceed();
      // Log the returned value
      System.out.println("\n=====>>> Result is: " + result);
    } catch (Exception e) {
      System.out.println("\n=====>>> The exception is: " + e.getMessage());
      // rethrow exception
      throw e;
    }

    long end = System.currentTimeMillis();

    long duration = end - begin;
    System.out.println("\n=====>>> Duration: " + duration / 1000.0 + " seconds");

    return result;

  }

}
