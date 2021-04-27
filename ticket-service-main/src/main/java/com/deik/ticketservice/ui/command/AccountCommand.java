package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.exception.AccountException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class AccountCommand {

    private static final String DESCRIBE_ACCOUNT_VALUE = "Describe account";
    private static final String DESCRIBE_ACCOUNT_KEY = "describe account";
    private static final String DESCRIBE_ACCOUNT_SUCCESS = "Signed in with privileged account '%s'%n";
    private static final String DESCRIBE_ACCOUNT_FAIL = "You are not signed in";

    private final AccountService accountService;

    public AccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @ShellMethod(value = DESCRIBE_ACCOUNT_VALUE, key = DESCRIBE_ACCOUNT_KEY)
    public void describeAccount() {
        try {
            if (accountService.isAdminSignedIn()) {
                System.out.printf(DESCRIBE_ACCOUNT_SUCCESS,
                        accountService.getSignedInAccount().getUsername());
            } else {
                System.out.println(DESCRIBE_ACCOUNT_FAIL);
            }
        } catch (AccountException e) {
            log.error(e.getMessage());
        }
    }

}