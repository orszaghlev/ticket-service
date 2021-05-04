package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.LoginException;
import com.deik.ticketservice.core.service.model.AccountDto;

public interface LoginService {

    void signInPrivileged(AccountDto accountDto) throws LoginException;

    void signOut() throws LoginException;

}
