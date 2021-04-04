package com.deik.ticketservice.ui.command;

import com.deik.ticketservice.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
public class RoomCommand {

    @Autowired
    RoomService roomService;

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

    @ShellMethod(value = "List rooms", key = "list rooms")
    public String listRooms() {
        try {
            roomService.listRooms();
        } catch (Exception e) {
            log.error("Failed to list rooms", e);
            return "Failed to list rooms";
        }
        return "";
    }

}