package com.deik.ticketservice.core.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "genre")
    private String genre;
    @Column(name = "runtime")
    private int runtime;

    @OneToMany(mappedBy = "id.movie")
    private Set<Screening> screenings;

    public Movie() {

    }

    public Movie(String title, String genre, int runtime) {
        this.title = title;
        this.genre = genre;
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie movie = (Movie) o;
        return getRuntime() == movie.getRuntime() && getTitle().equals(movie.getTitle())
                && getGenre().equals(movie.getGenre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getGenre(), getRuntime());
    }

    @Override
    public String toString() {
        return "Movie{" + ", title='" + title + '\'' + ", genre='" + genre + '\'' + ", runtime=" + runtime + '}';
    }

}
