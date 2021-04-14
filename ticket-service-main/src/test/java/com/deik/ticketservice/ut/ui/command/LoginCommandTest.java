package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.LoginService;
import com.deik.ticketservice.ui.command.LoginCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginCommandTest {

    private LoginCommand underTest;

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);
        String expected = "Login successful";

        // When
        String actual = underTest.signInPrivileged("admin", "admin");

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSignInPrivilegedShouldNotSignInAdminWhenTheCredentialsAreNotCorrect() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);
        String expected = "Login failed due to incorrect credentials";

        // When
        String actual = underTest.signInPrivileged("sanyi", "asdQWE123");

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSignOutShouldSignOut() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);
        String expected = "Signed out";

        // When
        String actual = underTest.signOut();

        // Then
        Assertions.assertEquals(expected, actual);
    }

}
