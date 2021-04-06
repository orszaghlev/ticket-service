package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class AccountCommand {

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Describe account", key = "describe account")
    public void describeAccount() {
        try {
            if (accountService.isAdminSignedIn()) {
                System.out.println(String.format("Signed in with privileged account %s", accountService.getSignedInAccount().getUsername()));
            }
            else {
                System.out.println("You are not signed in");
            }
        } catch (Exception e) {
            log.error("Failed to describe account", e);
        }
    }

}