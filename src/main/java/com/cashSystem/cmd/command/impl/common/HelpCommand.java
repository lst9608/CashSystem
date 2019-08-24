package com.cashSystem.cmd.command.impl.common;

import com.cashSystem.cmd.command.Command;
import com.cashSystem.cmd.command.Subject;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.AbstractCommand;
import com.cashSystem.cmd.command.impl.Commands;
import com.cashSystem.entity.Account;

import java.util.*;

@CommandMeta(
        name = "BZXX",
        desc = "帮助信息",
        group = "公共命令"
)
@EntranceCommand
@AdminCommand
@CustomerCommand
public class HelpCommand extends AbstractCommand {
    @Override
    public void execute(Subject subject) {
        System.out.println("帮助信息");
        Account account = subject.getAccount();
        if(account==null){
            entranceHelp();
        }else{
            switch (account.getAccountType()){
                case ADMIN:
                    adminHelp();
                    break;
                case CUSTOMER:
                    customerHelp();
                    break;
                    default:
            }
        }
    }
    public void entranceHelp(){
        printHelp("欢迎",Commands.ENTRANCE_COMMANDS.values());
    }
    public void adminHelp(){
        printHelp("管理端",Commands.ADMIN_COMMANDS.values());
    }
    public void customerHelp(){
        printHelp("客户端",Commands.CUSTOMER_COMMANDS.values());
    }

    public void printHelp(String title, Collection<Command> collection){
        System.out.println("*************"+title+"*************");
        Map<String,List<String>> helpTnfo = new HashMap<>();
        for(Command command :collection){
            CommandMeta commandMeta = command.getClass().getDeclaredAnnotation(CommandMeta.class);
            String group = commandMeta.group();
            List<String>func = helpTnfo.get(group);
            if(func==null){
                func = new ArrayList<>();
                helpTnfo.put(group,func);
            }
            func.add(commandMeta.desc()+"("+join(commandMeta.name())+")");
        }
        int i=0;
        for(Map.Entry<String,List<String>> entry:helpTnfo.entrySet()){
            i++;
            System.out.println(i+"."+entry.getKey());
            int j=0;
            for(String item:entry.getValue()){
                j++;
                System.out.println("\t"+(i)+"."+(j)+" "+item);
            }
        }
        System.out.println("输入菜单括号后面的编号（忽略大小写），进行下一步操作");
        System.out.println("*************************************************");
    }
    private String join(String[]array){
        return array[0];
    }
}
