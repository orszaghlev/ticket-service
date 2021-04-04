package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class MovieCommand {

    @Autowired
    MovieService movieService;

    /*@ShellMethod(value = "Create movie", key = "create movie")
    public String createMovie(@ShellOption String title, @ShellOption String genre, @ShellOption int runtime) {
        try {
            movieService.createMovie(title, genre, runtime);
        } catch (Exception e) {
            log.error("Failed to create movie", e);
            return "Failed to create movie";
        }
        return "";
    }*/

    @ShellMethod(value = "List movies", key = "list movies")
    public String listMovies() {
        try {
            movieService.listMovies();
        } catch (Exception e) {
            log.error("Failed to list movies", e);
            return "Failed to list movies";
        }
        return "";
    }

}
