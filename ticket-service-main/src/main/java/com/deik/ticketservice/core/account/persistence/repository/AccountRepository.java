package com.deik.ticketservice.core.account.persistence.repository;

import com.deik.ticketservice.core.account.persistence.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findByRoleAndIsSigned(Account.Role role, boolean isSigned);

    Optional<Account> findByRole(Account.Role role);

    Optional<Account> findByIsSigned(boolean isSigned);

}
