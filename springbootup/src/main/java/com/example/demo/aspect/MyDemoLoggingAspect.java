package com.example.demo.aspect;

import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.demo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

  @Around("execution(* com.example.demo.services.*.getFortune(..))")
  public Object aroundGetFortune(ProceedingJoinPoint joinPoint) throws Throwable {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @Around on method: " + method);

    long begin = System.currentTimeMillis();

    Object result = null;

    try {
      result = joinPoint.proceed();
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

  @After("execution(* com.example.demo.dao.AccountDAOImpl.findAccounts(..))")
  public void afterFinallyFindAccountsAdvice(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @After (finally) on method: " + method);
  }

  @AfterThrowing(pointcut = "execution(* com.example.demo.dao.AccountDAOImpl.findAccounts(..))", throwing = "theExc")
  public void afterThrowingFindAccountsAdvice(JoinPoint joinPoint, Throwable theExc) {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterThrowing on method: " + method);

    System.out.println("\n=====>>> The exception is: " + theExc);
  }

  @AfterReturning(pointcut = "execution(* com.example.demo.dao.AccountDAOImpl.findAccounts(..))", returning = "result")
  public void afterReturningFindAccountsAdvice(JoinPoint joinPoint, List<Account> result) {
    String method = joinPoint.getSignature().toShortString();
    System.out.println("\n=====>>> Executing @AfterReturning on method: " + method);

    System.out.println("\n=====>>> result is: " + result);

    // post-process the data

    // convert the account names to uppercase
    convertAccountNamesToUpperCase(result);
    System.out.println("\n=====>>> result is: " + result);
  }

  @Before("com.example.demo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
  public void beforeAddAccountAdvice(JoinPoint joinPoint) {
    System.out.println("\n=====>>> Executing @Before advice on addAccount()");

    // display the method signature
    MethodSignature methodSig = (MethodSignature) joinPoint.getSignature();
    System.out.println("Method: " + methodSig);

    // display method arguments
    Object[] args = joinPoint.getArgs();

    for (Object arg : args) {
      System.out.println(arg);

      if (arg instanceof com.example.demo.Account) {
        com.example.demo.Account account = (com.example.demo.Account) arg;
        System.out.println("account name: " + account.getName());
        System.out.println("account level: " + account.getLevel());
      }
    }

  }

  private void convertAccountNamesToUpperCase(List<Account> accounts) {
    for (Account account : accounts) {
      String name = account.getName().toUpperCase();
      account.setName(name);
    }
  }

}
