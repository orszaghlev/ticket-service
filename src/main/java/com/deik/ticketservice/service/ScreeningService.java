package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.entity.Screening;

import java.util.Date;
import java.util.List;

public interface ScreeningService {

    void createScreening(Movie movie, Room room, Date date);
    void deleteScreening(Movie movie, Room room, Date date);
    List<Screening> listScreenings();

}
