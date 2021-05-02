package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.core.service.ScreeningService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.exception.ScreeningException;
import com.deik.ticketservice.ui.command.ScreeningCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;

public class ScreeningCommandTest {

    private static final String MOVIE_TITLE = "Sátántangó";
    private static final String ROOM_NAME = "drama";
    private static final String DATE_AS_STRING = "2021-03-14 16:00";

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
    public void testCreateScreeningShouldCreateScreeningWhenTheAdminIsSignedInAndNoOverlapOccurs() throws ParseException,
            AccountException, ScreeningException, RoomException, MovieException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(screeningService).createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenTheAdminIsSignedIn() throws ParseException, AccountException,
            ScreeningException, RoomException, MovieException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(screeningService).deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldNotDeleteScreeningWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteScreening(MOVIE_TITLE, ROOM_NAME, DATE_AS_STRING);

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

}
