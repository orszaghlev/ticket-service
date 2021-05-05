package com.deik.ticketservice.ut.core.configuration;

import com.deik.ticketservice.core.configuration.InMemoryDbInitializer;
import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InMemoryDbInitializerTest {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account.Role ADMIN_ROLE = Account.Role.ADMIN;
    private static final Account LOGGED_OUT_ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ROLE, false);

    private InMemoryDbInitializer underTest;

    private AccountRepository accountRepository;

    @BeforeEach
    public void init() {
        accountRepository = Mockito.mock(AccountRepository.class);
        underTest = new InMemoryDbInitializer(accountRepository);
    }

    @Test
    public void testInitShouldCreateAdminAccountWhenTheAdminAccountIsNotInTheRepository() {
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
    public void testInitShouldNotCreateAdminAccountWhenTheAdminAccountIsInTheRepository() {
        // Given
        Mockito.when(accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD))
                .thenReturn(java.util.Optional.of(LOGGED_OUT_ADMIN_ACCOUNT));

        // When
        underTest.init();

        // Then
        Mockito.verify(accountRepository).findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD);
        Mockito.verifyNoMoreInteractions(accountRepository);
    }

}
