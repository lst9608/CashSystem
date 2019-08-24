package com.cashSystem.service;

import com.cashSystem.dao.AccountDao;
import com.cashSystem.dao.BaseDao;
import com.cashSystem.entity.Account;

import java.util.List;

public class AccountService extends BaseDao {
  private   AccountDao accountDao;
  public AccountService(){
      this.accountDao = new AccountDao();

        }
    public boolean register(Account account){
      return this.accountDao.register(account);
    }
    public Account login(String username,String password){
      return this.accountDao.login(username,password);
    }
    public List<Account> queryAllAccount(){
      return this.accountDao.queryAllAccount();
    }
    public boolean checkUserName(int id){
      return this.accountDao.checkUserName(id);
    }
    public boolean modifyPwd(int id,String password){
      return this.accountDao.modifyPwd(id,password);
    }
    public boolean accountStatuse(int id,int flag){
          return this.accountDao.quitAccount(id);
    }
}
