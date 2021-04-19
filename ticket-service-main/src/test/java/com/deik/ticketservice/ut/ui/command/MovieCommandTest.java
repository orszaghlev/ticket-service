package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MovieCommandTest {

    private MovieCommand underTest;

    @Test
    public void testCreateMovieShouldCreateMovieWhenAdminIsSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1))
                .createMovie("Sátántangó", "drama", 450);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateMovieShouldNotCreateMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenAdminIsSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1))
                .updateMovie("Sátántangó", "drama", 450);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldNotUpdateMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateMovie("Sátántangó", "drama", 450);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenAdminIsSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteMovie("Sátántangó");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(movieService, Mockito.times(1)).deleteMovie("Sátántangó");
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldNotDeleteMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteMovie("Sátántangó");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

}
