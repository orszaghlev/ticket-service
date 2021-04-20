package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Movie;

import java.util.List;

public interface MovieService {

    void createMovie(String title, String genre, int runtime);

    void updateMovie(String title, String genre, int runtime);

    void deleteMovie(String title);

    List<Movie> listMovies();

    int getRuntimeByTitle(String title);

}