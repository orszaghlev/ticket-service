package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;

    @Autowired
    public LoginServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void signInPrivileged(String username, String password) {
        if (username.equals("admin") && password.equals("admin")
                && accountRepository.findByUsernameAndPassword(username, password).isPresent()) {
            Account adminAccount = accountRepository.findByUsernameAndPassword(username, password).get();
            adminAccount.setIsSigned(true);
            accountRepository.save(adminAccount);
        }
    }

    @Override
    public void signOut() {
        if (accountRepository.findByisSigned(true).isPresent()) {
            Account signedInAccount = accountRepository.findByisSigned(true).get();
            signedInAccount.setIsSigned(false);
            accountRepository.save(signedInAccount);
        }
    }

}
