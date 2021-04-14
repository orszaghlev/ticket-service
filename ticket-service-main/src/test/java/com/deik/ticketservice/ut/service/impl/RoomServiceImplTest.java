package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RoomServiceImplTest {

    private RoomServiceImpl underTest;

    @Test
    public void testCreateRoomShouldCreateARoomWhenTheRoomIsNotInTheRepository() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
        Room created = new Room("Pedersoli", 20, 10);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.empty());

        // When
        underTest.createRoom("Pedersoli", 20, 10);

        // Then
        Mockito.verify(roomRepository, Mockito.times(1)).save(created);
    }

    @Test
    public void testDeleteRoomShouldDeleteRoomWhenTheRepositoryContainsThatRoom() {
        // Given
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new RoomServiceImpl(roomRepository);
        Room deleted = new Room("Pedersoli", 20, 10);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteRoom("Pedersoli");

        // Then
        Mockito.verify(roomRepository, Mockito.times(1)).delete(deleted);
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
    }

}