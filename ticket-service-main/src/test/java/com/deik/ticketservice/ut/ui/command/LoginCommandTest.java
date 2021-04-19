package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.LoginService;
import com.deik.ticketservice.ui.command.LoginCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginCommandTest {

    private LoginCommand underTest;

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);

        // When
        underTest.signInPrivileged("admin", "admin");

        // Then
        Mockito.verify(loginService, Mockito.times(1))
                .signInPrivileged("admin", "admin");
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignInPrivilegedShouldNotSignInAdminWhenTheCredentialsAreNotCorrect() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);

        // When
        underTest.signInPrivileged("sanyi", "asdQWE123");

        // Then
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignOutShouldSignOutUser() {
        // Given
        LoginService loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);

        // When
        underTest.signOut();

        // Then
        Mockito.verify(loginService, Mockito.times(1)).signOut();
        Mockito.verifyNoMoreInteractions(loginService);
    }

}
