package com.cashSystem.cmd.command.impl.common;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
@CommandMeta(
        name = "TCXT",
        desc = "退出系统",
        group = "公共命令"
)
@EntranceCommand
@AdminCommand
@CustomerCommand
public class QuitCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("退出系统,欢迎下次使用");
        this.scanner.close();
        System.exit(0);
    }
}
