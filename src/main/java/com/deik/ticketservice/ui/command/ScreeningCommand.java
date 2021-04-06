package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Movie;
import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.ScreeningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Date;
import java.util.List;

@Slf4j
@ShellComponent
public class ScreeningCommand {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Create screening", key = "create screening")
    public void createScreening(@ShellOption Movie movie, @ShellOption Room room, @ShellOption Date date) {
        try {
            if (accountService.isAdminSignedIn()) {
                screeningService.createScreening(movie, room, date);
            }
        } catch (Exception e) {
            log.error("Failed to create screening", e);
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    public void deleteScreening(@ShellOption Movie movie, @ShellOption Room room, @ShellOption Date date) {
        try {
            if (accountService.isAdminSignedIn()) {
                screeningService.deleteScreening(movie, room, date);
            }
        } catch (Exception e) {
            log.error("Failed to delete screening", e);
        }
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public void listScreenings() {
        try {
            List<Screening> screenings = screeningService.listScreenings();
            if (screenings.isEmpty()) {
                System.out.println("There are no screenings");
            }
            else {
                for (Screening screening : screenings) {
                    System.out.println(String.format("%s (%s, %d), screened in room %s, at %tFT",
                            screening.getMovie().getTitle(), screening.getMovie().getGenre(),
                            screening.getMovie().getRuntime(), screening.getRoom().getName(), screening.getDate()));
                }
            }
        } catch (Exception e) {
            log.error("Failed to list screenings", e);
        }
    }

}