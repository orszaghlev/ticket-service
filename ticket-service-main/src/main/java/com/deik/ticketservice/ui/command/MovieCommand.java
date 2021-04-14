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

    private final MovieService movieService;

    private final AccountService accountService;

    @Autowired
    public MovieCommand(MovieService movieService, AccountService accountService) {
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Create movie", key = "create movie")
    public String createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.createMovie(title, genre, runtime);
                return "Created movie";
            }
        } catch (Exception e) {
            log.error("Failed to create movie", e);
        }
        return "";
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    public String updateMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.updateMovie(title, genre, runtime);
                return "Updated movie";
            }
        } catch (Exception e) {
            log.error("Failed to update movie", e);
        }
        return "";
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    public String deleteMovie(@ShellOption String title) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.deleteMovie(title);
                return "Deleted movie";
            }
        } catch (Exception e) {
            log.error("Failed to delete movie", e);
        }
        return "";
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public List<Movie> listMovies() {
        try {
            List<Movie> movies = movieService.listMovies();
            if (movies.isEmpty()) {
                System.out.println("There are no movies at the moment");
                return movies;
            } else {
                for (Movie movie : movies) {
                    System.out.printf("%s (%s, %d minutes)%n", movie.getTitle(), movie.getGenre(), movie.getRuntime());
                }
                return movies;
            }
        } catch (Exception e) {
            log.error("Failed to list movies", e);
        }
        return null;
    }

}