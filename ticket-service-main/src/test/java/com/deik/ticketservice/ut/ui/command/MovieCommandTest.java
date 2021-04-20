package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MovieCommandTest {

    private static final Movie MOVIE = new Movie("Sátántangó", "drama", 450);

    private MovieCommand underTest;

    private MovieService movieService;

    private AccountService accountService;

    @BeforeEach
    public void init() {
        movieService = Mockito.mock(MovieService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
    }

    @Test
    public void testCreateMovieShouldCreateMovieWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1))
                .createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateMovieShouldNotCreateMovieWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1))
                .updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldNotUpdateMovieWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1)).deleteMovie(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldNotDeleteMovieWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

}
