package com.deik.ticketservice.core.account;

import com.deik.ticketservice.core.account.exception.LoginException;
import com.deik.ticketservice.core.account.model.AccountDto;

public interface LoginService {

    void signInPrivileged(AccountDto accountDto) throws LoginException;

    void signOut() throws LoginException;

}
