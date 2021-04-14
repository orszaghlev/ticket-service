package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.ui.command.AccountCommand;
import org.junit.jupiter.api.Assertions;
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
        String expected = String.format("Signed in with privileged account '%s'",
                accountService.getSignedInAccount().getUsername());

        // When
        String actual = underTest.describeAccount();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
    }

    @Test
    public void testDescribeAccountShouldNotDescribeAccountWhenAdminIsNotSignedIn() {
        // Given
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new AccountCommand(accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        Account account = Mockito.mock(Account.class);
        Mockito.when(accountService.getSignedInAccount()).thenReturn(account);
        String expected = "You are not signed in";

        // When
        String actual = underTest.describeAccount();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
    }

}
