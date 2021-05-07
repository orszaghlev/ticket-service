package com.deik.ticketservice.ut.core.account.impl;

import com.deik.ticketservice.core.account.persistence.entity.Account;
import com.deik.ticketservice.core.account.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.core.account.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountServiceImplTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account.Role ADMIN_ROLE = Account.Role.ADMIN;
    private static final Account LOGGED_IN_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ROLE, true);
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private AccountServiceImpl underTest;

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void testIsAdminSignedInShouldReturnTrueWhenTheAdminIsSignedIn() throws AccountException {
        // Given
        Mockito.when(accountRepository.findByRole(ADMIN_ROLE)).thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));
        boolean expected = LOGGED_IN_ADMIN_ACCOUNT.getIsSigned();

        // When
        boolean actual = underTest.isAdminSignedIn();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByRole(ADMIN_ROLE);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testIsAdminSignedInShouldThrowAccountExceptionWhenTheAdminAccountIsNotInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByRole(ADMIN_ROLE)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(AccountException.class, () -> underTest.isAdminSignedIn());

        // Then
        Mockito.verify(accountRepository).findByRole(ADMIN_ROLE);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testGetUsernameOfSignedInPrivilegedAccountShouldGetUsernameOfAdminAccountWhenTheAdminIsSignedIn() throws
            AccountException {
        // Given
        Mockito.when(accountRepository.findByRoleAndIsSigned(ADMIN_ROLE, true))
                .thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));

        // When
        String actual = underTest.getUsernameOfSignedInPrivilegedAccount();

        // Then
        Assertions.assertEquals(ADMIN_USERNAME, actual);
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByRoleAndIsSigned(ADMIN_ROLE, true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testGetUsernameOfSignedInPrivilegedAccountShouldThrowAccountExceptionWhenNoneOfThePrivilegedUsersAreSignedIn() {
        // Given
        Mockito.when(accountRepository.findByRoleAndIsSigned(ADMIN_ROLE, true)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(AccountException.class, () -> underTest.getUsernameOfSignedInPrivilegedAccount());

        // Then
        Mockito.verify(accountRepository).findByRoleAndIsSigned(ADMIN_ROLE, true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}
