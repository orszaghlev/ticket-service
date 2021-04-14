package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class MovieCommandTest {

    private MovieCommand underTest;

    @Test
    public void testCreateMovieShouldCreateMovieWhenAdminSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Created movie";

        // When
        String actual = underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testCreateMovieShouldNotCreateMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.createMovie("Sátántangó", "drama", 450);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenAdminIsSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Updated movie";

        // When
        String actual = underTest.updateMovie("Sátántangó", "drama", 450);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testUpdateMovieShouldNotUpdateMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.updateMovie("Sátántangó", "drama", 450);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenAdminIsSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Deleted movie";

        // When
        String actual = underTest.deleteMovie("Sátántangó");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteMovieShouldNotDeleteMovieWhenAdminIsNotSignedIn() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.deleteMovie("Sátántangó");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testListMovies() {
        // Given
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new MovieCommand(movieService, accountService);
        List<Movie> expected = movieService.listMovies();

        // When
        List<Movie> actual = underTest.listMovies();

        // Then
        Assertions.assertEquals(expected, actual);
    }

}
