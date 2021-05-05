package com.deik.ticketservice.ut.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.exception.LoginException;
import com.deik.ticketservice.core.service.impl.LoginServiceImpl;
import com.deik.ticketservice.core.service.model.AccountDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginServiceImplTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String USER_USERNAME = "sanyi";
    private static final String USER_PASSWORD = "asdQWE123";
    private static final Account.Role ADMIN_ROLE = Account.Role.ADMIN;
    private static final Account LOGGED_IN_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ROLE, true);
    private static final Account LOGGED_OUT_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ROLE, false);
    private static final AccountDto ADMIN_ACCOUNT_DTO = new AccountDto.Builder()
            .withUsername(ADMIN_USERNAME)
            .withPassword(ADMIN_PASSWORD)
            .build();
    private static final AccountDto ADMIN_ACCOUNT_DTO_WITH_INCORRECT_USERNAME = new AccountDto.Builder()
            .withUsername(USER_USERNAME)
            .withPassword(ADMIN_PASSWORD)
            .build();
    private static final AccountDto ADMIN_ACCOUNT_DTO_WITH_INCORRECT_PASSWORD = new AccountDto.Builder()
            .withUsername(ADMIN_USERNAME)
            .withPassword(USER_PASSWORD)
            .build();
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private LoginServiceImpl underTest;

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new LoginServiceImpl(accountRepository);
    }

    @Test
    public void testSignInPrivilegedShouldSignInAdminWhenTheCredentialsAreCorrect() throws LoginException {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_OUT_ADMIN_ACCOUNT));
        Mockito.when(accountRepository.save(LOGGED_OUT_ADMIN_ACCOUNT)).thenReturn(LOGGED_OUT_ADMIN_ACCOUNT);

        // When
        underTest.signInPrivileged(ADMIN_ACCOUNT_DTO);

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
    public void testSignInPrivilegedShouldThrowLoginExceptionWhenTheUsernameIsIncorrect() {
        // Given

        // When
        Assertions.assertThrows(LoginException.class, () -> underTest.signInPrivileged(ADMIN_ACCOUNT_DTO_WITH_INCORRECT_USERNAME));

        // Then
    }

    @Test
    public void testSignInPrivilegedShouldThrowLoginExceptionWhenThePasswordIsIncorrect() {
        // Given

        // When
        Assertions.assertThrows(LoginException.class, () -> underTest.signInPrivileged(ADMIN_ACCOUNT_DTO_WITH_INCORRECT_PASSWORD));

        // Then
    }

    @Test
    public void testSignInPrivilegedShouldThrowLoginExceptionWhenTheAdminAccountIsNotInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(LoginException.class, () -> underTest.signInPrivileged(ADMIN_ACCOUNT_DTO));

        // Then
        Mockito.verify(accountRepository).findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testSignInPrivilegedShouldThrowLoginExceptionWhenTheAdminAlreadySignedIn() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));

        // When
        Assertions.assertThrows(LoginException.class, () -> underTest.signInPrivileged(ADMIN_ACCOUNT_DTO));

        // Then
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS))
                .findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testSignOutShouldSignOutAdminWhenTheAdminAccountIsInTheRepository() throws LoginException {
        // Given
        Mockito.when(accountRepository.findByIsSigned(true)).thenReturn(java.util.Optional.of(LOGGED_IN_ADMIN_ACCOUNT));
        Mockito.when(accountRepository.save(LOGGED_IN_ADMIN_ACCOUNT)).thenReturn(LOGGED_IN_ADMIN_ACCOUNT);

        // When
        underTest.signOut();

        // Then
        Mockito.verify(accountRepository).save(LOGGED_IN_ADMIN_ACCOUNT);
        Assertions.assertEquals(ADMIN_USERNAME, LOGGED_IN_ADMIN_ACCOUNT.getUsername());
        Assertions.assertEquals(ADMIN_PASSWORD, LOGGED_IN_ADMIN_ACCOUNT.getPassword());
        Assertions.assertFalse(LOGGED_IN_ADMIN_ACCOUNT.isSigned());
        Mockito.verify(accountRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByIsSigned(true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testSignOutShouldThrowLoginExceptionWhenTheAdminAccountIsNotInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByIsSigned(true)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(LoginException.class, () -> underTest.signOut());

        // Then
        Mockito.verify(accountRepository).findByIsSigned(true);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}
