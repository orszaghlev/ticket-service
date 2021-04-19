package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
public class LoginCommand {

    private final LoginService loginService;

    @Autowired
    public LoginCommand(LoginService loginService) {
        this.loginService = loginService;
    }

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public void signInPrivileged(@ShellOption String username, @ShellOption String password) {
        try {
            if (username.equals("admin") && password.equals("admin")) {
                loginService.signInPrivileged(username, password);
            } else {
                System.out.println("Login failed due to incorrect credentials");
            }
        } catch (Exception e) {
            log.error("Failed to sign in", e);
        }
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public void signOut() {
        try {
            loginService.signOut();
        } catch (Exception e) {
            log.error("Failed to sign out", e);
        }
    }

}