package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.ui.command.AccountCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountCommandTest {

    private static final String ADMIN_USERNAME = "admin";

    private AccountCommand underTest;

    private AccountService accountService;

    @BeforeEach
    public void init() {
        accountService = Mockito.mock(AccountService.class);
        underTest = new AccountCommand(accountService);
    }

    @Test
    public void testDescribeAccountShouldDescribeAccountWhenTheAdminIsSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        Mockito.when(accountService.getUsernameOfSignedInPrivilegedAccount()).thenReturn(ADMIN_USERNAME);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(accountService).getUsernameOfSignedInPrivilegedAccount();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDescribeAccountShouldNotDescribeAccountWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDescribeAccountShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

}
