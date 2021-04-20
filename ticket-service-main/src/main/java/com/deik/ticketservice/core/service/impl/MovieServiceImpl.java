package com.deik.ticketservice.core.service.impl;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.persistence.repository.MovieRepository;
import com.deik.ticketservice.core.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void createMovie(String title, String genre, int runtime) {
        if (movieRepository.findByTitle(title).isEmpty()) {
            Movie movieToCreate = new Movie(title, genre, runtime);
            movieRepository.save(movieToCreate);
        }
    }

    @Override
    public void updateMovie(String title, String genre, int runtime) {
        if (movieRepository.findByTitle(title).isPresent()) {
            Movie movieToUpdate = movieRepository.findByTitle(title).get();
            movieToUpdate.setTitle(title);
            movieToUpdate.setGenre(genre);
            movieToUpdate.setRuntime(runtime);
            movieRepository.save(movieToUpdate);
        }
    }

    @Override
    public void deleteMovie(String title) {
        if (movieRepository.findByTitle(title).isPresent()) {
            Movie movieToDelete = movieRepository.findByTitle(title).get();
            movieRepository.delete(movieToDelete);
        }
    }

    @Override
    public List<Movie> listMovies() {
        List<Movie> movies = new LinkedList<>();
        movieRepository.findAll().forEach(movies::add);
        return movies;
    }

    @Override
    public int getRuntimeByTitle(String title) {
        if (movieRepository.findByTitle(title).isPresent()) {
            Movie movie = movieRepository.findByTitle(title).get();
            return movie.getRuntime();
        }
        return 0;
    }

}
