package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.account.AccountService;
import com.deik.ticketservice.core.movie.MovieService;
import com.deik.ticketservice.core.screening.ScreeningService;
import com.deik.ticketservice.core.account.exception.AccountException;
import com.deik.ticketservice.core.movie.exception.MovieException;
import com.deik.ticketservice.core.room.exception.RoomException;
import com.deik.ticketservice.core.screening.exception.ScreeningException;
import com.deik.ticketservice.core.screening.model.ScreeningDto;
import com.deik.ticketservice.core.screening.model.ScreeningListDto;
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
    private static final long ONE_MINUTE_IN_MS = 60_000;
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
                ScreeningDto screeningDto = getScreeningDto(movieTitle, roomName, dateAsString);
                if (!wouldOverlapOccur(screeningDto).equals("")) {
                    System.out.println(wouldOverlapOccur(screeningDto));
                } else {
                    screeningService.createScreening(screeningDto);
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
                ScreeningDto screeningDto = getScreeningDto(movieTitle, roomName, dateAsString);
                screeningService.deleteScreening(screeningDto);
            }
        } catch (AccountException | MovieException | ParseException | RoomException | ScreeningException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "List screenings", key = "list screenings")
    public void listScreenings() {
        try {
            List<ScreeningListDto> screenings = screeningService.listScreenings();
            if (screenings.isEmpty()) {
                System.out.println(LIST_SCREENINGS_FAILURE_MESSAGE);
            } else {
                for (ScreeningListDto screening : screenings) {
                    System.out.printf(LIST_SCREENINGS_SUCCESS_MESSAGE,
                            screening.getMovie().getTitle(), screening.getMovie().getGenre(),
                            screening.getMovie().getRuntime(), screening.getRoom().getName(),
                            new SimpleDateFormat(DATE_PATTERN).format(screening.getDate()));
                }
            }
        } catch (Exception e) {
            LOGGER.error(LIST_SCREENINGS_ERROR_MESSAGE);
        }
    }

    private String wouldOverlapOccur(ScreeningDto screeningDto) throws ParseException, MovieException {
        List<ScreeningListDto> screenings = screeningService.listScreenings();
        String newScreeningRoomName = screeningDto.getRoomName();
        Date newScreeningStartDate = new SimpleDateFormat(DATE_PATTERN).parse(screeningDto.getDateAsString());
        for (ScreeningListDto screening : screenings) {
            String existingScreeningRoomName = screening.getRoom().getName();
            Date existingScreeningStartDate = screening.getDate();
            long startOfExistingScreening = existingScreeningStartDate.getTime();
            long runtimeOfExistingScreening = screening.getMovie().getRuntime() * ONE_MINUTE_IN_MS;
            long endOfExistingScreening = startOfExistingScreening + runtimeOfExistingScreening;
            long startOfNewScreening = newScreeningStartDate.getTime();
            long runtimeOfNewScreening = movieService.getRuntimeByTitle(screeningDto.getMovieTitle())
                    * ONE_MINUTE_IN_MS;
            long endOfNewScreening = startOfNewScreening + runtimeOfNewScreening;
            if (existingScreeningRoomName.equals(newScreeningRoomName)) {
                if (newScreeningStartDate.before(new Date(endOfExistingScreening))
                        && existingScreeningStartDate.before(new Date(endOfNewScreening))) {
                    return SCREENING_OVERLAP_MESSAGE;
                } else if (newScreeningStartDate.before(new Date(endOfExistingScreening + TEN_MINUTES_IN_MS))
                    && newScreeningStartDate.after(new Date(endOfExistingScreening))) {
                    return BREAK_OVERLAP_MESSAGE;
                }
            }
        }
        return "";
    }

    private ScreeningDto getScreeningDto(String movieTitle, String roomName, String dateAsString) {
        return new ScreeningDto.Builder()
                .withMovieTitle(movieTitle)
                .withRoomName(roomName)
                .withDateAsString(dateAsString)
                .build();
    }

}