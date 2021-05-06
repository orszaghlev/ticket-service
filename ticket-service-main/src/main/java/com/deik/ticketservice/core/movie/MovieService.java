package com.deik.ticketservice.core.movie;

import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.movie.model.MovieDto;

import java.util.List;

public interface MovieService {

    void createMovie(MovieDto movieDto) throws MovieException;

    void updateMovie(MovieDto movieDto) throws MovieException;

    void deleteMovie(String title) throws MovieException;

    List<MovieDto> listMovies();

    int getRuntimeByTitle(String title) throws MovieException;

}