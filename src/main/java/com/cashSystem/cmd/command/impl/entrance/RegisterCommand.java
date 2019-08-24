package com.cashSystem.cmd.command.impl.entrance;

import com.cashSystem.Common.AccountStatus;
import com.cashSystem.Common.AccountType;
import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Account;

@CommandMeta(
        name = "REGISTER",
        desc = "注册",
        group = "入口命令"
)
@EntranceCommand
public class RegisterCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("注册");
        System.out.println("请输入用户名：");
        String username = this.scanner.nextLine();
        System.out.println("请输入密码：");
        String password1 = scanner.nextLine();
        System.out.println("请再次输入密码：");
        String password2 = scanner.nextLine();
        if(!password1.equals(password2)){
            System.out.println("两次输入的密码不一致");
            return;
        }
        System.out.println("请输入姓名：");
        String name = scanner.nextLine();
        System.out.println("请输入账户类型：1.管理员  2.客户");
        int accountTypeFlag = scanner.nextInt();
        AccountType accountType = AccountType.valueOf(accountTypeFlag);
        final Account account = new Account();
        account.setUsername(username);
        account.setPassword(password1);
        account.setAccountType(accountType);
        account.setName(name);
        account.setAccountStatus(AccountStatus.UNLOCK);
        boolean effect = this.accountService.register(account);
        if(effect){
            System.out.println("恭喜注册成功");
        }else{
            System.out.println("注册失败");
        }
    }
}
