package com.deik.ticketservice.core.service.model;

import java.util.Objects;

public class AccountDto {

    private final String username;
    private final String password;
    private final boolean isSigned;

    public AccountDto(String username, String password, boolean isSigned) {
        this.username = username;
        this.password = password;
        this.isSigned = isSigned;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isSigned() {
        return isSigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountDto)) {
            return false;
        }
        AccountDto that = (AccountDto) o;
        return isSigned() == that.isSigned() && getUsername().equals(that.getUsername())
                && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isSigned());
    }

    @Override
    public String toString() {
        return "AccountDto{" + "username='" + username + '\'' + ", password='" + password + '\'' + ", isSigned="
                + isSigned + '}';
    }

}
