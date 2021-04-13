package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie createMovie(String title, String genre, int runtime);

    Movie updateMovie(String title, String genre, int runtime);

    Movie deleteMovie(String title);

    List<Movie> listMovies();

    int getRuntimeByTitle(String title);


}