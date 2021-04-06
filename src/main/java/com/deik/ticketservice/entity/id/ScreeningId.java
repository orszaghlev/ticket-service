package com.deik.ticketservice.entity.id;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ScreeningId implements Serializable {

    private Movie movie;

    private Room room;

    private Date date;

    public ScreeningId() {

    }

    public ScreeningId(Movie movie, Room room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScreeningId)) return false;
        ScreeningId that = (ScreeningId) o;
        return movie.equals(that.movie) && room.equals(that.room) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, room, date);
    }

}
