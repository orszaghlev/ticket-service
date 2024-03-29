package com.deik.ticketservice.ui.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ExitCommand implements Quit.Command {

    private static final int EXIT_STATUS = 0;

    @ShellMethod(value = "Exit", key = "exit")
    public void exit() {
        System.exit(EXIT_STATUS);
    }

}