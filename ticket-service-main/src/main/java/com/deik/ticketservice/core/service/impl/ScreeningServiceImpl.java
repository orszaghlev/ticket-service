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
import com.deik.ticketservice.core.service.model.ScreeningDto;
import com.deik.ticketservice.core.service.model.ScreeningListDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private static final String MOVIE_NOT_FOUND_MESSAGE = "Movie not found in the repository";
    private static final String ROOM_NOT_FOUND_MESSAGE = "Room not found in the repository";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String SCREENING_ALREADY_CREATED_MESSAGE = "Screening is already in the repository";
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
    public void createScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException {
        requireNonNull(screeningDto);
        Movie movie = getMovieByTitle(screeningDto.getMovieTitle());
        Room room = getRoomByName(screeningDto.getRoomName());
        Date date = getDateByString(screeningDto.getDateAsString());
        if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isPresent()) {
            throw new ScreeningException(SCREENING_ALREADY_CREATED_MESSAGE);
        }
        ScreeningId screeningId = new ScreeningId(movie, room, date);
        Screening screeningToCreate = new Screening(screeningId);
        screeningRepository.save(screeningToCreate);
    }

    @Override
    public void deleteScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException {
        requireNonNull(screeningDto);
        Movie movie = getMovieByTitle(screeningDto.getMovieTitle());
        Room room = getRoomByName(screeningDto.getRoomName());
        Date date = getDateByString(screeningDto.getDateAsString());
        if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isEmpty()) {
            throw new ScreeningException(SCREENING_NOT_FOUND_MESSAGE);
        }
        Screening screeningToDelete = screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).get();
        screeningRepository.delete(screeningToDelete);
    }

    @Override
    public List<ScreeningListDto> listScreenings() {
        return screeningRepository.findAll().stream().map(this::convertEntityToListDto).collect(Collectors.toList());
    }

    private Movie getMovieByTitle(String movieTitle) throws MovieException {
        if (movieRepository.findByTitle(movieTitle).isEmpty()) {
            throw new MovieException(MOVIE_NOT_FOUND_MESSAGE);
        }
        return movieRepository.findByTitle(movieTitle).get();
    }

    private Room getRoomByName(String roomName) throws RoomException {
        if (roomRepository.findByName(roomName).isEmpty()) {
            throw new RoomException(ROOM_NOT_FOUND_MESSAGE);
        }
        return roomRepository.findByName(roomName).get();
    }

    private Date getDateByString(String dateAsString) throws ParseException {
        return new SimpleDateFormat(DATE_PATTERN).parse(dateAsString);
    }

    private void requireNonNull(ScreeningDto screeningDto) {
        Objects.requireNonNull(screeningDto, "Screening cannot be null");
        Objects.requireNonNull(screeningDto.getMovieTitle(), "Screening movie title cannot be null");
        Objects.requireNonNull(screeningDto.getRoomName(), "Screening room name cannot be null");
        Objects.requireNonNull(screeningDto.getDateAsString(), "Screening date (as string) cannot be null");
    }

    private ScreeningListDto convertEntityToListDto(Screening screening) {
        return new ScreeningListDto.Builder()
                .withMovie(screening.getId().getMovie())
                .withRoom(screening.getId().getRoom())
                .withDate(screening.getId().getDate())
                .build();
    }

}