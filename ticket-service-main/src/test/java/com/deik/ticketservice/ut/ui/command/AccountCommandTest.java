package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.ui.command.AccountCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountCommandTest {

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
        Account account = Mockito.mock(Account.class);
        Mockito.when(accountService.getSignedInAccount()).thenReturn(account);

        // When
        underTest.describeAccount();

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(accountService).getSignedInAccount();
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

}
