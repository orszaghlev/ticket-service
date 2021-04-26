package com.deik.ticketservice.ut.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountServiceImplTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account LOGGED_IN_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, true);
    private static final Account LOGGED_OUT_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, false);
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private AccountServiceImpl underTest;

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void testInitShouldCreateAdminAccountWhenAdminAccountIsNotInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.empty());

        // When
        underTest.init();

        // Then
        Mockito.verify(accountRepository).save(LOGGED_OUT_ADMIN_ACCOUNT);
        Mockito.verify(accountRepository).findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testInitShouldNotCreateAdminAccountWhenAdminAccountIsInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_OUT_ADMIN_ACCOUNT));

        // When
        underTest.init();

        // Then
        Mockito.verify(accountRepository).findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testIsAdminSignedInShouldReturnTrueWhenAdminIsSignedIn() throws AccountException {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));
        boolean expected = LOGGED_IN_ADMIN_ACCOUNT.isSigned();

        // When
        boolean actual = underTest.isAdminSignedIn();

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS))
                .findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testIsAdminSignedInShouldThrowAccountExceptionWhenAdminAccountIsNotInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(AccountException.class, () -> underTest.isAdminSignedIn());

        // Then
        Mockito.verify(accountRepository).findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testGetSignedInAccountShouldGetAdminAccountWhenTheAdminIsSignedIn() throws AccountException {
        // Given
        Mockito.when(accountRepository.findByisSigned(true)).thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));

        // When
        Account actual = underTest.getSignedInAccount();

        // Then
        Assertions.assertEquals(LOGGED_IN_ADMIN_ACCOUNT, actual);
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByisSigned(true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testGetSignedInAccountShouldThrowAccountExceptionWhenNoneOfTheAccountsAreSignedIn() {
        // Given
        Mockito.when(accountRepository.findByisSigned(true)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(AccountException.class, () -> underTest.getSignedInAccount());

        // Then
        Mockito.verify(accountRepository).findByisSigned(true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}
