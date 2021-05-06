package com.deik.ticketservice.core.account.model;

import java.util.Objects;

public class AccountDto {

    private final String username;
    private final String password;

    private AccountDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
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
        return getUsername().equals(that.getUsername()) && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword());
    }

    @Override
    public String toString() {
        return "AccountDto{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }

    public static class Builder {

        private String username;
        private String password;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public AccountDto build() {
            return new AccountDto(username, password);
        }

    }

}
