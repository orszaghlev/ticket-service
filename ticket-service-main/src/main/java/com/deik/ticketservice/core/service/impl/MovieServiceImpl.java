package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.model.MovieDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final String MOVIE_ALREADY_CREATED_MESSAGE = "Movie is already in the repository";
    private static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found in the repository";

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
        Objects.requireNonNull(movieDto, "Movie cannot be null");
        Objects.requireNonNull(movieDto.getTitle(), "Movie title cannot be null");
        Objects.requireNonNull(movieDto.getGenre(), "Movie genre cannot be null");
    }

    private MovieDto convertEntityToDto(Movie movie) {
        return new MovieDto.Builder()
                .withTitle(movie.getTitle())
                .withGenre(movie.getGenre())
                .withRuntime(movie.getRuntime())
                .build();
    }

}
