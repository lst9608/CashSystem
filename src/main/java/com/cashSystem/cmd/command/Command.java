package com.cashSystem.cmd.command;


import com.cashSystem.cmd.command.Subject;

import java.util.Scanner;

public interface Command {
    Scanner scanner = new Scanner(System.in);
    void execute(Subject subject);
}
