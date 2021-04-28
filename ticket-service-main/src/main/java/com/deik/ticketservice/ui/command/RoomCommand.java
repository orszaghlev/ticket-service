package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.RoomService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.exception.RoomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class RoomCommand {

    private static final String LIST_ROOMS_SUCCESS_MESSAGE = "Room %s with %d seats, %d rows and %d columns%n";
    private static final String LIST_ROOMS_FAILURE_MESSAGE = "There are no rooms at the moment";
    private static final String LIST_ROOMS_ERROR_MESSAGE = "Failed to list rooms";
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomCommand.class);

    private final RoomService roomService;

    private final AccountService accountService;

    public RoomCommand(RoomService roomService, AccountService accountService) {
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Create room", key = "create room")
    public void createRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.createRoom(name, numberOfRows, numberOfCols);
            }
        } catch (AccountException | RoomException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Update room", key = "update room")
    public void updateRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.updateRoom(name, numberOfRows, numberOfCols);
            }
        } catch (AccountException | RoomException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    public void deleteRoom(@ShellOption String name) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.deleteRoom(name);
            }
        } catch (AccountException | RoomException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public void listRooms() {
        try {
            List<Room> rooms = roomService.listRooms();
            if (rooms.isEmpty()) {
                System.out.println(LIST_ROOMS_FAILURE_MESSAGE);
            } else {
                for (Room room : rooms) {
                    System.out.printf(LIST_ROOMS_SUCCESS_MESSAGE, room.getName(),
                            (room.getNumberOfRows() * room.getNumberOfCols()), room.getNumberOfRows(),
                            room.getNumberOfCols());
                }
            }
        } catch (Exception e) {
            LOGGER.error(LIST_ROOMS_ERROR_MESSAGE, e);
        }
    }

}