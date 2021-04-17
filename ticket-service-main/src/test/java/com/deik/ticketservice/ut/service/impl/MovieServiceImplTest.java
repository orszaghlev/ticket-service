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
    public void testCreateMovieShouldCreateMovieWhenTheMovieIsNotInTheRepository() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie created = new Movie("Sátántangó", "drama", 450);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.empty());

        // When
        underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).save(created);
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle("Sátántangó");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenTheOriginalMovieIsInTheRepository() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie existing = new Movie("Sátántangó", "dram", 45);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(existing));
        Mockito.when(movieRepository.save(existing)).thenReturn(existing);

        // When
        underTest.updateMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).save(existing);
        Assertions.assertEquals("Sátántangó", existing.getTitle());
        Assertions.assertEquals("drama", existing.getGenre());
        Assertions.assertEquals(450, existing.getRuntime());
        Mockito.verify(movieRepository, Mockito.times(2)).findByTitle("Sátántangó");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenTheRepositoryContainsTheMovie() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie deleted = new Movie("Sátántangó", "drama", 450);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteMovie("Sátántangó");

        // Then
        Mockito.verify(movieRepository, Mockito.times(1)).delete(deleted);
        Mockito.verify(movieRepository, Mockito.times(2)).findByTitle("Sátántangó");
        Mockito.verifyNoMoreInteractions(movieRepository);
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
        Mockito.verify(movieRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testGetRuntimeByTitleShouldGetRuntimeWhenTheRepositoryContainsTheMovieWithTheGivenTitle() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Movie existing = new Movie("Sátántangó", "drama", 450);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(existing));
        int expected = existing.getRuntime();

        // When
        int actual = underTest.getRuntimeByTitle("Sátántangó");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository, Mockito.times(2)).findByTitle("Sátántangó");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

    @Test
    public void testGetRuntimeByTitleShouldGetZeroWhenTheRepositoryDoesNotContainTheMovieWithTheGivenTitle() {
        // Given
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        underTest = new MovieServiceImpl(movieRepository);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.empty());
        int expected = 0;

        // When
        int actual = underTest.getRuntimeByTitle("Sátántangó");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(movieRepository, Mockito.times(1)).findByTitle("Sátántangó");
        Mockito.verifyNoMoreInteractions(movieRepository);
    }

}
