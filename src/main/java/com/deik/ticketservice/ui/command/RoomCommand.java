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

    @Autowired
    private RoomService roomService;

    @Autowired
    private AccountService accountService;

    @ShellMethod(value = "Create room", key = "create room")
    public void createRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.createRoom(name, numberOfRows, numberOfCols);
            }
        } catch (Exception e) {
            log.error("Failed to create room", e);
        }
    }

    @ShellMethod(value = "Update room", key = "update room")
    public void updateRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.updateRoom(name, numberOfRows, numberOfCols);
            }
        } catch (Exception e) {
            log.error("Failed to update room", e);
        }
    }

    @ShellMethod(value = "Delete room", key = "delete room")
    public void deleteRoom(@ShellOption String name) {
        try {
            if (accountService.isAdminSignedIn()) {
                roomService.deleteRoom(name);
            }
        } catch (Exception e) {
            log.error("Failed to delete room", e);
        }
    }

    @ShellMethod(value = "List rooms", key = "list rooms")
    public void listRooms() {
        try {
            List<Room> rooms = roomService.listRooms();
            if (rooms.isEmpty()) {
                System.out.println("There are no rooms at the moment");
            }
            else {
                for (Room room : rooms) {
                    System.out.println(String.format("%s (%d, %d)", room.getName(), room.getNumberOfRows(), room.getNumberOfCols()));
                }
            }
        } catch (Exception e) {
            log.error("Failed to list rooms", e);
        }
    }

}