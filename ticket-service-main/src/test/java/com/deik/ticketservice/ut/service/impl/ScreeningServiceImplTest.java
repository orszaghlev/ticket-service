package com.deik.ticketservice.ut.service.impl;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.entity.id.ScreeningId;
import com.deik.ticketservice.repository.MovieRepository;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.repository.ScreeningRepository;
import com.deik.ticketservice.service.impl.ScreeningServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreeningServiceImplTest {

    private ScreeningServiceImpl underTest;

    @Test
    public void testCreateScreeningShouldCreateScreeningWhenTheScreeningIsNotInTheRepository() throws ParseException {
        // Given
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-03-14 16:00");
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);
        Screening created = new Screening(screeningId);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.empty());

        // When
        underTest.createScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(screeningRepository, Mockito.times(1)).save(created);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenTheRepositoryContainsTheScreening() throws ParseException {
        // Given
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-03-14 16:00");
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);
        Screening deleted = new Screening(screeningId);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteScreening("Sátántangó", "Pedersoli", "2021-03-14 16:00");

        // Then
        Mockito.verify(screeningRepository, Mockito.times(1)).delete(deleted);
    }

    @Test
    public void testListScreeningsShouldListScreeningsWhenTheRepositoryContainsScreenings() throws ParseException {
        // Given
        ScreeningRepository screeningRepository = Mockito.mock(ScreeningRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        RoomRepository roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle("Sátántangó")).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName("Pedersoli")).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2021-03-14 16:00");
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);

        // When
        Mockito.when(screeningRepository.findAll()).thenReturn(Stream.of(
                new Screening(screeningId)).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listScreenings().size() > 0);
    }

}
