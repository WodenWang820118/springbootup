package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.MembershipDAO;

@SpringBootApplication
public class SpringbootupApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootupApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(AccountDAO accountDAO, MembershipDAO membershipDAO) {
    return runner -> {
      demoTheBeforeAdvice(accountDAO, membershipDAO);
    };
  }

  private void demoTheBeforeAdvice(AccountDAO theAccountDAO, MembershipDAO theMembershipDAO) {

    // call the business method
    Account myAccount = new Account();
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
}
