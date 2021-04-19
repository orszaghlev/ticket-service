package com.deik.ticketservice.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @Column(name = "username")
    @NotBlank
    private String username;
    @Column(name = "password")
    @NotBlank
    private String password;
    @Column(name = "isSigned")
    private boolean isSigned;

    public Account() {

    }

    public Account(@NotBlank String username, @NotBlank String password, boolean isSigned) {
        this.username = username;
        this.password = password;
        this.isSigned = isSigned;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setIsSigned(boolean isSigned) {
        this.isSigned = isSigned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isSigned());
    }

    @Override
    public String toString() {
        return "Account{" + ", username='" + username + '\'' + ", password='" + password + '\'' + ", isSigned="
                + isSigned + '}';
    }

}