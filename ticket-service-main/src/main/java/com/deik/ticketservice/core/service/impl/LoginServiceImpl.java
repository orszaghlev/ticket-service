package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.LoginService;
import com.deik.ticketservice.core.service.exception.LoginException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private final AccountRepository accountRepository;

    public LoginServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void signInPrivileged(String username, String password) throws LoginException {
        if (!username.equals("admin") || !password.equals("admin")) {
            throw new LoginException("Incorrect credentials");
        }
        if (accountRepository.findByUsernameAndPassword(username, password).isEmpty()) {
            throw new LoginException("Admin account not found");
        }
        Account adminAccount = accountRepository.findByUsernameAndPassword(username, password).get();
        adminAccount.setIsSigned(true);
        accountRepository.save(adminAccount);
    }

    @Override
    public void signOut() throws LoginException {
        if (accountRepository.findByisSigned(true).isEmpty()) {
            throw new LoginException("None of the accounts are signed in at the moment");
        }
        Account signedInAccount = accountRepository.findByisSigned(true).get();
        signedInAccount.setIsSigned(false);
        accountRepository.save(signedInAccount);
    }

}
