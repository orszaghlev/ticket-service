package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.repository.MovieRepository;
import com.deik.ticketservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieServiceImplTest {

    private MovieServiceImpl underTest;

    @Test
    public void testCreateMovieShouldCreateAMovieWhenTheMovieIsNotInTheRepository() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie created = new Movie("Sátántangó", "drama", 450);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.empty());

        // When
        underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).save(created);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenTheRepositoryContainsThatMovie() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie deleted = new Movie("Sátántangó", "drama", 450);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteMovie("Sátántangó");

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).delete(deleted);
    }

    @Test
    public void testListMoviesShouldListMoviesWhenTheRepositoryContainsMovies() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);

        // When
        Mockito.when(movieRepository.findAll()).thenReturn(Stream.of(
                new Movie("Sátántangó", "drama", 450)).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listMovies().size() > 0);
    }

}
