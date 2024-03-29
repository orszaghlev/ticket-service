package com.deik.ticketservice.ut.core.screening.impl;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.room.persistence.entity.Room;
import com.deik.ticketservice.core.screening.persistence.entity.Screening;
import com.deik.ticketservice.core.screening.persistence.entity.id.ScreeningId;
import com.deik.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.room.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.room.exception.RoomException;
import com.deik.ticketservice.core.screening.exception.ScreeningException;
import com.deik.ticketservice.core.screening.impl.ScreeningServiceImpl;
import com.deik.ticketservice.core.screening.model.ScreeningDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScreeningServiceImplTest {

    private static final String MOVIE_TITLE = "Sátántangó";
    private static final String ROOM_NAME = "Pedersoli";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String DATE_AS_STRING = "2021-03-14 16:00";
    private static final ScreeningDto SCREENING_DTO = new ScreeningDto.Builder()
            .withMovieTitle(MOVIE_TITLE)
            .withRoomName(ROOM_NAME)
            .withDateAsString(DATE_AS_STRING)
            .build();
    private static final int WANTED_NUMBER_OF_INVOCATIONS = 2;

    private ScreeningServiceImpl underTest;

    private ScreeningRepository screeningRepository;

    private MovieRepository movieRepository;

    private RoomRepository roomRepository;

    @BeforeEach
    public void init() {
        screeningRepository = Mockito.mock(ScreeningRepository.class);
        movieRepository = Mockito.mock(MovieRepository.class);
        roomRepository = Mockito.mock(RoomRepository.class);
        underTest = new ScreeningServiceImpl(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    public void testCreateScreeningShouldCreateScreeningWhenTheScreeningIsNotInTheRepository() throws ParseException,
            ScreeningException, RoomException, MovieException {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(DATE_AS_STRING);
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);
        Screening created = new Screening(screeningId);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.empty());

        // When
        underTest.createScreening(SCREENING_DTO);

        // Then
        Mockito.verify(screeningRepository).save(created);
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM_NAME);
        Mockito.verify(screeningRepository)
                .findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository, existingMovie,
                existingRoom);
    }

    @Test
    public void testCreateScreeningShouldThrowMovieExceptionWhenTheMovieIsNotInTheRepository() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(MovieException.class, () -> underTest.createScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository).findByTitle(MOVIE_TITLE);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    public void testCreateScreeningShouldThrowRoomExceptionWhenTheRoomIsNotInTheRepository() {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(RoomException.class, () -> underTest.createScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository).findByName(ROOM_NAME);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    public void testCreateScreeningShouldThrowScreeningExceptionWhenTheScreeningIsInTheRepository() throws ParseException {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(DATE_AS_STRING);
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);
        Screening created = new Screening(screeningId);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.of(created));

        // When
        Assertions.assertThrows(ScreeningException.class, () -> underTest.createScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM_NAME);
        Mockito.verify(screeningRepository)
                .findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository, existingMovie,
                existingRoom);
    }

    @Test
    public void testDeleteScreeningShouldDeleteScreeningWhenTheScreeningIsInTheRepository() throws ParseException,
            ScreeningException, RoomException, MovieException {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(DATE_AS_STRING);
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);
        Screening deleted = new Screening(screeningId);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.of(deleted));

        // When
        underTest.deleteScreening(SCREENING_DTO);

        // Then
        Mockito.verify(screeningRepository).delete(deleted);
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM_NAME);
        Mockito.verify(screeningRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS))
                .findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository, existingMovie,
                existingRoom);
    }

    @Test
    public void testDeleteScreeningShouldThrowMovieExceptionWhenTheMovieIsNotInTheRepository() {
        // Given
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(MovieException.class, () -> underTest.deleteScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository).findByTitle(MOVIE_TITLE);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    public void testDeleteScreeningShouldThrowRoomExceptionWhenTheRoomIsNotInTheRepository() {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(RoomException.class, () -> underTest.deleteScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository).findByName(ROOM_NAME);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository);
    }

    @Test
    public void testDeleteScreeningShouldThrowScreeningExceptionWhenTheScreeningIsNotInTheRepository() throws ParseException {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Mockito.when(movieRepository.findByTitle(MOVIE_TITLE)).thenReturn(java.util.Optional.of(existingMovie));
        Room existingRoom = Mockito.mock(Room.class);
        Mockito.when(roomRepository.findByName(ROOM_NAME)).thenReturn(java.util.Optional.of(existingRoom));
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(DATE_AS_STRING);
        Mockito.when(screeningRepository.findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date))
                .thenReturn(java.util.Optional.empty());

        // When
        Assertions.assertThrows(ScreeningException.class, () -> underTest.deleteScreening(SCREENING_DTO));

        // Then
        Mockito.verify(movieRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByTitle(MOVIE_TITLE);
        Mockito.verify(roomRepository, Mockito.times(WANTED_NUMBER_OF_INVOCATIONS)).findByName(ROOM_NAME);
        Mockito.verify(screeningRepository).findById_MovieAndId_RoomAndId_Date(existingMovie, existingRoom, date);
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository, existingMovie,
                existingRoom);
    }

    @Test
    public void testListScreeningsShouldListScreeningsWhenTheRepositoryContainsScreenings() throws ParseException {
        // Given
        Movie existingMovie = Mockito.mock(Movie.class);
        Room existingRoom = Mockito.mock(Room.class);
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(DATE_AS_STRING);
        ScreeningId screeningId = new ScreeningId(existingMovie, existingRoom, date);

        // When
        Mockito.when(screeningRepository.findAll()).thenReturn(Stream.of(
                new Screening(screeningId)).collect(Collectors.toList()));

        // Then
        Assertions.assertTrue(underTest.listScreenings().size() > 0);
        Mockito.verify(screeningRepository).findAll();
        Mockito.verifyNoMoreInteractions(screeningRepository, movieRepository, roomRepository, existingMovie,
                existingRoom);
    }

}
