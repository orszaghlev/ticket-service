package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginServiceImplTest {

    private LoginServiceImpl underTest;

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new LoginServiceImpl(accountRepository);
        Account existing = new Account("admin", "admin", false);
        Mockito.when(accountRepository.findByUsernameAndPassword("admin", "admin"))
                .thenReturn(java.util.Optional.of(existing));
        Mockito.when(accountRepository.save(existing)).thenReturn(existing);

        // When
        underTest.signInPrivileged("admin", "admin");

        // Then
        Mockito.verify(accountRepository, Mockito.times(1)).save(existing);
        Assertions.assertEquals("admin", existing.getUsername());
        Assertions.assertEquals("admin", existing.getPassword());
        Assertions.assertTrue(existing.isSigned());
    }

    @Test
    public void testSignOutShouldSignOutUserWhenTheUsersAccountIsInTheRepository() {
        // Given
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new LoginServiceImpl(accountRepository);
        Account existing = new Account("admin", "admin", true);
        Mockito.when(accountRepository.findByisSigned(true)).thenReturn(java.util.Optional.of(existing));
        Mockito.when(accountRepository.save(existing)).thenReturn(existing);

        // When
        underTest.signOut();

        // Then
        Mockito.verify(accountRepository, Mockito.times(1)).save(existing);
        Assertions.assertEquals("admin", existing.getUsername());
        Assertions.assertEquals("admin", existing.getPassword());
        Assertions.assertFalse(existing.isSigned());
    }

}
