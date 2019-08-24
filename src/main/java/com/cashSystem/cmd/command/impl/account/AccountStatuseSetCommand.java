package com.cashSystem.cmd.command.impl.account;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Account;

@CommandMeta(
        name = "TZZH",
        desc = "停止账号",
        group = "账号信息"
)
@AdminCommand
public class AccountStatuseSetCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        System.out.println("停止账号");
        System.out.println("请输入你要停止账号的id");
        int id = scanner.nextInt();
        boolean flag = this.accountService.checkUserName(id);
        if(flag) {
            System.out.println("是否真的要停止此账号:y/n");
            String str = scanner.next();
            if ("y".equalsIgnoreCase(str)) {
                boolean effect = this.accountService.accountStatuse
                        (id,account.getAccountStatus().getFlg());
                if(effect){
                    System.out.println("账号已停止使用");
                }else{
                    System.out.println("停止账号失败");
                }
                }else{
                System.out.println("不停止此账户");
            }
        }else{
            System.out.println("账户不存在");
        }
    }
}
