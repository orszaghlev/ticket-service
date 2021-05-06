package com.deik.ticketservice.core.screening.persistence.entity.id;

import com.deik.ticketservice.core.movie.persistence.entity.Movie;
import com.deik.ticketservice.core.room.persistence.entity.Room;

import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class ScreeningId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "movie")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "room")
    private Room room;
    @Column(name = "date")
    private Date date;

    public ScreeningId() {

    }

    public ScreeningId(Movie movie, Room room, Date date) {
        this.movie = movie;
        this.room = room;
        this.date = date;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScreeningId)) {
            return false;
        }
        ScreeningId that = (ScreeningId) o;
        return movie.equals(that.movie) && room.equals(that.room) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, room, date);
    }

    @Override
    public String toString() {
        return "ScreeningId{" + "movie=" + movie + ", room=" + room + ", date=" + date + '}';
    }

}
