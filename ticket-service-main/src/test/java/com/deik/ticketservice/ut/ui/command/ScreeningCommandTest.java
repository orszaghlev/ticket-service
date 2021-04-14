package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.service.ScreeningService;
import com.deik.ticketservice.ui.command.ScreeningCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ScreeningCommandTest {

    private ScreeningCommand underTest;

    @Test
    public void testCreateScreeningShouldCreateScreeningWhenAdminIsSignedInAndOverlapCannotHappen() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Created screening";

        // When
        String actual = underTest.createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testCreateScreeningShouldNotCreateScreeningWhenAdminIsNotSignedIn() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenAdminIsSignedIn() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Deleted screening";

        // When
        String actual = underTest.deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteScreeningShouldNotDeleteScreeningWhenAdminIsNotSignedIn() {
        // Given
        ScreeningService screeningService = Mockito.mock(ScreeningService.class);
        MovieService movieService = Mockito.mock(MovieService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new ScreeningCommand(screeningService, movieService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

}
