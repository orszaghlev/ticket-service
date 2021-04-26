package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.exception.AccountException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsernameAndPassword("admin", "admin").isEmpty()) {
            accountRepository.save(new Account(null, "admin", "admin", false));
        }
    }

    @Override
    public boolean isAdminSignedIn() throws AccountException {
        if (accountRepository.findByUsernameAndPassword("admin", "admin").isEmpty()) {
            throw new AccountException("Admin account not found");
        }
        Account adminAccount = accountRepository.findByUsernameAndPassword("admin", "admin").get();
        return adminAccount.isSigned();
    }

    @Override
    public Account getSignedInAccount() throws AccountException {
        if (accountRepository.findByisSigned(true).isEmpty()) {
            throw new AccountException("None of the accounts are signed in at the moment");
        }
        return accountRepository.findByisSigned(true).get();
    }

}