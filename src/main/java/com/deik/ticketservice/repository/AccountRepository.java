package com.deik.ticketservice.repository;

import com.deik.ticketservice.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByUsernameAndPassword(String username, String password);

}
