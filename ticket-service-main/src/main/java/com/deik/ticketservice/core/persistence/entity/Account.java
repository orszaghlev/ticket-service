package com.deik.ticketservice.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "isSigned")
    private boolean isSigned;

    public Account() {

    }

    public Account(Integer id, String username, String password, Role role, boolean isSigned) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isSigned = isSigned;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setIsSigned(boolean signed) {
        isSigned = signed;
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
        return isSigned() == account.isSigned() && getUsername().equals(account.getUsername())
                && getPassword().equals(account.getPassword()) && getRole() == account.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getRole(), isSigned());
    }

    @Override
    public String toString() {
        return "Account{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\''
                + ", role=" + role + ", isSigned=" + isSigned + '}';
    }

    public static enum Role {

        ADMIN, USER

    }

}