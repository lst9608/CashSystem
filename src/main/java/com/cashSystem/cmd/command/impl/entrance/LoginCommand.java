package com.cashSystem.cmd.command.impl.entrance;

import com.cashSystem.Common.AccountStatus;
import com.cashSystem.cmd.command.Command;
import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Account;

@CommandMeta(
        name = "LOGIN",
        desc = "登录",
        group = "入口命令"
)
@EntranceCommand
public class LoginCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("登录");
        Account account = subject.getAccount();
        if(account != null) {
            System.out.println("已经登录过了");
            return;
        }
        System.out.println("请输入用户名：");
        String username = Command.scanner.nextLine();
        System.out.println("请输入密码：");
        String password = Command.scanner.nextLine();
        //去数据查询是否有此账户，有返回，无返回null
        account = this.accountService.login(username,password);

        if(account != null && account.getAccountStatus() == AccountStatus.UNLOCK) {
            System.out.println(account.getAccountType().getDesc()+"登录成功");
            subject.setAccount(account);
        }else {
            if(account.getAccountStatus().getFlg()==2){
                System.out.println("此账户已停止使用");
            }else {
                System.out.println("登录失败，密码或者用户名错误");
            }
        }
    }
}
