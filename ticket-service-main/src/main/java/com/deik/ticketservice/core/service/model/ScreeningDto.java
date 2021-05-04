package com.deik.ticketservice.core.service.model;

import java.util.Objects;

public class ScreeningDto {

    private final String movieTitle;
    private final String roomName;
    private final String dateAsString;

    public ScreeningDto(String movieTitle, String roomName, String dateAsString) {
        this.movieTitle = movieTitle;
        this.roomName = roomName;
        this.dateAsString = dateAsString;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getDateAsString() {
        return dateAsString;
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
        return getMovieTitle().equals(that.getMovieTitle()) && getRoomName().equals(that.getRoomName())
                && getDateAsString().equals(that.getDateAsString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieTitle(), getRoomName(), getDateAsString());
    }

    @Override
    public String toString() {
        return "ScreeningDto{" + "movieTitle='" + movieTitle + '\'' + ", roomName='" + roomName + '\''
                + ", dateAsString='" + dateAsString + '\'' + '}';
    }

    public static class Builder {

        private String movieTitle;
        private String roomName;
        private String dateAsString;

        public Builder withMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
            return this;
        }

        public Builder withRoomName(String roomName) {
            this.roomName = roomName;
            return this;
        }

        public Builder withDateAsString(String dateAsString) {
            this.dateAsString = dateAsString;
            return this;
        }

        public ScreeningDto build() {
            return new ScreeningDto(movieTitle, roomName, dateAsString);
        }

    }

}
