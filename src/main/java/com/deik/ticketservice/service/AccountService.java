package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Account;

public interface AccountService {

    void init();
    boolean isAdminSignedIn();
    Account getSignedInAccount();

}
