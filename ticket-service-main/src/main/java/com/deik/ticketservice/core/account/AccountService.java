package com.deik.ticketservice.core.account;

import com.deik.ticketservice.core.account.exception.AccountException;

public interface AccountService {

    boolean isAdminSignedIn() throws AccountException;

    String getUsernameOfSignedInPrivilegedAccount() throws AccountException;

}
