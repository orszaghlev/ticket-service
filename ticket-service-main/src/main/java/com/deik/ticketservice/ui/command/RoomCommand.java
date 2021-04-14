package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.service.AccountService;
import com.deik.ticketservice.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@Slf4j
@ShellComponent
public class RoomCommand {

    private final RoomService roomService;

    private final AccountService accountService;

    @Autowired
    public RoomCommand(RoomService roomService, AccountService accountService) {
        this.roomService = roomService;
        this.accountService = accountService;
    }

    @ShellMethod(value = "Create room", key = "create room")
    public String createRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.createRoom(name, numberOfRows, numberOfCols);
                return "Created room";
            }
        } catch (Exception e) {
            log.error("Failed to create room", e);
        }
        return "";
    }

    @ShellMethod(value = "Update room", key = "update room")
    public String updateRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.updateRoom(name, numberOfRows, numberOfCols);
                return "Updated room";
            }
        } catch (Exception e) {
            log.error("Failed to update room", e);
        }
        return "";
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    public String deleteRoom(@ShellOption String name) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.deleteRoom(name);
                return "Deleted room";
            }
        } catch (Exception e) {
            log.error("Failed to delete room", e);
        }
        return "";
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public List<Room> listRooms() {
        try {
            List<Room> rooms = roomService.listRooms();
            if (rooms.isEmpty()) {
                System.out.println("There are no rooms at the moment");
                return rooms;
            } else {
                for (Room room : rooms) {
                    System.out.printf("Room %s with %d seats, %d rows and %d columns%n", room.getName(),
                            (room.getNumberOfRows() * room.getNumberOfCols()), room.getNumberOfRows(),
                            room.getNumberOfCols());
                }
                return rooms;
            }
        } catch (Exception e) {
            log.error("Failed to list rooms", e);
        }
        return null;
    }

}