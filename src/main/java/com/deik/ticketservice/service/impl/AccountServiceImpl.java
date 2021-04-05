package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        if (accountRepository.findByUsernameAndPassword("admin", "admin").isEmpty()) {
            accountRepository.save(new Account(1, "admin", "admin", false));
        }
    }

    public boolean describeAccount() {
        Account adminAccount = accountRepository.findByUsernameAndPassword("admin", "admin").get();
        return adminAccount.isLogged();
    }

    public Account getAdminAccount() {
        return accountRepository.findByUsernameAndPassword("admin", "admin").get();
    }

}
