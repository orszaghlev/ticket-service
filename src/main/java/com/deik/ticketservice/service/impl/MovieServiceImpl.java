package com.deik.ticketservice.service.impl;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.repository.MovieRepository;
import com.deik.ticketservice.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void createMovie(String title, String genre, int runtime) {
        Movie movie = new Movie(title, genre, runtime);
        movieRepository.save(movie);
    }

    public void updateMovie(String title, String genre, int runtime) {
        Optional<Movie> movies = this.movieRepository.findByTitle(title);
        if (movies.isPresent()) {
            Movie updatedMovie = movies.get();
            updatedMovie.setTitle(title);
            updatedMovie.setGenre(genre);
            updatedMovie.setRuntime(runtime);
            movieRepository.save(updatedMovie);
        }
    }

    public void deleteMovie(String title) {
        Optional<Movie> movies = this.movieRepository.findByTitle(title);
        if (movies.isPresent()) {
            Movie movieToDelete = movies.get();
            movieRepository.delete(movieToDelete);
        }
    }

    public List<Movie> listMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> movies.add(movie));
        return movies;
    }

}
