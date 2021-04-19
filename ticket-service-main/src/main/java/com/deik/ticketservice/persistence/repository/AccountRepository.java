package com.deik.ticketservice.persistence.repository;

import com.deik.ticketservice.persistence.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {

    Optional<Account> findByUsernameAndPassword(String username, String password);

    Optional<Account> findByisSigned(boolean isSigned);

}
