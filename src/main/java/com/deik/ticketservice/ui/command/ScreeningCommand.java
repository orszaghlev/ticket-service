package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Screening;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.ScreeningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Slf4j
@ShellComponent
public class ScreeningCommand {

    @Autowired
    private ScreeningService screeningService;

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Create screening", key = "create screening")
    public void createScreening(@ShellOption String movieTitle, @ShellOption String roomName,
                                @ShellOption String dateAsString) {
        try {
            if (accountService.isAdminSignedIn()) {
                screeningService.createScreening(movieTitle, roomName, dateAsString);
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
            }
            else {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                for (Screening screening : screenings) {
                    System.out.println(String.format("%s (,  minutes), screened in room %s, at %s",
                            screening.getId().getMovie().getTitle(), screening.getId().getRoom().getName(),
                            formatter.format(screening.getId().getDate())));
                }
            }
        } catch (Exception e) {
            log.error("Failed to list screenings", e);
        }
    }

}