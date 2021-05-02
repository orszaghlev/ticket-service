package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.exception.AccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AccountCommand {

    private static final String DESCRIBE_ACCOUNT_SUCCESS_MESSAGE = "Signed in with privileged account '%s'%n";
    private static final String DESCRIBE_ACCOUNT_FAILURE_MESSAGE = "You are not signed in";
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCommand.class);

    private final AccountService accountService;

    public AccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public void describeAccount() {
        try {
            if (accountService.isAdminSignedIn()) {
                System.out.printf(DESCRIBE_ACCOUNT_SUCCESS_MESSAGE, accountService.getSignedInAccount().getUsername());
            } else {
                System.out.println(DESCRIBE_ACCOUNT_FAILURE_MESSAGE);
            }
        } catch (AccountException e) {
            LOGGER.error(e.getMessage());
        }
    }

}