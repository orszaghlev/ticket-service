package com.deik.ticketservice.core.account.impl;

import com.deik.ticketservice.core.account.persistence.entity.Account;
import com.deik.ticketservice.core.account.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.account.LoginService;
import com.deik.ticketservice.core.account.exception.LoginException;
import com.deik.ticketservice.core.account.model.AccountDto;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String ADMIN_ACCOUNT_NOT_FOUND_MESSAGE = "Admin account not found in the repository";
    private static final String ADMIN_SIGNED_IN_MESSAGE = "Admin already signed in";
    private static final String NO_SIGNED_IN_USERS_MESSAGE = "None of the users are signed in at the moment";
    private static final String ACCOUNT_CANNOT_BE_NULL_MESSAGE = "Account cannot be null";
    private static final String USERNAME_CANNOT_BE_NULL_MESSAGE = "Account username cannot be null";
    private static final String PASSWORD_CANNOT_BE_NULL_MESSAGE = "Account password cannot be null";

    private final AccountRepository accountRepository;

    public LoginServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void signInPrivileged(AccountDto accountDto) throws LoginException {
        requireNonNull(accountDto);
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
        Objects.requireNonNull(accountDto, ACCOUNT_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(accountDto.getUsername(), USERNAME_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(accountDto.getPassword(), PASSWORD_CANNOT_BE_NULL_MESSAGE);
    }

}
