package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.movie.MovieService;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.movie.model.MovieDto;
import com.deik.ticketservice.ui.command.MovieCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MovieCommandTest {

    private static final Movie MOVIE = new Movie(null, "Sátántangó", "drama", 450);
    private static final MovieDto MOVIE_DTO = new MovieDto.Builder()
            .withTitle(MOVIE.getTitle())
            .withGenre(MOVIE.getGenre())
            .withRuntime(MOVIE.getRuntime())
            .build();

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
    public void testCreateMovieShouldCreateMovieWhenTheAdminIsSignedIn() throws AccountException, MovieException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(movieService).createMovie(MOVIE_DTO);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateMovieShouldNotCreateMovieWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateMovieShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.createMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldUpdateMovieWhenTheAdminIsSignedIn() throws AccountException, MovieException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(movieService).updateMovie(MOVIE_DTO);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldNotUpdateMovieWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testUpdateMovieShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.updateMovie(MOVIE.getTitle(), MOVIE.getGenre(), MOVIE.getRuntime());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldDeleteMovieWhenTheAdminIsSignedIn() throws AccountException, MovieException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(movieService).deleteMovie(MOVIE.getTitle());
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldNotDeleteMovieWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testDeleteMovieShouldCatchExceptionWhenTheAdminAccountIsNotInTheRepository() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.deleteMovie(MOVIE.getTitle());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

}
