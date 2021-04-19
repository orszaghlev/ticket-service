package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.RoomService;
import com.deik.ticketservice.ui.command.RoomCommand;
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

        // When
        underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1))
                .createRoom("Pedersoli", 20, 10);
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testCreateRoomShouldNotCreateRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenAdminIsSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateRoom("Pedersoli", 10, 10);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1))
                .updateRoom("Pedersoli", 10, 10);
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldNotUpdateRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateRoom("Pedersoli", 10, 10);

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenAdminIsSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteRoom("Pedersoli");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1)).deleteRoom("Pedersoli");
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldNotDeleteRoomWhenAdminIsNotSignedIn() {
        // Given
        RoomService roomService = Mockito.mock(RoomService.class);
        AccountService accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteRoom("Pedersoli");

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

}
