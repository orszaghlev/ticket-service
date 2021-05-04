package com.deik.ticketservice.ut.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.impl.RoomServiceImpl;
import com.deik.ticketservice.core.service.model.RoomDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomServiceImplTest {

    private static final Room ROOM = new Room(null, "Pedersoli", 20, 10);
    private static final Room ROOM_TO_UPDATE = new Room(null, "Pedersoli", 20, 1);
    private static final RoomDto ROOM_DTO = new RoomDto.Builder()
            .withName(ROOM.getName())
            .withNumberOfRows(ROOM.getNumberOfRows())
            .withNumberOfCols(ROOM.getNumberOfCols())
            .build();
    private static final RoomDto ROOM_TO_UPDATE_DTO = new RoomDto.Builder()
            .withName(ROOM.getName())
            .withNumberOfRows(ROOM.getNumberOfRows())
            .withNumberOfCols(ROOM.getNumberOfCols())
            .build();
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private RoomServiceImpl underTest;

    private RoomRepository roomRepository;

    @BeforeEach
    public void init() {
        roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
    }

    @Test
    public void testCreateRoomShouldCreateRoomWhenTheRoomIsNotInTheRepository() throws RoomException {
        // Given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(java.util.Optional.empty());

        // When
        underTest.createRoom(ROOM_DTO);

        // Then
        Mockito.verify(roomRepository).save(ROOM);
        Mockito.verify(roomRepository).findByName(ROOM.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testCreateRoomShouldThrowRoomExceptionWhenTheRoomIsInTheRepository() {
        // Given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(java.util.Optional.of(ROOM));

        // When
        Assertions.assertThrows(RoomException.class, () -> underTest.createRoom(ROOM_DTO));

        // Then
        Mockito.verify(roomRepository).findByName(ROOM.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenTheOriginalRoomIsInTheRepository() throws RoomException {
        // Given
        Mockito.when(roomRepository.findByName(ROOM_TO_UPDATE.getName()))
                .thenReturn(java.util.Optional.of(ROOM_TO_UPDATE));
        Mockito.when(roomRepository.save(ROOM_TO_UPDATE)).thenReturn(ROOM_TO_UPDATE);

        // When
        underTest.updateRoom(ROOM_TO_UPDATE_DTO);

        // Then
        Mockito.verify(roomRepository).save(ROOM_TO_UPDATE);
        Assertions.assertEquals(ROOM.getName(), ROOM_TO_UPDATE.getName());
        Assertions.assertEquals(ROOM.getNumberOfRows(), ROOM_TO_UPDATE.getNumberOfRows());
        Assertions.assertEquals(ROOM.getNumberOfCols(), ROOM_TO_UPDATE.getNumberOfCols());
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM_TO_UPDATE.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdateRoomShouldThrowRoomExceptionWhenTheOriginalRoomIsNotInTheRepository() {
        // Given
        Mockito.when(roomRepository.findByName(ROOM_TO_UPDATE.getName()))
                .thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(RoomException.class, () -> underTest.updateRoom(ROOM_TO_UPDATE_DTO));

        // Then
        Mockito.verify(roomRepository).findByName(ROOM_TO_UPDATE.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenTheRoomIsInTheRepository() throws RoomException {
        // Given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(java.util.Optional.of(ROOM));

        // When
        underTest.deleteRoom(ROOM.getName());

        // Then
        Mockito.verify(roomRepository).delete(ROOM);
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeleteRoomShouldThrowRoomExceptionWhenTheRoomIsNotInTheRepository() {
        // Given
        Mockito.when(roomRepository.findByName(ROOM.getName())).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(RoomException.class, () -> underTest.deleteRoom(ROOM.getName()));

        // Then
        Mockito.verify(roomRepository).findByName(ROOM.getName());
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testListRoomsShouldListRoomsWhenTheRepositoryContainsRooms() {
        // Given

        // When
        Mockito.when(roomRepository.findAll()).thenReturn(Stream.of(ROOM).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listRooms().size() > 0);
        Mockito.verify(roomRepository).findAll();
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

}
