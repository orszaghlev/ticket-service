package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Movie;

import java.util.List;

public interface MovieService {

    void createMovie(String title, String genre, int runtime);
    void updateMovie(String title, String genre, int runtime);
    void deleteMovie(String title);
    List<Movie> listMovies();

}