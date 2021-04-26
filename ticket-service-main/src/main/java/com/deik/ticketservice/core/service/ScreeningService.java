package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.persistence.entity.Screening;
import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.exception.ScreeningException;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void createScreening(String movieTitle, String roomName, String dateAsString) throws ParseException,
            ScreeningException, MovieException, RoomException;

    void deleteScreening(String movieTitle, String roomName, String dateAsString) throws ParseException,
            MovieException, RoomException, ScreeningException;

    List<Screening> listScreenings();

}
