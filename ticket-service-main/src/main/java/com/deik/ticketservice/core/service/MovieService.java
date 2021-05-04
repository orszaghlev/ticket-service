package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.model.MovieDto;

import java.util.List;

public interface MovieService {

    void createMovie(MovieDto movieDto) throws MovieException;

    void updateMovie(MovieDto movieDto) throws MovieException;

    void deleteMovie(String title) throws MovieException;

    List<MovieDto> listMovies();

    int getRuntimeByTitle(String title) throws MovieException;

}