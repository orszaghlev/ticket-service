package com.deik.ticketservice.core.service.model;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.entity.Room;

import java.util.Date;
import java.util.Objects;

public class ScreeningListDto {

    private final Movie movie;
    private final Room room;
    private final Date date;

    private ScreeningListDto(Movie movie, Room room, Date date) {
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
        if (!(o instanceof ScreeningListDto)) {
            return false;
        }
        ScreeningListDto that = (ScreeningListDto) o;
        return getMovie().equals(that.getMovie()) && getRoom().equals(that.getRoom())
                && getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovie(), getRoom(), getDate());
    }

    @Override
    public String toString() {
        return "ScreeningListDto{" + "movie=" + movie + ", room=" + room + ", date=" + date + '}';
    }

    public static class Builder {

        private Movie movie;
        private Room room;
        private Date date;

        public Builder withMovie(Movie movie) {
            this.movie = movie;
            return this;
        }

        public Builder withRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public ScreeningListDto build() {
            return new ScreeningListDto(movie, room, date);
        }

    }

}
