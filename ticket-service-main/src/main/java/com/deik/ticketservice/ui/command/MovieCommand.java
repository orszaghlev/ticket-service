package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.movie.MovieService;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.movie.model.MovieDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class MovieCommand {

    private static final String LIST_MOVIES_SUCCESS_MESSAGE = "%s (%s, %d minutes)%n";
    private static final String LIST_MOVIES_FAILURE_MESSAGE = "There are no movies at the moment";
    private static final String LIST_MOVIES_ERROR_MESSAGE = "Failed to list movies";
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieCommand.class);

    private final MovieService movieService;

    private final AccountService accountService;

    public MovieCommand(MovieService movieService, AccountService accountService) {
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Create movie", key = "create movie")
    public void createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                MovieDto movieDto = getMovieDto(title, genre, runtime);
                movieService.createMovie(movieDto);
            }
        } catch (AccountException | MovieException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Update movie", key = "update movie")
    public void updateMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            if (accountService.isAdminSignedIn()) {
                MovieDto movieDto = getMovieDto(title, genre, runtime);
                movieService.updateMovie(movieDto);
            }
        } catch (AccountException | MovieException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete movie", key = "delete movie")
    public void deleteMovie(@ShellOption String title) {
        try {
            if (accountService.isAdminSignedIn()) {
                movieService.deleteMovie(title);
            }
        } catch (AccountException | MovieException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "List movies", key = "list movies")
    public void listMovies() {
        try {
            List<MovieDto> movies = movieService.listMovies();
            if (movies.isEmpty()) {
                System.out.println(LIST_MOVIES_FAILURE_MESSAGE);
            } else {
                for (MovieDto movie : movies) {
                    System.out.printf(LIST_MOVIES_SUCCESS_MESSAGE, movie.getTitle(), movie.getGenre(),
                            movie.getRuntime());
                }
            }
        } catch (Exception e) {
            LOGGER.error(LIST_MOVIES_ERROR_MESSAGE);
        }
    }

    private MovieDto getMovieDto(String title, String genre, int runtime) {
        return new MovieDto.Builder()
                .withTitle(title)
                .withGenre(genre)
                .withRuntime(runtime)
                .build();
    }

}