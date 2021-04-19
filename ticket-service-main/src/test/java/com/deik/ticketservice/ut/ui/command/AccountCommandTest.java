package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.ui.command.AccountCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountCommandTest {

    private AccountCommand underTest;

    @Test
    public void testDescribeAccountShouldDescribeAccountWhenAdminIsSignedIn() {
        // Given
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new AccountCommand(accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        Account account = Mockito.mock(Account.class);
        Mockito.when(accountService.getSignedInAccount()).thenReturn(account);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(accountService, Mockito.times(1)).getSignedInAccount();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDescribeAccountShouldNotDescribeAccountWhenAdminIsNotSignedIn() {
        // Given
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new AccountCommand(accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

}
