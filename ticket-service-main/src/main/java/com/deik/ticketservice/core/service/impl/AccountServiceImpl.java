package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsernameAndPassword("admin", "admin").isEmpty()) {
            accountRepository.save(new Account(1, "admin", "admin", false));
        }
    }

    @Override
    public boolean isAdminSignedIn() {
        if (accountRepository.findByUsernameAndPassword("admin", "admin").isPresent()) {
            Account adminAccount = accountRepository.findByUsernameAndPassword("admin", "admin").get();
            return adminAccount.isSigned();
        }
        return false;
    }

    @Override
    public Account getSignedInAccount() {
        Optional<Account> signedInAccount = accountRepository.findByisSigned(true);
        return signedInAccount.orElse(null);
    }

}