package com.deik.ticketservice.core.service;

public interface LoginService {

    void signInPrivileged(String username, String password);

    void signOut();

}
