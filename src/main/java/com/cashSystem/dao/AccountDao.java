package com.cashSystem.dao;

import com.cashSystem.Common.AccountStatus;
import com.cashSystem.Common.AccountType;
import com.cashSystem.entity.Account;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao extends BaseDao{
    //注册
    public boolean register(Account account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean effect =false;
        try {
            connection = this.getConnection(true);
            String sql = "insert into account(username,password,name,account_type,account_status)values(?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement .setString(1, account.getUsername());
            preparedStatement .setString(2, DigestUtils.md5Hex(account.getPassword()));
            preparedStatement .setString(3, account.getName());
            preparedStatement .setInt(4, account.getAccountType().getFlg());
            preparedStatement .setInt(5, account.getAccountStatus().getFlg());
            effect = (preparedStatement.executeUpdate()==1);
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                Integer id = resultSet.getInt(1);
                account.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return effect;
    }


//登录
    public Account login(String username,String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        try {
            connection = this.getConnection(true);
            String sql = "select id, username, password, name,account_type, account_status from account where username=? and password=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, DigestUtils.md5Hex(password));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = this.extractAccount(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet,preparedStatement, connection);
        }
        return account;
    }
    private Account extractAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt("id"));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setName(resultSet.getString("name"));
        account.setAccountType(AccountType.valueOf(resultSet.getInt("account_type")));
        account.setAccountStatus(AccountStatus.valueOf(resultSet.getInt("account_status")));
        return account;
    }

//查看所有账户
    public List<Account> queryAllAccount(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accountList = new ArrayList<>();
        try {
            connection = this.getConnection(true);
            String sql = "select id, username, password, name, account_type, account_status from account";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountList.add(this.extractAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return accountList;
    }
    //检查重名
    public boolean checkUserName(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.getConnection(true);
            String sql = "select * from account where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet, preparedStatement, connection);
        }
        return false;
    }
    //重置密码
    public boolean modifyPwd(int id, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update Account set password=? where id=?";
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,DigestUtils.md5Hex(password));
            preparedStatement.setInt(2,id);
            effect = (preparedStatement.executeUpdate()==1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
      return effect;
    }
    //停止账户
    public boolean quitAccount(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean effect = false;
        try {
            connection = this.getConnection(true);
            String sql = "update Account set account_status=2 where id=?";
            preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1,id);
            effect = (preparedStatement.executeUpdate()==1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            this.closeResource(resultSet,preparedStatement,connection);
        }
        return effect;
    }

}
