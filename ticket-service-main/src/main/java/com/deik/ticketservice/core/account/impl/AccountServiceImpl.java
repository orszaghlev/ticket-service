package com.deik.ticketservice.core.account.impl;

import com.deik.ticketservice.core.account.persistence.entity.Account;
import com.deik.ticketservice.core.account.persistence.repository.AccountRepository;
import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.account.exception.AccountException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Account.Role ADMIN_ROLE = Account.Role.ADMIN;
    private static final String ADMIN_ACCOUNT_NOT_FOUND_MESSAGE = "Admin account not found in the repository";
    private static final String NO_SIGNED_IN_PRIVILEGED_USERS_MESSAGE =
            "None of the privileged users are signed in at the moment";

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean isAdminSignedIn() throws AccountException {
        if (accountRepository.findByRole(ADMIN_ROLE).isEmpty()) {
            throw new AccountException(ADMIN_ACCOUNT_NOT_FOUND_MESSAGE);
        }
        return accountRepository.findByRole(ADMIN_ROLE).get().getIsSigned();
    }

    @Override
    public String getUsernameOfSignedInPrivilegedAccount() throws AccountException {
        if (accountRepository.findByRoleAndIsSigned(ADMIN_ROLE, true).isEmpty()) {
            throw new AccountException(NO_SIGNED_IN_PRIVILEGED_USERS_MESSAGE);
        }
        return accountRepository.findByRoleAndIsSigned(ADMIN_ROLE, true).get().getUsername();
    }

}