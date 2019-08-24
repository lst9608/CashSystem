package com.cashSystem.cmd.command.impl.account;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Account;

import java.util.List;

@CommandMeta(
        name = "CKZH",
        desc = "查看账号",
        group = "账号信息"
)
@AdminCommand
public class AccountBrowseCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("账户信息");
        List<Account> accountList = this.accountService.queryAllAccount();
        if(accountList.isEmpty()) {
            System.out.println("暂且没有账号存在");
        }else {
            System.out.println("---------------账号信息列表-------------");
            for (Account account: accountList ) {
                System.out.println("编号:"+account.getId());
                System.out.println("账号:"+account.getUsername());
                System.out.println("姓名:"+account.getName());
                System.out.println("类型:"+account.getAccountType().getDesc());
                System.out.println("状态:"+account.getAccountStatus().getDesc());
                System.out.println("--------------------------------------");
            }
        }
    }
}
