package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.core.persistence.entity.Room;
import com.deik.ticketservice.core.service.AccountService;
import com.deik.ticketservice.core.service.RoomService;
import com.deik.ticketservice.core.service.exception.AccountException;
import com.deik.ticketservice.core.service.exception.RoomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class RoomCommand {

    private static final String CREATE_ROOM_VALUE = "Create room";
    private static final String CREATE_ROOM_KEY = "create room";
    private static final String UPDATE_ROOM_VALUE = "Update room";
    private static final String UPDATE_ROOM_KEY = "update room";
    private static final String DELETE_ROOM_VALUE = "Delete room";
    private static final String DELETE_ROOM_KEY = "delete room";
    private static final String LIST_ROOMS_VALUE = "List rooms";
    private static final String LIST_ROOMS_KEY = "list rooms";
    private static final String LIST_ROOMS_SUCCESS = "Room %s with %d seats, %d rows and %d columns%n";
    private static final String LIST_ROOMS_EMPTY = "There are no rooms at the moment";
    private static final String LIST_ROOMS_FAIL = "Failed to list rooms";

    private final RoomService roomService;

    private final AccountService accountService;

    public RoomCommand(RoomService roomService, AccountService accountService) {
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @ShellMethod(value = CREATE_ROOM_VALUE, key = CREATE_ROOM_KEY)
    public void createRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.createRoom(name, numberOfRows, numberOfCols);
            }
        } catch (AccountException | RoomException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = UPDATE_ROOM_VALUE, key = UPDATE_ROOM_KEY)
    public void updateRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.updateRoom(name, numberOfRows, numberOfCols);
            }
        } catch (AccountException | RoomException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = DELETE_ROOM_VALUE, key = DELETE_ROOM_KEY)
    public void deleteRoom(@ShellOption String name) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.deleteRoom(name);
            }
        } catch (AccountException | RoomException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod(value = LIST_ROOMS_VALUE, key = LIST_ROOMS_KEY)
    public void listRooms() {
        try {
            List<Room> rooms = roomService.listRooms();
            if (rooms.isEmpty()) {
                System.out.println(LIST_ROOMS_EMPTY);
            } else {
                for (Room room : rooms) {
                    System.out.printf(LIST_ROOMS_SUCCESS, room.getName(),
                            (room.getNumberOfRows() * room.getNumberOfCols()), room.getNumberOfRows(),
                            room.getNumberOfCols());
                }
            }
        } catch (Exception e) {
            log.error(LIST_ROOMS_FAIL, e);
        }
    }

}