package com.deik.ticketservice.core.service.model;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.entity.Room;

import java.util.Date;
import java.util.Objects;

public class ScreeningDto {

    private final Movie movie;
    private final Room room;
    private final Date date;

    public ScreeningDto(Movie movie, Room room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public Room getRoom() {
        return room;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreeningDto)) {
            return false;
        }
        ScreeningDto that = (ScreeningDto) o;
        return getMovie().equals(that.getMovie()) && getRoom().equals(that.getRoom())
                && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie(), getRoom(), getDate());
    }

    @Override
    public String toString() {
        return "ScreeningDto{" + "movie=" + movie + ", room=" + room + ", date=" + date + '}';
    }

}
