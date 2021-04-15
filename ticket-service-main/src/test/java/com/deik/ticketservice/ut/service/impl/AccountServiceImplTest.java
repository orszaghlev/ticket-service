package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class AccountServiceImplTest {

    private AccountServiceImpl underTest;

    @Test
    public void testInitShouldCreateAdminAccountWhenAdminAccountIsNotInTheRepository() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
        Account created = new Account("admin", "admin", false);
        Mockito.when(accountRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(java.util.Optional.empty());

        // When
        underTest.init();

        // Then
        Mockito.verify(accountRepository, Mockito.times(1)).save(created);
    }

    @Test
    public void testIsAdminSignedInShouldReturnTrueWhenAdminIsSignedIn() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
        Account existing = new Account("admin", "admin", true);
        Mockito.when(accountRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(java.util.Optional.of(existing));
        boolean expected = existing.isSigned();

        // When
        boolean actual = underTest.isAdminSignedIn();

        // Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testIsAdminSignedInShouldReturnFalseWhenAdminAccountIsNotInTheRepository() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
        Mockito.when(accountRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(java.util.Optional.empty());

        // When
        boolean actual = underTest.isAdminSignedIn();

        // Then
        Assertions.assertFalse(actual);
    }

    @Test
    public void testGetSignedInAccountShouldGetAdminAccountWhenTheAdminIsSignedIn() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new AccountServiceImpl(accountRepository);
        Account expected = new Account("admin", "admin", true);
        Mockito.when(accountRepository.findByisSigned(true)).thenReturn(java.util.Optional.of(expected));

        // When
        Account actual = underTest.getSignedInAccount();

        // Then
        Assertions.assertEquals(expected, actual);
    }

}
