package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.service.LoginService;
import com.deik.ticketservice.core.service.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
public class LoginCommand {

    private static final String SIGN_IN_PRIVILEGED_VALUE = "Sign in privileged";
    private static final String SIGN_IN_PRIVILEGED_KEY = "sign in privileged";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String SIGN_IN_PRIVILEGED_FAIL = "Login failed due to incorrect credentials";
    private static final String SIGN_OUT_VALUE = "Sign out";
    private static final String SIGN_OUT_KEY = "sign out";

    private final LoginService loginService;

    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = SIGN_IN_PRIVILEGED_VALUE, key = SIGN_IN_PRIVILEGED_KEY)
    public void signInPrivileged(@ShellOption String username, @ShellOption String password) {
        try {
            if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
                loginService.signInPrivileged(username, password);
            } else {
                System.out.println(SIGN_IN_PRIVILEGED_FAIL);
            }
        } catch (LoginException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = SIGN_OUT_VALUE, key = SIGN_OUT_KEY)
    public void signOut() {
        try {
            loginService.signOut();
        } catch (LoginException e) {
            log.error(e.getMessage());
        }
    }

}