package com.deik.ticketservice.ut.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginServiceImplTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account LOGGED_IN_ADMIN_ACCOUNT = new Account(ADMIN_USERNAME, ADMIN_PASSWORD, true);
    private static final Account LOGGED_OUT_ADMIN_ACCOUNT = new Account(ADMIN_USERNAME, ADMIN_PASSWORD, false);
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private LoginServiceImpl underTest;

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new LoginServiceImpl(accountRepository);
    }

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_OUT_ADMIN_ACCOUNT));
        Mockito.when(accountRepository.save(LOGGED_OUT_ADMIN_ACCOUNT)).thenReturn(LOGGED_OUT_ADMIN_ACCOUNT);

        // When
        underTest.signInPrivileged(ADMIN_USERNAME, ADMIN_PASSWORD);

        // Then
        Mockito.verify(accountRepository).save(LOGGED_OUT_ADMIN_ACCOUNT);
        Assertions.assertEquals(ADMIN_USERNAME, LOGGED_OUT_ADMIN_ACCOUNT.getUsername());
        Assertions.assertEquals(ADMIN_PASSWORD, LOGGED_OUT_ADMIN_ACCOUNT.getPassword());
        Assertions.assertTrue(LOGGED_OUT_ADMIN_ACCOUNT.isSigned());
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS))
                .findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testSignOutShouldSignOutUserWhenTheUsersAccountIsInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByisSigned(true)).thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));
        Mockito.when(accountRepository.save(LOGGED_IN_ADMIN_ACCOUNT)).thenReturn(LOGGED_IN_ADMIN_ACCOUNT);

        // When
        underTest.signOut();

        // Then
        Mockito.verify(accountRepository).save(LOGGED_IN_ADMIN_ACCOUNT);
        Assertions.assertEquals(ADMIN_USERNAME, LOGGED_IN_ADMIN_ACCOUNT.getUsername());
        Assertions.assertEquals(ADMIN_PASSWORD, LOGGED_IN_ADMIN_ACCOUNT.getPassword());
        Assertions.assertFalse(LOGGED_IN_ADMIN_ACCOUNT.isSigned());
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByisSigned(true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}
