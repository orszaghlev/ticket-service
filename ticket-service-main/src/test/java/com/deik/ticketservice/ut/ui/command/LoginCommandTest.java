package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.service.LoginService;
import com.deik.ticketservice.ui.command.LoginCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginCommandTest {

    private final static String ADMIN_USERNAME = "admin";
    private final static String ADMIN_PASSWORD = "admin";
    private final static String USER_USERNAME = "sanyi";
    private final static String USER_PASSWORD = "asdQWE123";

    private LoginCommand underTest;

    private LoginService loginService;

    @BeforeEach
    public void init() {
        loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);
    }

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() {
        // Given

        // When
        underTest.signInPrivileged(ADMIN_USERNAME, ADMIN_PASSWORD);

        // Then
        Mockito.verify(loginService).signInPrivileged(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignInPrivilegedShouldNotSignInAdminWhenTheCredentialsAreNotCorrect() {
        // Given

        // When
        underTest.signInPrivileged(USER_USERNAME, USER_PASSWORD);

        // Then
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignOutShouldSignOutUser() {
        // Given

        // When
        underTest.signOut();

        // Then
        Mockito.verify(loginService).signOut();
        Mockito.verifyNoMoreInteractions(loginService);
    }

}
