package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.exception.AccountException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final Account ADMIN_ACCOUNT = new Account(null, ADMIN_USERNAME, ADMIN_PASSWORD, false);
    private static final String ADMIN_ACCOUNT_NOT_FOUND_MESSAGE = "Admin account not found in the repository";
    private static final String NO_SIGNED_IN_USERS_MESSAGE = "None of the users are signed in at the moment";

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD).isEmpty()) {
            accountRepository.save(ADMIN_ACCOUNT);
        }
    }

    @Override
    public boolean isAdminSignedIn() throws AccountException {
        if (accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD).isEmpty()) {
            throw new AccountException(ADMIN_ACCOUNT_NOT_FOUND_MESSAGE);
        }
        Account adminAccount = accountRepository.findByUsernameAndPassword(ADMIN_USERNAME, ADMIN_PASSWORD).get();
        return adminAccount.isSigned();
    }

    @Override
    public Account getSignedInAccount() throws AccountException {
        if (accountRepository.findByIsSigned(true).isEmpty()) {
            throw new AccountException(NO_SIGNED_IN_USERS_MESSAGE);
        }
        return accountRepository.findByIsSigned(true).get();
    }

}