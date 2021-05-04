package com.deik.ticketservice.core.service;

import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.exception.ScreeningException;
import com.deik.ticketservice.core.service.model.ScreeningDto;
import com.deik.ticketservice.core.service.model.ScreeningListDto;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void createScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException;

    void deleteScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException;

    List<ScreeningListDto> listScreenings();

}
