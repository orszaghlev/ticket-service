package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.persistence.entity.Screening;
import com.deik.ticketservice.core.persistence.entity.id.ScreeningId;
import com.deik.ticketservice.core.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.persistence.repository.ScreeningRepository;
import com.deik.ticketservice.core.service.ScreeningService;
import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.exception.ScreeningException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found in the repository";
    private static final String ROOM_NOT_FOUND_MESSAGE = "Room not found in the repository";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String SCREENING_ALREADY_CREATED_MESSAGE = "Screening already created";
    private static final String SCREENING_NOT_FOUND_MESSAGE = "Screening not found in the repository";

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository,
                                RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createScreening(String movieTitle, String roomName, String dateAsString) throws ParseException,
            MovieException, RoomException, ScreeningException {
        if (movieRepository.findByTitle(movieTitle).isEmpty()) {
            throw new MovieException(MOVIE_NOT_FOUND_MESSAGE);
        }
        if (roomRepository.findByName(roomName).isEmpty()) {
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
        }
        Movie movie = movieRepository.findByTitle(movieTitle).get();
        Room room = roomRepository.findByName(roomName).get();
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(dateAsString);
        if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isPresent()) {
            throw new ScreeningException(SCREENING_ALREADY_CREATED_MESSAGE);
        }
        ScreeningId screeningId = new ScreeningId(movie, room, date);
        Screening screeningToCreate = new Screening(screeningId);
        screeningRepository.save(screeningToCreate);
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String dateAsString) throws ParseException,
            MovieException, RoomException, ScreeningException {
        if (movieRepository.findByTitle(movieTitle).isEmpty()) {
            throw new MovieException(MOVIE_NOT_FOUND_MESSAGE);
        }
        if (roomRepository.findByName(roomName).isEmpty()) {
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
        }
        Movie movie = movieRepository.findByTitle(movieTitle).get();
        Room room = roomRepository.findByName(roomName).get();
        Date date = new SimpleDateFormat(DATE_PATTERN).parse(dateAsString);
        if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isEmpty()) {
            throw new ScreeningException(SCREENING_NOT_FOUND_MESSAGE);
        }
        Screening screeningToDelete = screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).get();
        screeningRepository.delete(screeningToDelete);
    }

    @Override
    public List<Screening> listScreenings() {
        List<Screening> screenings = new LinkedList<>();
        screeningRepository.findAll().forEach(screenings::add);
        return screenings;
    }

}