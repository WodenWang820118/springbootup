package com.example.demo.dao;

import java.util.List;

import com.example.demo.Account;

public interface AccountDAO {
  List<Account> findAccounts(boolean tripWire);

  List<Account> findAccounts();

  void addAccount(Account theAccount, boolean vipFlag);

  boolean doWork();

  public String getName();

  public void setName(String name);

  public String getServiceCode();

  public void setServiceCode(String serviceCode);

}
