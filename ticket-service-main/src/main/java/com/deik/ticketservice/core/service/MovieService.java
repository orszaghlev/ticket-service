package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.service.exception.MovieException;

import java.util.List;

public interface MovieService {

    void createMovie(String title, String genre, int runtime) throws MovieException;

    void updateMovie(String title, String genre, int runtime) throws MovieException;

    void deleteMovie(String title) throws MovieException;

    List<Movie> listMovies();

    int getRuntimeByTitle(String title) throws MovieException;

}