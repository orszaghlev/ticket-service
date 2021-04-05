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

    @Autowired
    private LoginService loginService;

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public String signInPrivileged(@ShellOption String username, @ShellOption String password) {
        try {
            loginService.signInPrivileged(username, password);
        } catch (Exception e) {
            log.error("Login failed due to incorrect credentials", e);
            return "Login failed due to incorrect credentials";
        }
        return "";
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public void signOut() {
        try {
            loginService.signOut();
        } catch (Exception e) {
            log.error("Failed to sign out", e);
        }
    }

    @ShellMethod(value = "Describe account", key = "describe account")
    public void describeAccount(@ShellOption String username) {
        try {

        } catch (Exception e) {
            log.error("Failed to describe account", e);
        }
    }

}