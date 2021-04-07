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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void createScreening(String movieTitle, String roomName, String dateAsString) throws ParseException {
        Optional<Movie> moviesWithTheGivenTitle = this.movieRepository.findByTitle(movieTitle);
        Optional<Room> roomsWithTheGivenName = this.roomRepository.findByName(roomName);
        if (moviesWithTheGivenTitle.isPresent() && roomsWithTheGivenName.isPresent()) {
            Movie movie = moviesWithTheGivenTitle.get();
            Room room = roomsWithTheGivenName.get();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = formatter.parse(dateAsString);
            Optional<Screening> screeningsWithTheGivenId = this.screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date);
            if (screeningsWithTheGivenId.isEmpty()) {
                ScreeningId screeningId = new ScreeningId(movie, room, date);
                Screening screeningToCreate = new Screening(screeningId);
                screeningRepository.save(screeningToCreate);
            }
        }
    }

    public void deleteScreening(String movieTitle, String roomName, String dateAsString) throws ParseException {
        Optional<Movie> moviesWithTheGivenTitle = this.movieRepository.findByTitle(movieTitle);
        Optional<Room> roomsWithTheGivenName = this.roomRepository.findByName(roomName);
        if (moviesWithTheGivenTitle.isPresent() && roomsWithTheGivenName.isPresent()) {
            Movie movie = moviesWithTheGivenTitle.get();
            Room room = roomsWithTheGivenName.get();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = formatter.parse(dateAsString);
            Optional<Screening> screeningsWithTheGivenId = this.screeningRepository.findById_MovieAndId_RoomAndId_Date(movie, room, date);
            if (screeningsWithTheGivenId.isPresent()) {
                Screening screeningToDelete = screeningsWithTheGivenId.get();
                screeningRepository.delete(screeningToDelete);
            }
        }
    }

    public List<Screening> listScreenings() {
        List<Screening> screenings = new ArrayList<>();
        screeningRepository.findAll().forEach(screening -> screenings.add(screening));
        return screenings;
    }

}