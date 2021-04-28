package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.persistence.entity.Screening;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.MovieService;
import com.deik.ticketservice.core.service.ScreeningService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.exception.MovieException;
import com.deik.ticketservice.core.service.exception.RoomException;
import com.deik.ticketservice.core.service.exception.ScreeningException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ShellComponent
public class ScreeningCommand {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";
    private static final long ONE_MINUTE_IN_MS = 60_000L;
    private static final long TEN_MINUTES_IN_MS = ONE_MINUTE_IN_MS * 10;
    private static final String SCREENING_OVERLAP_MESSAGE = "There is an overlapping screening";
    private static final String BREAK_OVERLAP_MESSAGE =
            "This would start in the break period after another screening in this room";
    private static final String LIST_SCREENINGS_SUCCESS_MESSAGE = "%s (%s, %d minutes), screened in room %s, at %s%n";
    private static final String LIST_SCREENINGS_FAILURE_MESSAGE = "There are no screenings";
    private static final String LIST_SCREENINGS_ERROR_MESSAGE = "Failed to list screenings";
    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningCommand.class);

    private final ScreeningService screeningService;

    private final MovieService movieService;

    private final AccountService accountService;

    public ScreeningCommand(ScreeningService screeningService, MovieService movieService,
                            AccountService accountService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Create screening", key = "create screening")
    public void createScreening(@ShellOption String movieTitle, @ShellOption String roomName,
                                @ShellOption String dateAsString) {
        try {
            if (accountService.isAdminSignedIn()) {
                List<Screening> screenings = screeningService.listScreenings();
                boolean screeningOverlap = false;
                boolean breakOverlap = false;
                Date date = new SimpleDateFormat(DATE_PATTERN).parse(dateAsString);
                for (Screening screening : screenings) {
                    long existingScreeningSum = screening.getId().getDate().getTime()
                            + (screening.getId().getMovie().getRuntime() * ONE_MINUTE_IN_MS);
                    long newScreeningSum = date.getTime() + (movieService.getRuntimeByTitle(movieTitle)
                            * ONE_MINUTE_IN_MS);
                    if (screening.getId().getRoom().getName().equals(roomName)) {
                        if (date.before(new Date(existingScreeningSum))
                                && screening.getId().getDate().before(new Date(newScreeningSum))) {
                            screeningOverlap = true;
                        } else if (date.before(new Date(existingScreeningSum + TEN_MINUTES_IN_MS))
                                && date.after(new Date(existingScreeningSum))) {
                            breakOverlap = true;
                        }
                    }
                }
                if (screeningOverlap) {
                    System.out.println(SCREENING_OVERLAP_MESSAGE);
                } else if (breakOverlap) {
                    System.out.println(BREAK_OVERLAP_MESSAGE);
                } else {
                    screeningService.createScreening(movieTitle, roomName, dateAsString);
                }
            }
        } catch (AccountException | MovieException | ParseException | RoomException | ScreeningException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete screening", key = "delete screening")
    public void deleteScreening(@ShellOption String movieTitle, @ShellOption String roomName,
                                @ShellOption String dateAsString) {
        try {
            if (accountService.isAdminSignedIn()) {
                screeningService.deleteScreening(movieTitle, roomName, dateAsString);
            }
        } catch (AccountException | MovieException | ParseException | RoomException | ScreeningException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public void listScreenings() {
        try {
            List<Screening> screenings = screeningService.listScreenings();
            if (screenings.isEmpty()) {
                System.out.println(LIST_SCREENINGS_FAILURE_MESSAGE);
            } else {
                for (Screening screening : screenings) {
                    System.out.printf(LIST_SCREENINGS_SUCCESS_MESSAGE,
                            screening.getId().getMovie().getTitle(), screening.getId().getMovie().getGenre(),
                            screening.getId().getMovie().getRuntime(), screening.getId().getRoom().getName(),
                            new SimpleDateFormat(DATE_PATTERN).format(screening.getId().getDate()));
                }
            }
        } catch (Exception e) {
            LOGGER.error(LIST_SCREENINGS_ERROR_MESSAGE, e);
        }
    }

}