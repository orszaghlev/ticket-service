package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.core.service.exception.MovieException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String MOVIE_ALREADY_CREATED_MESSAGE = "Movie already created";
    private static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found in the repository";

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createMovie(String title, String genre, int runtime) throws MovieException {
        if (movieRepository.findByTitle(title).isPresent()) {
            throw new MovieException(MOVIE_ALREADY_CREATED_MESSAGE);
        }
        Movie movieToCreate = new Movie(null, title, genre, runtime);
        movieRepository.save(movieToCreate);
    }

    @Override
    public void updateMovie(String title, String genre, int runtime) throws MovieException {
        Movie movieToUpdate = getMovieByTitle(title);
        movieToUpdate.setTitle(title);
        movieToUpdate.setGenre(genre);
        movieToUpdate.setRuntime(runtime);
        movieRepository.save(movieToUpdate);
    }

    @Override
    public void deleteMovie(String title) throws MovieException {
        Movie movieToDelete = getMovieByTitle(title);
        movieRepository.delete(movieToDelete);
    }

    @Override
    public List<Movie> listMovies() {
        List<Movie> movies = new LinkedList<>();
        movieRepository.findAll().forEach(movies::add);
        return movies;
    }

    @Override
    public int getRuntimeByTitle(String title) throws MovieException {
        return getMovieByTitle(title).getRuntime();
    }

    private Movie getMovieByTitle(String title) throws MovieException {
        if (movieRepository.findByTitle(title).isEmpty()) {
            throw new MovieException(MOVIE_NOT_FOUND_MESSAGE);
        }
        return movieRepository.findByTitle(title).get();
    }

}
