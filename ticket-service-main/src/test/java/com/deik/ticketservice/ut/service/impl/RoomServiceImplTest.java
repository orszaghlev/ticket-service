package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.persistence.entity.Room;
import com.deik.ticketservice.persistence.repository.RoomRepository;
import com.deik.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomServiceImplTest {

    private RoomServiceImpl underTest;

    @Test
    public void testCreateRoomShouldCreateRoomWhenTheRoomIsNotInTheRepository() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
        Room created = new Room("Pedersoli", 20, 10);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.empty());

        // When
        underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Mockito.verify(roomRepository, Mockito.times(1)).save(created);
        Mockito.verify(roomRepository, Mockito.times(1)).findByName("Pedersoli");
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testUpdateRoomShouldUpdateRoomWhenTheOriginalRoomIsInTheRepository() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
        Room existing = new Room("Pedersoli", 20, 1);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(existing));
        Mockito.when(roomRepository.save(existing)).thenReturn(existing);

        // When
        underTest.updateRoom("Pedersoli", 10, 10);

        // Then
        Mockito.verify(roomRepository, Mockito.times(1)).save(existing);
        Assertions.assertEquals("Pedersoli", existing.getName());
        Assertions.assertEquals(10, existing.getNumberOfRows());
        Assertions.assertEquals(10, existing.getNumberOfCols());
        Mockito.verify(roomRepository, Mockito.times(2)).findByName("Pedersoli");
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenTheRepositoryContainsTheRoom() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
        Room deleted = new Room("Pedersoli", 20, 10);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteRoom("Pedersoli");

        // Then
        Mockito.verify(roomRepository, Mockito.times(1)).delete(deleted);
        Mockito.verify(roomRepository, Mockito.times(2)).findByName("Pedersoli");
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

    @Test
    public void testListRoomsShouldListRoomsWhenTheRepositoryContainsRooms() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);

        // When
        Mockito.when(roomRepository.findAll()).thenReturn(Stream.of(
                new Room("Pedersoli", 20, 10)).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listRooms().size() > 0);
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(roomRepository);
    }

}
