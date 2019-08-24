package com.cashSystem.entity;
import com.cashSystem.Common.AccountStatus;
import com.cashSystem.Common.AccountType;
import lombok.Data;

@Data
public class Account {
    private Integer id;
    private  String username;
    private  String password;
    private String name;
    private AccountType accountType;
    private AccountStatus accountStatus;

}
