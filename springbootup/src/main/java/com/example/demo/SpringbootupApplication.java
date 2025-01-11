package com.example.demo;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.MembershipDAO;
import com.example.demo.services.trafficfortune.TrafficFortuneService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableJpaAuditing
public class SpringbootupApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootupApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AccountDAO accountDAO,
      MembershipDAO membershipDAO,
      TrafficFortuneService trafficFortuneService) {
    return runner -> {
      // demoTheBeforeAdvice(accountDAO, membershipDAO);
      // demoTheAfterReturningAdvice(accountDAO);
      // demoTheAfterThrowingAdvice(accountDAO);
      // demoTheAfterAdvice(accountDAO);
      // demoTheAroundAdvice(trafficFortuneService);
      // demoTheAroundAdviceHandleException(trafficFortuneService);
      // demoTheArounAdviceRethrowException(trafficFortuneService);
    };
  }

  private void demoTheArounAdviceRethrowException(TrafficFortuneService trafficFortuneService) {
    System.out.println("\nMain Program: AroundDemoRethrowExceptionApp");
    System.out.println("Calling getFortune");

    boolean tripWire = true;
    String data = trafficFortuneService.getFortune(tripWire);

    System.out.println("\nMy fortune is: " + data);
    System.out.println("Finished");
  }

  private void demoTheAroundAdviceHandleException(TrafficFortuneService trafficFortuneService) {
    System.out.println("\nMain Program: AroundDemoHandleExceptionApp");
    System.out.println("Calling getFortune");

    boolean tripWire = true;
    String data = trafficFortuneService.getFortune(tripWire);

    System.out.println("\nMy fortune is: " + data);
    System.out.println("Finished");
  }

  private void demoTheAroundAdvice(TrafficFortuneService trafficFortuneService) {
    System.out.println("\nMain Program: AroundDemoApp");
    System.out.println("Calling getFortune");

    String data = trafficFortuneService.getFortune();

    System.out.println("\nMy fortune is: " + data);
    System.out.println("Finished");
  }

  private void demoTheAfterAdvice(AccountDAO accountDAO) {
    List<Account> accounts = null;
    try {
      boolean tripWire = false;
      accounts = accountDAO.findAccounts(tripWire);
    } catch (Exception e) {
      System.out.println("\n\nMain Program ... caught exception: " + e);
    }
    // display the accounts
    System.out.println("\n\nMain Program: AfterThrowingAdviceDemoApp");
    System.out.println("----");
    System.out.println(accounts);
    System.out.println("\n");
  }

  private void demoTheAfterThrowingAdvice(AccountDAO accountDAO) {
    List<Account> accounts = null;
    try {
      boolean tripWire = true;
      accounts = accountDAO.findAccounts(tripWire);
    } catch (Exception e) {
      System.out.println("\n\nMain Program ... caught exception: " + e);
    }
    // display the accounts
    System.out.println("\n\nMain Program: AfterThrowingAdviceDemoApp");
    System.out.println("----");
    System.out.println(accounts);
    System.out.println("\n");
  }

  private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

    // call the business method
    Account myAccount = new Account();
    myAccount.setName("Madhu");
    myAccount.setLevel("Platinum");
    theAccountDAO.addAccount(myAccount, true);
    theAccountDAO.doWork();

    // call the accountdao getter/setter methods
    theAccountDAO.setName("foobar");
    theAccountDAO.setServiceCode("silver");

    String name = theAccountDAO.getName();
    String code = theAccountDAO.getServiceCode();

    // call the membership business method
    theMembershipDAO.addSillyMember();
    theMembershipDAO.goToSleep();

  }

  private void demoTheAfterReturningAdvice(AccountDAO accountDAO) {

    List<Account> accounts = accountDAO.findAccounts();

    // display the accounts
    System.out.println("\n\nMain Program: AfterReturningDemoApp");
    System.out.println("----");
    System.out.println(accounts);
    System.out.println("\n");
  }

}
