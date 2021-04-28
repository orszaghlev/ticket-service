package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.service.LoginService;
import com.deik.ticketservice.core.service.exception.LoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class LoginCommand {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String SIGN_IN_PRIVILEGED_FAILURE_MESSAGE = "Login failed due to incorrect credentials";
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCommand.class);

    private final LoginService loginService;

    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public void signInPrivileged(@ShellOption String username, @ShellOption String password) {
        try {
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                loginService.signInPrivileged(username, password);
            } else {
                System.out.println(SIGN_IN_PRIVILEGED_FAILURE_MESSAGE);
            }
        } catch (LoginException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public void signOut() {
        try {
            loginService.signOut();
        } catch (LoginException e) {
            LOGGER.error(e.getMessage());
        }
    }

}