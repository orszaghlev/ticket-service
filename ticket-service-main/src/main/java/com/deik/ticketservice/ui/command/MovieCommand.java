package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.persistence.entity.Movie;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.exception.MovieException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class MovieCommand {

    private static final String CREATE_MOVIE_VALUE = "Create movie";
    private static final String CREATE_MOVIE_KEY = "create movie";
    private static final String UPDATE_MOVIE_VALUE = "Update movie";
    private static final String UPDATE_MOVIE_KEY = "update movie";
    private static final String DELETE_MOVIE_VALUE = "Delete movie";
    private static final String DELETE_MOVIE_KEY = "delete movie";
    private static final String LIST_MOVIES_VALUE = "List movies";
    private static final String LIST_MOVIES_KEY = "list movies";
    private static final String LIST_MOVIES_SUCCESS = "%s (%s, %d minutes)%n";
    private static final String LIST_MOVIES_EMPTY = "There are no movies at the moment";
    private static final String LIST_MOVIES_FAIL = "Failed to list movies";

    private final MovieService movieService;

    private final AccountService accountService;

    public MovieCommand(MovieService movieService, AccountService accountService) {
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethod(value = CREATE_MOVIE_VALUE, key = CREATE_MOVIE_KEY)
    public void createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.createMovie(title, genre, runtime);
            }
        } catch (AccountException | MovieException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = UPDATE_MOVIE_VALUE, key = UPDATE_MOVIE_KEY)
    public void updateMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.updateMovie(title, genre, runtime);
            }
        } catch (AccountException | MovieException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = DELETE_MOVIE_VALUE, key = DELETE_MOVIE_KEY)
    public void deleteMovie(@ShellOption String title) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.deleteMovie(title);
            }
        } catch (AccountException | MovieException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = LIST_MOVIES_VALUE, key = LIST_MOVIES_KEY)
    public void listMovies() {
        try {
            List<Movie> movies = movieService.listMovies();
            if (movies.isEmpty()) {
                System.out.println(LIST_MOVIES_EMPTY);
            } else {
                for (Movie movie : movies) {
                    System.out.printf(LIST_MOVIES_SUCCESS, movie.getTitle(), movie.getGenre(), movie.getRuntime());
                }
            }
        } catch (Exception e) {
            log.error(LIST_MOVIES_FAIL, e);
        }
    }

}