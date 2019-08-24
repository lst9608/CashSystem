package com.cashSystem.cmd.command.impl.account;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.entity.Account;

@CommandMeta(
        name = "CZMM",
        desc = "重置密码",
        group = "账号信息"
)
@AdminCommand
public class AccountPasswordCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        Account account = subject.getAccount();
        System.out.println("密码重置");
        System.out.println("请输入你要重置密码账户的id");
        int id = scanner.nextInt();
        System.out.println("请输入密码：");
        String password1 = scanner.next();
        System.out.println("请再次输入密码：");
        String password2 = scanner.next();
        if(!password1.equals(password2)){
            System.out.println("两次输入的密码不一致");
            return;
        }
        boolean flag = this.accountService.checkUserName(id);
        if(flag){
            boolean effect = this.accountService.modifyPwd(id,password1);
            if(effect){
                System.out.println("密码修改成功");
            }else{
                System.out.println("重置密码失败");
            }
        }else{
            System.out.println("账户不存在");
        }
    }
}
