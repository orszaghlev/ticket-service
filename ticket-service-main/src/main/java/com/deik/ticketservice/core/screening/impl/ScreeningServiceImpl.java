package com.deik.ticketservice.core.screening.impl;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.room.persistence.entity.Room;
import com.deik.ticketservice.core.screening.persistence.entity.Screening;
import com.deik.ticketservice.core.screening.persistence.entity.id.ScreeningId;
import com.deik.ticketservice.core.movie.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.room.persistence.repository.RoomRepository;
import com.deik.ticketservice.core.screening.persistence.repository.ScreeningRepository;
import com.deik.ticketservice.core.screening.ScreeningService;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.room.exception.RoomException;
import com.deik.ticketservice.core.screening.exception.ScreeningException;
import com.deik.ticketservice.core.screening.model.ScreeningDto;
import com.deik.ticketservice.core.screening.model.ScreeningListDto;
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
    private static final String SCREENING_CANNOT_BE_NULL_MESSAGE = "Screening cannot be null";
    private static final String MOVIE_TITLE_CANNOT_BE_NULL_MESSAGE = "Screening movie title cannot be null";
    private static final String ROOM_NAME_CANNOT_BE_NULL_MESSAGE = "Screening room name cannot be null";
    private static final String DATE_AS_STRING_CANNOT_BE_NULL_MESSAGE = "Screening date (as string) cannot be null";

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
        Objects.requireNonNull(screeningDto, SCREENING_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(screeningDto.getMovieTitle(), MOVIE_TITLE_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(screeningDto.getRoomName(), ROOM_NAME_CANNOT_BE_NULL_MESSAGE);
        Objects.requireNonNull(screeningDto.getDateAsString(), DATE_AS_STRING_CANNOT_BE_NULL_MESSAGE);
    }

    private ScreeningListDto convertEntityToListDto(Screening screening) {
        return new ScreeningListDto.Builder()
                .withMovie(screening.getId().getMovie())
                .withRoom(screening.getId().getRoom())
                .withDate(screening.getId().getDate())
                .build();
    }

}