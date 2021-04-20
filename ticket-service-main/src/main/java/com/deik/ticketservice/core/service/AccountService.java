package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Account;

public interface AccountService {

    void init();

    boolean isAdminSignedIn();

    Account getSignedInAccount();

}
