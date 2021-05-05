package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Account;
import com.deik.ticketservice.core.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.service.LoginService;
import com.deik.ticketservice.core.service.exception.LoginException;
import com.deik.ticketservice.core.service.model.AccountDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String INCORRECT_CREDENTIALS_MESSAGE = "Incorrect credentials";
    private static final String ADMIN_ACCOUNT_NOT_FOUND_MESSAGE = "Admin account not found in the repository";
    private static final String ADMIN_SIGNED_IN_MESSAGE = "Admin already signed in";
    private static final String NO_SIGNED_IN_USERS_MESSAGE = "None of the users are signed in at the moment";

    private final AccountRepository accountRepository;

    public LoginServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void signInPrivileged(AccountDto accountDto) throws LoginException {
        requireNonNull(accountDto);
        if (!accountDto.getUsername().equals(ADMIN_USERNAME) || !accountDto.getPassword().equals(ADMIN_PASSWORD)) {
            throw new LoginException(INCORRECT_CREDENTIALS_MESSAGE);
        }
        if (accountRepository.findByUsernameAndPassword(accountDto.getUsername(), accountDto.getPassword()).isEmpty()) {
            throw new LoginException(ADMIN_ACCOUNT_NOT_FOUND_MESSAGE);
        }
        Account adminAccount = accountRepository.findByUsernameAndPassword(accountDto.getUsername(),
                accountDto.getPassword()).get();
        if (adminAccount.getIsSigned()) {
            throw new LoginException(ADMIN_SIGNED_IN_MESSAGE);
        }
        adminAccount.setIsSigned(true);
        accountRepository.save(adminAccount);
    }

    @Override
    public void signOut() throws LoginException {
        if (accountRepository.findByIsSigned(true).isEmpty()) {
            throw new LoginException(NO_SIGNED_IN_USERS_MESSAGE);
        }
        Account signedInAccount = accountRepository.findByIsSigned(true).get();
        signedInAccount.setIsSigned(false);
        accountRepository.save(signedInAccount);
    }

    private void requireNonNull(AccountDto accountDto) {
        Objects.requireNonNull(accountDto, "Account cannot be null");
        Objects.requireNonNull(accountDto.getUsername(), "Account username cannot be null");
        Objects.requireNonNull(accountDto.getPassword(), "Account password cannot be null");
    }

}
