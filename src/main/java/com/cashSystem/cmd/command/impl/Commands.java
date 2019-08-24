package com.cashSystem.cmd.command.impl;

import com.cashSystem.cmd.command.Command;
import com.cashSystem.cmd.command.annotation.AdminCommand;
import com.cashSystem.cmd.command.annotation.CommandMeta;
import com.cashSystem.cmd.command.annotation.CustomerCommand;
import com.cashSystem.cmd.command.annotation.EntranceCommand;
import com.cashSystem.cmd.command.impl.account.AccountBrowseCommand;
import com.cashSystem.cmd.command.impl.account.AccountPasswordCommand;
import com.cashSystem.cmd.command.impl.account.AccountStatuseSetCommand;
import com.cashSystem.cmd.command.impl.common.AboutCommand;
import com.cashSystem.cmd.command.impl.common.HelpCommand;
import com.cashSystem.cmd.command.impl.common.QuitCommand;
import com.cashSystem.cmd.command.impl.entrance.LoginCommand;
import com.cashSystem.cmd.command.impl.entrance.RegisterCommand;
import com.cashSystem.cmd.command.impl.goods.GoodsBrowseCommand;
import com.cashSystem.cmd.command.impl.goods.GoodsPutAwayCommand;
import com.cashSystem.cmd.command.impl.goods.GoodsSoldOutCommand;
import com.cashSystem.cmd.command.impl.goods.GoodsUpdateCommand;
import com.cashSystem.cmd.command.impl.order.OrderBrowseCommand;
import com.cashSystem.cmd.command.impl.order.OrderPayCommand;

import java.util.*;

public class Commands {
    private static final Set<Command> COMMANDS = new HashSet<>();
    private static final Command CACHED_HELP_COMMAND;
    public static final Map<Set<String>,Command> ADMIN_COMMANDS = new HashMap<>();
    public static final Map<Set<String>,Command> CUSTOMER_COMMANDS = new HashMap<>();
    public  static final Map<Set<String>,Command> ENTRANCE_COMMANDS = new HashMap<>();
    static {
        Collections.addAll(COMMANDS,
                new LoginCommand(),
                new RegisterCommand(),
                new AboutCommand(),
                new QuitCommand(),
                CACHED_HELP_COMMAND = new HelpCommand(),
                new AccountBrowseCommand(),
                new AccountPasswordCommand(),
                new AccountStatuseSetCommand(),
                new GoodsBrowseCommand(),
                new GoodsPutAwayCommand(),
                new GoodsSoldOutCommand(),
                new GoodsUpdateCommand(),
                new OrderBrowseCommand(),
                new OrderPayCommand()
                );
        for(Command command:COMMANDS){
            Class<?> cls = command.getClass();
            AdminCommand adminCommand = cls.getDeclaredAnnotation(AdminCommand.class);
            CustomerCommand customerCommand = cls.getDeclaredAnnotation(CustomerCommand.class);
            EntranceCommand entranceCommand = cls.getDeclaredAnnotation(EntranceCommand.class);
            CommandMeta commandMeta = cls.getDeclaredAnnotation(CommandMeta.class);

            String[] strings = commandMeta.name();
            Set<String> commandCodesKey;
            commandCodesKey = new HashSet<>(Arrays.asList(strings));
            if(adminCommand!=null){
                ADMIN_COMMANDS.put(commandCodesKey,command);

            }
            if(customerCommand!=null){
                CUSTOMER_COMMANDS.put(commandCodesKey,command);
            }
            if(entranceCommand!=null){
                ENTRANCE_COMMANDS.put(commandCodesKey,command);
            }
        }
    }
    public static Command getCachedHelpCommand(){
        return CACHED_HELP_COMMAND;
    }
    public static Command getEntranceCommand(String command){
        return getCommand(command,ENTRANCE_COMMANDS);
    }
    public static Command getAdminCommand(String command){
        return getCommand(command,ADMIN_COMMANDS);
    }
    public static Command getCustomerCommand(String command){
        return getCommand(command,CUSTOMER_COMMANDS);
    }


    public static Command getCommand(String command,Map<Set<String>,Command>commandMap){
        for(Map.Entry<Set<String>,Command> entry:commandMap.entrySet()){
            if(entry.getKey().contains(command)){
                return entry.getValue();
            }
        }
        return CACHED_HELP_COMMAND;
    }
}
