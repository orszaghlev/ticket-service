package com.deik.ticketservice.ut.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MovieServiceImplTest {

    private static final Movie MOVIE = new Movie(null, "Sátántangó", "drama", 450);
    private static final Movie MOVIE_TO_UPDATE = new Movie(null, "Sátántangó", "dram", 45);
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private MovieServiceImpl underTest;

    private MovieRepository movieRepository;

    @BeforeEach
    public void init() {
        movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
    }

    @Test
    public void testCreateMovieShouldCreateMovieWhenTheMovieIsNotInTheRepository() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle())).thenReturn(java.util.Optional.empty());

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(movieRepository).save(MOVIE);
        Mockito.verify(movieRepository).findByTitle(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenTheOriginalMovieIsInTheRepository() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE_TO_UPDATE.getTitle()))
                .thenReturn(java.util.Optional.of(MOVIE_TO_UPDATE));
        Mockito.when(movieRepository.save(MOVIE_TO_UPDATE)).thenReturn(MOVIE_TO_UPDATE);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(movieRepository).save(MOVIE_TO_UPDATE);
        Assertions.assertEquals(MOVIE.getTitle(), MOVIE_TO_UPDATE.getTitle());
        Assertions.assertEquals(MOVIE.getGenre(), MOVIE_TO_UPDATE.getGenre());
        Assertions.assertEquals(MOVIE.getRuntime(), MOVIE_TO_UPDATE.getRuntime());
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS))
                .findByTitle(MOVIE_TO_UPDATE.getTitle());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenTheRepositoryContainsTheMovie() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle())).thenReturn(java.util.Optional.of(MOVIE));

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(movieRepository).delete(MOVIE);
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testListMoviesShouldListMoviesWhenTheRepositoryContainsMovies() {
        // Given

        // When
        Mockito.when(movieRepository.findAll()).thenReturn(Stream.of(MOVIE).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listMovies().size() > 0);
        Mockito.verify(movieRepository).findAll();
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testGetRuntimeByTitleShouldGetRuntimeWhenTheRepositoryContainsTheMovieWithTheGivenTitle() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle())).thenReturn(java.util.Optional.of(MOVIE));

        // When
        int actual = underTest.getRuntimeByTitle(MOVIE.getTitle());

        // Then
        Assertions.assertEquals(MOVIE.getRuntime(), actual);
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testGetRuntimeByTitleShouldGetZeroWhenTheRepositoryDoesNotContainTheMovieWithTheGivenTitle() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE.getTitle())).thenReturn(java.util.Optional.empty());
        int expected = 0;

        // When
        int actual = underTest.getRuntimeByTitle(MOVIE.getTitle());

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository).findByTitle(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

}
