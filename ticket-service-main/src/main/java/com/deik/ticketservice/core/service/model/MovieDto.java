package com.deik.ticketservice.core.service.model;

import java.util.Objects;

public class MovieDto {

    private final String title;
    private final String genre;
    private final int runtime;

    private MovieDto(String title, String genre, int runtime) {
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getRuntime() {
        return runtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MovieDto)) {
            return false;
        }
        MovieDto movieDto = (MovieDto) o;
        return getRuntime() == movieDto.getRuntime() && getTitle().equals(movieDto.getTitle())
                && getGenre().equals(movieDto.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getGenre(), getRuntime());
    }

    @Override
    public String toString() {
        return "MovieDto{" + "title='" + title + '\'' + ", genre='" + genre + '\'' + ", runtime=" + runtime + '}';
    }

    public static class Builder {

        private String title;
        private String genre;
        private int runtime;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withGenre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder withRuntime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        public MovieDto build() {
            return new MovieDto(title, genre, runtime);
        }

    }

}
