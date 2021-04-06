package com.deik.ticketservice.entity;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "Movie{" +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", runtime=" + runtime +
                '}';
    }

}
