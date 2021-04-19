package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.service.ScreeningService;
import com.deik.ticketservice.ui.command.ScreeningCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;

public class ScreeningCommandTest {

    private final static String MOVIE_TITLE = "Sátántangó";
    private final static String ROOM_NAME = "drama";
    private final static String DATE_AS_STRING = "2021-03-14 16:00";

    private ScreeningCommand underTest;

    private ScreeningService screeningService;

    private MovieService movieService;

    private AccountService accountService;

    @BeforeEach
    public void init() {
        screeningService = Mockito.mock(ScreeningService.class);
        movieService = Mockito.mock(MovieService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
    }

    @Test
    public void testCreateScreeningShouldCreateScreeningWhenAdminIsSignedInAndNoOverlapOccurs() throws ParseException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(screeningService, Mockito.times(1))
                .createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenAdminIsSignedIn() throws ParseException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(screeningService, Mockito.times(1))
                .deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldNotDeleteScreeningWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

}
