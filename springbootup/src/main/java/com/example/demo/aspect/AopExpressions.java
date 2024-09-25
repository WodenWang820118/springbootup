package com.example.demo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AopExpressions {
  @Pointcut("execution(* com.example.demo.dao.*.*(..))")
  public void forDaoPackage() {
  }

  @Pointcut("execution(* com.example.demo.dao.*.get*(..))")
  public void getter() {
  }

  @Pointcut("execution(* com.example.demo.dao.*.set*(..))")
  public void setter() {
  }

  @Pointcut("forDaoPackage() && !(getter() || setter())")
  public void forDaoPackageNoGetterSetter() {
  }
}
