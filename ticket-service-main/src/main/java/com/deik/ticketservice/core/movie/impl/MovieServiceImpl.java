package com.deik.ticketservice.core.movie.impl;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.movie.MovieService;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.movie.model.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String MOVIE_ALREADY_CREATED_MESSAGE = "Movie is already in the repository";
    private static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found in the repository";
    private static final String MOVIE_CANNOT_BE_NULL_MESSAGE = "Movie cannot be null";
    private static final String TITLE_CANNOT_BE_NULL_MESSAGE = "Movie title cannot be null";
    private static final String GENRE_CANNOT_BE_NULL_MESSAGE = "Movie genre cannot be null";

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createMovie(MovieDto movieDto) throws MovieException {
        requireNonNull(movieDto);
        if (movieRepository.findByTitle(movieDto.getTitle()).isPresent()) {
            throw new MovieException(MOVIE_ALREADY_CREATED_MESSAGE);
        }
        Movie movieToCreate = new Movie(null, movieDto.getTitle(), movieDto.getGenre(), movieDto.getRuntime());
        movieRepository.save(movieToCreate);
    }

    @Override
    public void updateMovie(MovieDto movieDto) throws MovieException {
        requireNonNull(movieDto);
        Movie movieToUpdate = getMovieByTitle(movieDto.getTitle());
        movieToUpdate.setTitle(movieDto.getTitle());
        movieToUpdate.setGenre(movieDto.getGenre());
        movieToUpdate.setRuntime(movieDto.getRuntime());
        movieRepository.save(movieToUpdate);
    }

    @Override
    public void deleteMovie(String title) throws MovieException {
        Movie movieToDelete = getMovieByTitle(title);
        movieRepository.delete(movieToDelete);
    }

    @Override
    public List<MovieDto> listMovies() {
        return movieRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
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

    private void requireNonNull(MovieDto movieDto) {
        Objects.requireNonNull(movieDto, MOVIE_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(movieDto.getTitle(), TITLE_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(movieDto.getGenre(), GENRE_CANNOT_BE_NULL_MESSAGE);
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return new MovieDto.Builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withRuntime(movie.getRuntime())
                .build();
    }

}
