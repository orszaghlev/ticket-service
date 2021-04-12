package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class MovieCommand {

    @Autowired
    private MovieService movieService;

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Create movie", key = "create movie")
    public void createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.createMovie(title, genre, runtime);
            }
        } catch (Exception e) {
            log.error("Failed to create movie", e);
        }
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    public void updateMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.updateMovie(title, genre, runtime);
            }
        } catch (Exception e) {
            log.error("Failed to update movie", e);
        }
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    public void deleteMovie(@ShellOption String title) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.deleteMovie(title);
            }
        } catch (Exception e) {
            log.error("Failed to delete movie", e);
        }
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public void listMovies() {
        try {
            List<Movie> movies = movieService.listMovies();
            if (movies.isEmpty()) {
                System.out.println("There are no movies at the moment");
            } else {
                for (Movie movie : movies) {
                    System.out.println(String.format("%s (%s, %d minutes)", movie.getTitle(), movie.getGenre(),
                            movie.getRuntime()));
                }
            }
        } catch (Exception e) {
            log.error("Failed to list movies", e);
        }
    }

}