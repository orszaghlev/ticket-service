package com.deik.ticketservice.service;

import com.deik.ticketservice.entity.Screening;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void createScreening(String movieTitle, String roomName, String dateAsString) throws ParseException;

    void deleteScreening(String movieTitle, String roomName, String dateAsString) throws ParseException;

    List<Screening> listScreenings();

}
