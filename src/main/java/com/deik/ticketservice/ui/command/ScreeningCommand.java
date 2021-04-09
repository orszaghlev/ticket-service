package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.MovieService;
import com.deik.ticketservice.service.ScreeningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@ShellComponent
public class ScreeningCommand {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Create screening", key = "create screening")
    public void createScreening(@ShellOption String movieTitle, @ShellOption String roomName,
                                @ShellOption String dateAsString) {
        try {
            if (accountService.isAdminSignedIn()) {
                List<Screening> screenings = screeningService.listScreenings();
                boolean screeningOverlap = false;
                boolean breakOverlap = false;
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = formatter.parse(dateAsString);
                for (Screening screening : screenings) {
                    long existingScreeningSum = screening.getId().getDate().getTime()
                            + (screening.getId().getMovie().getRuntime() * 60_000L);
                    long newScreeningSum = date.getTime() + (movieService.getRuntimeByTitle(movieTitle) * 60_000L);
                    Date endOfExistingScreening = new Date(existingScreeningSum);
                    Date endOfNewScreening = new Date(newScreeningSum);
                    Date endOfBreak = new Date(existingScreeningSum + 600_000L);
                    if (screening.getId().getRoom().getName().equals(roomName)) {
                        //(ÚJ ELEJE BEFORE RÉGI VÉGE) VAGY (RÉGI ELEJE BEFORE ÚJ VÉGE)
                        if (date.before(endOfExistingScreening)
                                || screening.getId().getDate().before(endOfNewScreening)) {
                            screeningOverlap = true;
                        } else if (date.before(endOfBreak) && date.after(endOfExistingScreening)) {
                            breakOverlap = true;
                        }
                    }
                }
                if (screeningOverlap) {
                    System.out.println("There is an overlapping screening");
                } else if (breakOverlap) {
                    System.out.println("This would start in the break period after another screening in this room");
                } else {
                    screeningService.createScreening(movieTitle, roomName, dateAsString);
                }
            }
        } catch (Exception e) {
            log.error("Failed to create screening", e);
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    public void deleteScreening(@ShellOption String movieTitle, @ShellOption String roomName,
                                @ShellOption String dateAsString) {
        try {
            if (accountService.isAdminSignedIn()) {
                screeningService.deleteScreening(movieTitle, roomName, dateAsString);
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
            } else {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (Screening screening : screenings) {
                    System.out.println(String.format("%s (%s, %d minutes), screened in room %s, at %s",
                            screening.getId().getMovie().getTitle(), screening.getId().getMovie().getGenre(),
                            screening.getId().getMovie().getRuntime(), screening.getId().getRoom().getName(),
                            formatter.format(screening.getId().getDate())));
                }
            }
        } catch (Exception e) {
            log.error("Failed to list screenings", e);
        }
    }

}