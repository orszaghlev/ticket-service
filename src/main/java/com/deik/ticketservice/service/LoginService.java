package com.deik.ticketservice.service;

public interface LoginService {

    void signInPrivileged(String username, String password);
    void signOut();

}
