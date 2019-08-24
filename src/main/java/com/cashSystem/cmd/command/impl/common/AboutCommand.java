package com.cashSystem.cmd.command.impl.common;

import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
@CommandMeta(
        name = "GYXT",
        desc = "关于系统",
        group = "公共命令"
)
@EntranceCommand
@AdminCommand
@CustomerCommand
public class AboutCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("关于系统");
        System.out.println("作者：李树婷");
        System.out.println("时间：20190817");
    }
}
