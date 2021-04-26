package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.LoginException;

public interface LoginService {

    void signInPrivileged(String username, String password) throws LoginException;

    void signOut() throws LoginException;

}
