package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.service.ScreeningService;
import com.deik.ticketservice.ui.command.ScreeningCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;

public class ScreeningCommandTest {

    private ScreeningCommand underTest;

    @Test
    public void testCreateScreeningShouldCreateScreeningWhenAdminIsSignedInAndNoOverlapOccurs() throws ParseException {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(screeningService, Mockito.times(1))
                .createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");
        Mockito.verifyNoMoreInteractions(accountService, movieService);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenAdminIsNotSignedIn() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenAdminIsSignedIn() throws ParseException {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(screeningService, Mockito.times(1))
                .deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

    @Test
    public void testDeleteScreeningShouldNotDeleteScreeningWhenAdminIsNotSignedIn() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, movieService, screeningService);
    }

}
