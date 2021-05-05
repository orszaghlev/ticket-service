package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.AccountException;

public interface AccountService {

    boolean isAdminSignedIn() throws AccountException;

    String getUsernameOfSignedInPrivilegedAccount() throws AccountException;

}
