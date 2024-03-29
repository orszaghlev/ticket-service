package com.deik.ticketservice.ut.ui.command;

import com.deik.ticketservice.core.room.persistence.entity.Room;
import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.room.RoomService;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.core.room.exception.RoomException;
import com.deik.ticketservice.core.room.model.RoomDto;
import com.deik.ticketservice.ui.command.RoomCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoomCommandTest {

    private static final Room ROOM = new Room(null, "Pedersoli", 20, 10);
    private static final RoomDto ROOM_DTO = new RoomDto.Builder()
            .withName(ROOM.getName())
            .withNumberOfRows(ROOM.getNumberOfRows())
            .withNumberOfCols(ROOM.getNumberOfCols())
            .build();

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
    public void testCreateRoomShouldCreateRoomWhenTheAdminIsSignedIn() throws AccountException, RoomException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(roomService).createRoom(ROOM_DTO);
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testCreateRoomShouldNotCreateRoomWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testCreateRoomShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.createRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenTheAdminIsSignedIn() throws AccountException, RoomException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(roomService).updateRoom(ROOM_DTO);
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldNotUpdateRoomWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testUpdateRoomShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.updateRoom(ROOM.getName(), ROOM.getNumberOfRows(), ROOM.getNumberOfCols());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenTheAdminIsSignedIn() throws AccountException, RoomException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(true);

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verify(roomService).deleteRoom(ROOM.getName());
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldNotDeleteRoomWhenTheAdminIsNotSignedIn() throws AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenReturn(false);

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

    @Test
    public void testDeleteRoomShouldCatchAccountExceptionWhenTheAdminAccountIsNotInTheRepository() throws
            AccountException {
        // Given
        Mockito.when(accountService.isAdminSignedIn()).thenThrow(AccountException.class);

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(accountService).isAdminSignedIn();
        Mockito.verifyNoMoreInteractions(accountService, roomService);
    }

}
