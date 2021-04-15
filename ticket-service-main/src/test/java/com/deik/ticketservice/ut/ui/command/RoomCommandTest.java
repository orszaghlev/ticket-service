package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.RoomService;
import com.deik.ticketservice.ui.command.RoomCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoomCommandTest {

    private RoomCommand underTest;

    @Test
    public void testCreateRoomShouldCreateRoomWhenAdminIsSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Created room";

        // When
        String actual = underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testCreateRoomShouldNotCreateRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenAdminIsSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Updated room";

        // When
        String actual = underTest.updateRoom("Pedersoli", 10, 10);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testUpdateRoomShouldNotUpdateRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.updateRoom("Pedersoli", 10, 10);

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenAdminIsSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);
        String expected = "Deleted room";

        // When
        String actual = underTest.deleteRoom("Pedersoli");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

    @Test
    public void testDeleteRoomShouldNotDeleteRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);
        String expected = "";

        // When
        String actual = underTest.deleteRoom("Pedersoli");

        // Then
        Assertions.assertEquals(expected, actual);
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService);
    }

}
