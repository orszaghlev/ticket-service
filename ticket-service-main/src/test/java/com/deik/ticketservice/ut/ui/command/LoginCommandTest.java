package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.account.LoginService;
import com.deik.ticketservice.core.account.exception.LoginException;
import com.deik.ticketservice.core.account.model.AccountDto;
import com.deik.ticketservice.ui.command.LoginCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginCommandTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_USERNAME = "sanyi";
    private static final String USER_PASSWORD = "asdQWE123";
    private static final AccountDto ADMIN_ACCOUNT_DTO = new AccountDto.Builder()
            .withUsername(ADMIN_USERNAME)
            .withPassword(ADMIN_PASSWORD)
            .build();

    private LoginCommand underTest;

    private LoginService loginService;

    @BeforeEach
    public void init() {
        loginService = Mockito.mock(LoginService.class);
        underTest = new LoginCommand(loginService);
    }

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() throws LoginException {
        // Given

        // When
        underTest.signInPrivileged(ADMIN_USERNAME, ADMIN_PASSWORD);

        // Then
        Mockito.verify(loginService).signInPrivileged(ADMIN_ACCOUNT_DTO);
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignInPrivilegedShouldNotSignInAdminWhenTheCredentialsAreIncorrect() {
        // Given

        // When
        underTest.signInPrivileged(USER_USERNAME, USER_PASSWORD);

        // Then
        Mockito.verifyNoMoreInteractions(loginService);
    }

    @Test
    public void testSignOutShouldSignOutUserWhenTheUserIsSignedIn() throws LoginException {
        // Given

        // When
        underTest.signOut();

        // Then
        Mockito.verify(loginService).signOut();
        Mockito.verifyNoMoreInteractions(loginService);
    }

}
