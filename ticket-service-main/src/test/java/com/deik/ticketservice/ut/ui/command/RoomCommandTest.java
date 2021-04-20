package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.RoomService;
import com.deik.ticketservice.ui.command.RoomCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoomCommandTest {

    private final static Room ROOM = new Room("Pedersoli", 20, 10);

    private RoomCommand underTest;

    private RoomService roomService;

    private AccountService accountService;

    @BeforeEach
    public void init() {
        roomService = Mockito.mock(RoomService.class);
        accountService = Mockito.mock(AccountService.class);
        underTest = new RoomCommand(roomService, accountService);
    }

    @Test
    public void testCreateRoomShouldCreateRoomWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1))
                .createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testCreateRoomShouldNotCreateRoomWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1))
                .updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldNotUpdateRoomWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenAdminIsSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verify(roomService, Mockito.times(1)).deleteRoom(ROOM.getName());
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldNotDeleteRoomWhenAdminIsNotSignedIn() {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(accountService, Mockito.times(1)).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

}
