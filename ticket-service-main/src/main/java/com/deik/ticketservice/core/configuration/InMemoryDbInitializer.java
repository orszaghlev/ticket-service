package com.deik.ticketservice.core.configuration;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class InMemoryDbInitializer {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account.Role ADMIN_ROLE = Account.Role.ADMIN;
    private static final Account ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ROLE,
            false);

    private final AccountRepository accountRepository;

    public InMemoryDbInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD).isEmpty()) {
            accountRepository.save(ADMIN_ACCOUNT);
        }
    }

}
