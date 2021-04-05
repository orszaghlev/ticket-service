package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.entity.Room;
import com.deik.ticketservice.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@Slf4j
@ShellComponent
public class RoomCommand {

    @Autowired
    private RoomService roomService;

    /*@ShellMethod(value = "Create room", key = "create room")
    public String createRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            roomService.createRoom(name, numberOfRows, numberOfCols);
        } catch (Exception e) {
            log.error("Failed to create room", e);
            return "Failed to create room";
        }
        return "";
    }*/

    /*@ShellMethod(value = "Update room", key = "update room")
    public String updateRoom(@ShellOption String name, @ShellOption int numberOfRows, @ShellOption int numberOfCols) {
        try {
            roomService.updateRoom(name, numberOfRows, numberOfCols);
        } catch (Exception e) {
            log.error("Failed to update room", e);
            return "Failed to update room";
        }
        return "";
    }*/

    /*@ShellMethod(value = "Delete room", key = "delete room")
    public String deleteRoom(@ShellOption String name) {
        try {
            roomService.deleteRoom(name);
        } catch (Exception e) {
            log.error("Failed to delete room", e);
            return "Failed to delete room";
        }
        return "";
    }*/

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