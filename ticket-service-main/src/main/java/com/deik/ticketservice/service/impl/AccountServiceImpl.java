package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.AccountService;
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
            accountRepository.save(new Account("admin", "admin", false));
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