package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountRepository accountRepository;

    public void signInPrivileged(String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            Account adminAccount = accountRepository.findByUsernameAndPassword(username, password).get();
            adminAccount.setLogged(true);
            accountRepository.save(adminAccount);
        }
    }

    public void signOut() {
        Account adminAccount = accountRepository.findByUsernameAndPassword("admin", "admin").get();
        adminAccount.setLogged(false);
        accountRepository.save(adminAccount);
    }

}
