package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Account;
import com.deik.ticketservice.repository.AccountRepository;
import com.deik.ticketservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AccountRepository accountRepository;

    public void signInPrivileged(String username, String password) {
    }

    public void signOut() {
    }

}
