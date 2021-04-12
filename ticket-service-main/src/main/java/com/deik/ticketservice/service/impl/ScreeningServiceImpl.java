package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.entity.id.ScreeningId;
import com.deik.ticketservice.repository.MovieRepository;
import com.deik.ticketservice.repository.RoomRepository;
import com.deik.ticketservice.repository.ScreeningRepository;
import com.deik.ticketservice.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    @Autowired
    public ScreeningServiceImpl(ScreeningRepository screeningRepository, MovieRepository movieRepository,
                                RoomRepository roomRepository) {
        this.screeningRepository = screeningRepository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void createScreening(String movieTitle, String roomName, String dateAsString) throws ParseException {
        if (movieRepository.findByTitle(movieTitle).isPresent() && roomRepository.findByName(roomName).isPresent()) {
            Movie movie = movieRepository.findByTitle(movieTitle).get();
            Room room = roomRepository.findByName(roomName).get();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateAsString);
            if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isEmpty()) {
                ScreeningId screeningId = new ScreeningId(movie, room, date);
                Screening screeningToCreate = new Screening(screeningId);
                screeningRepository.save(screeningToCreate);
            }
        }
    }

    @Override
    public void deleteScreening(String movieTitle, String roomName, String dateAsString) throws ParseException {
        if (movieRepository.findByTitle(movieTitle).isPresent() && roomRepository.findByName(roomName).isPresent()) {
            Movie movie = movieRepository.findByTitle(movieTitle).get();
            Room room = roomRepository.findByName(roomName).get();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateAsString);
            if (screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).isPresent()) {
                Screening screeningToDelete =
                        screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date).get();
                screeningRepository.delete(screeningToDelete);
            }
        }
    }

    @Override
    public List<Screening> listScreenings() {
        List<Screening> screenings = new ArrayList<>();
        screeningRepository.findAll().forEach(screenings::add);
        return screenings;
    }

}