package com.deik.ticketservice.repository;

import com.deik.ticketservice.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Optional<Movie> findByTitle(String title);

}
