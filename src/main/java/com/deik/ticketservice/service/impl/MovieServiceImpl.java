package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.repository.MovieRepository;
import com.deik.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void createMovie(String title, String genre, int runtime) {
        Movie movie = new Movie(1, title, genre, runtime);
        movieRepository.save(movie);
    }

    public void updateMovie(String title, String genre, int runtime) {

    }

    public void deleteMovie(String title) {
        Movie movie = movieRepository.findByTitle(title).get();
        movieRepository.delete(movie);
    }

    public List<Movie> listMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> movies.add(movie));
        return movies;
    }

}
