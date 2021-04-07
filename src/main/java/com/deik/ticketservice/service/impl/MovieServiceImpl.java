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
        Optional<Movie> moviesWithTheGivenTitle = this.movieRepository.findByTitle(title);
        if (moviesWithTheGivenTitle.isEmpty()) {
            Movie movieToCreate = new Movie(title, genre, runtime);
            movieRepository.save(movieToCreate);
        }
    }

    public void updateMovie(String title, String genre, int runtime) {
        Optional<Movie> moviesWithTheGivenTitle = this.movieRepository.findByTitle(title);
        if (moviesWithTheGivenTitle.isPresent()) {
            Movie movieToUpdate = moviesWithTheGivenTitle.get();
            movieToUpdate.setTitle(title);
            movieToUpdate.setGenre(genre);
            movieToUpdate.setRuntime(runtime);
            movieRepository.save(movieToUpdate);
        }
    }

    public void deleteMovie(String title) {
        Optional<Movie> moviesWithTheGivenTitle = this.movieRepository.findByTitle(title);
        if (moviesWithTheGivenTitle.isPresent()) {
            Movie movieToDelete = moviesWithTheGivenTitle.get();
            movieRepository.delete(movieToDelete);
        }
    }

    public List<Movie> listMovies() {
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().forEach(movie -> movies.add(movie));
        return movies;
    }

}
