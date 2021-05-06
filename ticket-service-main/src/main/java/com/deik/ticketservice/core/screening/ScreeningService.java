package com.deik.ticketservice.core.screening;

import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.room.exception.RoomException;
import com.deik.ticketservice.core.screening.exception.ScreeningException;
import com.deik.ticketservice.core.screening.model.ScreeningDto;
import com.deik.ticketservice.core.screening.model.ScreeningListDto;

import java.text.ParseException;
import java.util.List;

public interface ScreeningService {

    void createScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException;

    void deleteScreening(ScreeningDto screeningDto) throws ParseException, MovieException, RoomException,
            ScreeningException;

    List<ScreeningListDto> listScreenings();

}
